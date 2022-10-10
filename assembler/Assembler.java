package assembler;
// * Storing Obj
import computer.ComputerInt;
// * IO obj/Function in Uses
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
// * Data Structure in Uses.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// * Custom Exception
import assembler.exception.DuplicateLabelException;
import assembler.exception.InvalidInstructionException;
import assembler.exception.InvalidLabelException;
import assembler.exception.InvalidValueUsesException;
import assembler.exception.UnknownLabelException;



public class Assembler implements AssemblerInt {

    File file;
    File macCodeFile = new File("C:\\Users\\DELL\\OneDrive - Chiang Mai University\\University File\\Year 65 (2022)\\1_65\\CPE304 [Sec001]\\Proj\\CoAD\\MachineCode\\CurrentMachineCode.txt");
    BufferedReader br;
    BufferedWriter bw;
    AssemblyParser ap;

    private String[] instrRTypeKey ={"add", "nand"};
    private String[] instrITypeKey ={"lw", "sw", "beq"};
    private String[] instrJTypeKey ={"jalr"};
    private String[] instrOTypeKey ={"halt", "noop"};
    private String[] specialKey ={".fill"};
    private String[] reservedKey = {"add", "nand", "lw", "sw", "beq", "jalr", "halt", "noop", ".fill"};

    List<String[]> tokenAssem = new ArrayList<String[]>();
    Map <String, Integer> labelMap = new HashMap<String, Integer>();
    List<String> finalResult = new ArrayList<String>();

    public Assembler(String macCodeLoca) {
        macCodeFile = new File(macCodeLoca);
    }

    public Assembler(){
        
    }

    @Override
    public void interpretAndSave(String fileAddress, ComputerInt inObject) {
        init(fileAddress);
        try {
            readFileNTokenize();
            labelFindiNRemove();
            System.out.println(fileAddress);
            removeComment();
            convertSymbolic();
            offsetAndValChecking();
            gettingBiStringConv();
            writeIntreResult();
        } catch (Exception e) {
            System.err.println("exit(1): " + e);
            return;
        }
        settingToReturn(inObject);
        setLoopNum(inObject);
        System.out.println("exist(0): Processd with caution");
    }
     
    /** Initialize file and br variable for other uses
    *   @param fileaddress in form of String
    *
    */
    private void init(String fileAddress){
        ap = new AssemblyParser();
        file = new File(fileAddress);
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println(fileAddress + " Text file not found");
            // e.printStackTrace();
        }
    }

    /** Read every line in br then store, and tokenize it in tokenAssem.
     * @throws IOException
     *
     */
    private void readFileNTokenize() throws IOException{
        String currLine;
            while ((currLine = br.readLine()) != null){
                // System.out.println(currLine);
                tokenAssem.add(tokenizing(currLine));
            }
    }

    private void writeIntreResult() throws IOException{
        bw = new BufferedWriter(new FileWriter(macCodeFile));
        for (int i = 0; i < finalResult.size(); i++) {
            String currBiInstr = finalResult.get(i);
            // int currDecInstr = ;
            String tempHex = Integer.toHexString(Integer.parseUnsignedInt(currBiInstr,2));
            String tempDec = Integer.toString(Integer.parseUnsignedInt(currBiInstr,2));
            String leStr = "(address " + i + "):"  +  tempDec +"(Hex:0x" + tempHex + ")";
            // String leStr =  "(address " + i + "):" + currBiInstr + "(Hex:" + tempHex + ")";
            bw.write(leStr);
            bw.newLine();
        }
        bw.close();
    }

    /** Taken a line of assembly and break it down into tokens.
     *  @param line of string wish tobe tokenize with space as spliter
     * 
     */
    private String[] tokenizing(String line){
        return line.trim().split("\\s+");
    }

    /** Checking if given string is valid instruction header or not
     * 
     * 
     */
    private Boolean isInstr(String str){
        for (String key : reservedKey) {if (str.equals(key)) return true;}
        return false;
    }

    /** Check if the given string is valid X-type instruction or not
     * 
     * @param keys a predetermined set of key.
     * @param str
     * @return if given key is valid X-type format or not
     */
    private Boolean isXTypeInstr(String[] keys,String str){
        for (String key : keys) {if (str.equals(key)) return true;}
        return false;
    }

    /** Return a single string represent a type of give instruction
     * 
     * @param str
     * @return
     */
    private String getInstrType(String str){
        if(isXTypeInstr(instrRTypeKey, str)) return "R";
        else if(isXTypeInstr(instrITypeKey, str)) return "I";
        else if(isXTypeInstr(instrJTypeKey, str)) return "J";
        else if(isXTypeInstr(instrOTypeKey, str)) return "O";
        else if(isXTypeInstr(specialKey, str)) return "S";
        else return "NULL";
    }

    /**returning true id given str is lavid label
     * 
     * @param str
     * @return is Given String is valid label
     */
    private Boolean isValidLabel(String str){
        Boolean firstCharCheck = !stringIsNum(String.valueOf(str.charAt(0)));
        Boolean containSpecial = stringContainSpecial(str);
        return !isInstr(str) && (str.chars().count() <= 6) && firstCharCheck && !containSpecial;
    }

    /**
     * @param str
     * @return
     */
    private Boolean stringContainSpecial(String str){
        String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(specialCharactersString.contains(Character.toString(c))) return true;
        }
        return false;
    }

    /**go throught tokenAssem and look for label. if label found add into labelMap.
     * 
     * @throws DuplicateLabelException
     * @throws InvalidLabelException
     */
    private void labelFindiNRemove() throws DuplicateLabelException, InvalidLabelException{
        int addressNum = 0;
        for (String[] tokens : tokenAssem) {
            if(isValidLabel(tokens[0])) {
                if(!labelMap.containsKey(tokens[0])){
                    labelMap.put(tokens[0], addressNum);
                    removeLabel(tokens);
                }else throw new DuplicateLabelException(tokens[0]+" is ready declare as label");
            }else if(stringContainSpecial(tokens[0])) throw new InvalidLabelException(tokens[0]+" is not valid label");
            addressNum++;
        }
    }

    /**remove first Token in given Tokens
     * 
     * @param tokens
     */
    private void removeLabel(String[] tokens){
        System.arraycopy(tokens, 1, tokens, 0, tokens.length-1);
    }

    /**remove other element in tokens except instruction and its fields
     * 
     * @param tokens
     * @param fieldCount
     * @return
     * @throws InvalidInstructionException
     */
    private String[] removeEleFrom(String[] tokens, int fieldCount) throws InvalidInstructionException{
        List<String> result = new ArrayList<>();
            for (int i = 0; i < fieldCount+1; i++) {
                result.add(tokens[i]);
            }
            return result.toArray(new String[fieldCount+1]);
    }

    /** Remove comment in every instructions 
     * 
     * @throws InvalidInstructionException
     */
    private void removeComment() throws InvalidInstructionException{
        List<String[]> newTokensAssem = new ArrayList<>();
        for (String[] tokens : tokenAssem) {
            String instr = tokens[0];
            try {
                switch (getInstrType(instr)) {
                    case "R":
                        newTokensAssem.add(removeEleFrom(tokens, 3));
                        break;
                    case "I":
                        newTokensAssem.add(removeEleFrom(tokens, 3));
                        break;
                    case "J":
                        newTokensAssem.add(removeEleFrom(tokens, 2));
                        break;
                    case "O":
                        newTokensAssem.add(removeEleFrom(tokens, 0));
                        break;
                    case "S":
                        newTokensAssem.add(removeEleFrom(tokens, 1));
                        break;
                    default:
                        throw new InvalidInstructionException("Unknown Instruction: " + instr);
                }
            }catch (ArrayIndexOutOfBoundsException e) {
                String line = "";
                for (String str : tokens) {line+=(str+" ");}
                throw new InvalidInstructionException("None enough field as required: " + line);
            }
            
        }
        tokenAssem = newTokensAssem;
    }

    /** Check if given string can be number or not.
     * 
     * @param str
     * @return
     */
    private Boolean stringIsNum(String str){
        try{
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException ex){
            return false;
        }
    }

    private int stringToNum(String str){
        return Integer.parseInt(str);
    }

    /** Convert all symbolic for it value
     * 
     * @throws UnknownLabelException
     */
    private void convertSymbolic() throws UnknownLabelException{
        List<String[]> newTokensAssem = new ArrayList<>();
        for (int pc = 0; pc < tokenAssem.size(); pc++) {
            String[] tokens = tokenAssem.get(pc);
            String instr = tokens[0];
            if (isXTypeInstr(instrITypeKey, instr)){
                if (stringIsNum(tokens[3])) newTokensAssem.add(tokens);
                else{
                    if(labelMap.containsKey(tokens[3])){
                        String sym;
                        if(instr.equals("beq")){
                            int rela = labelMap.get(tokens[3]) - pc - 1;
                            sym = Integer.toString(rela);
                        }else{
                            sym = labelMap.get(tokens[3]).toString();
                        }
                        String[] temp = {tokens[0],tokens[1],tokens[2],sym};
                        newTokensAssem.add(temp);
                    }else throw new UnknownLabelException(tokens[3] + "is not a label");
                }
            }else if (isXTypeInstr(specialKey, instr)){
                if (stringIsNum(tokens[1])) newTokensAssem.add(tokens);
                else{
                    if(labelMap.containsKey(tokens[1])){
                        String[] temp = {tokens[0], labelMap.get(tokens[1]).toString()};
                    newTokensAssem.add(temp);
                    }else throw new UnknownLabelException(tokens[1] + "is not a label");
                }
            }else newTokensAssem.add(tokens);
        }
        tokenAssem = newTokensAssem;
    }

    /** Turning all tokens in to final binary string.
     * 
     */
    private void gettingBiStringConv(){
        for (String[] Tokens : tokenAssem) {
            finalResult.add(ap.InstructionsTobin32(Tokens));
        }
    }

    /** Storing every processed binary string into Computer obj.
     * 
     * @param com
     */
    private void settingToReturn(ComputerInt com){
        for (int i = 0; i < finalResult.size(); i++) {
            // com.setMem(i, finalResult.get(i));
            com.addMem(finalResult.get(i));
        }
    }

    /** Checking if offsets from given assembly is valid or not
     * @throws InvalidValueUsesException
     * 
     */
    private void offsetAndValChecking() throws InvalidValueUsesException{
        for (String[] tokens : tokenAssem) {
            switch (getInstrType(tokens[0])) {
                case "I":
                    String offStr = tokens[3];
                    if (stringIsNum(offStr)){
                        int offset = stringToNum(offStr);
                        if(!isValidOffSet(offset))throw new InvalidValueUsesException(offStr+" is not valid offset");
                    }
                    break;
                case "S":
                    String valStr = tokens[1];
                    if (stringIsNum(valStr)){
                        int val = stringToNum(valStr);
                        if(!isValidVal(val))throw new InvalidValueUsesException(valStr+" is not valid value");
                    }
                    break;
            }
        }
    }

    private Boolean isValidOffSet(int offset){
        return offset <= 32767 && offset >= -32768;
    }

    private Boolean isValidVal(int val){
        return val <= 65535 && val >= -65536;
    }

    private void  setLoopNum(ComputerInt pc){
        pc.setNumPrintLoop(finalResult.size());
    }

}
