package assembler;

import computer.ComputerInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
// import java.io.BufferedWriter;


import java.util.ArrayList;
// import java.util.arr
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Assembler implements AssemblerInt {

    File file;
    BufferedReader br;
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

    public Assembler() {
    }

    // private void printTokenStip(){
    //     for (String[] tokenS : tokenAssem) {
    //         printEachToken(tokenS);
    //         System.out.println("");
    //     }
    // }

    // private void printEachToken(String[] tokenS){
    //     for (String token : tokenS) {
    //         System.out.print(token+" ");
    //     }
    // }

    // private void printEachBiInstr(){
    //     for (String string : finalResult) {
    //         System.out.println(string);
    //     }
    // }

    @Override
    public void interpretAndSave(String fileAddress, ComputerInt inObject) {
        init(fileAddress);
        try {
            readFile();
            labelFindiNRemove();
            removeComment();
            convertSymbolic();            
        } catch (Exception e) {
            System.err.println("exit(1): " + e);
        }
        gettingBiStringConv();
        settingToReturn(inObject);

        // printTokenStip();
        // System.out.println("=============After finding and removing label=============");
        // printTokenStip();
        // System.out.println(labelMap);
        // System.out.println("=============After removing comments=============");
        // System.out.println("=============After converting Symbolic=============");
        // printTokenStip();
        // printEachBiInstr();
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
    private void readFile() throws IOException{
        String currLine;
            while ((currLine = br.readLine()) != null){
                // System.out.println(currLine);
                tokenAssem.add(tokenizing(currLine));
            }

    }

    /** Taken a line of assembly and break it down into tokens.
     *  @param line of string wish tobe tokenize with space as spliter
     * 
     */
    private String[] tokenizing(String line){
        return line.toLowerCase().trim().split("\\s+");
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
     * @throws InvalidLabel
     */
    private void labelFindiNRemove() throws DuplicateLabelException, InvalidLabel{
        int addressNum = 0;
        for (String[] tokens : tokenAssem) {
            if(isValidLabel(tokens[0])) {
                if(!labelMap.containsKey(tokens[0])){
                    labelMap.put(tokens[0], addressNum);
                    removeLabel(tokens);
                }else throw new DuplicateLabelException();
            }else throw new InvalidLabel();
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
     * @throws InvalidInstruction
     */
    private String[] removeEleFrom(String[] tokens, int fieldCount) throws InvalidInstruction{
        List<String> result = new ArrayList<>();
        try {
            for (int i = 0; i < fieldCount+1; i++) {
                result.add(tokens[i]);
            }
            return result.toArray(new String[fieldCount+1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidInstruction("None enough field as required");
        }

        
    }

    /** Remove comment in every instructions 
     * 
     * @throws InvalidInstruction
     */
    private void removeComment() throws InvalidInstruction{
        List<String[]> newTokensAssem = new ArrayList<>();
        for (String[] tokens : tokenAssem) {
            String instr = tokens[0];
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
                    throw new InvalidInstruction("Intruction have no format");
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

    /** Convert all symbolic for it value
     * 
     * @throws UnknownLabel
     */
    private void convertSymbolic() throws UnknownLabel{
        List<String[]> newTokensAssem = new ArrayList<>();
        for (String[] tokens : tokenAssem) {
            String instr = tokens[0];
            if (isXTypeInstr(instrITypeKey, instr)){
                if (stringIsNum(tokens[3])) newTokensAssem.add(tokens);
                else{
                    if(labelMap.containsKey(tokens[3])){
                        String[] temp = {tokens[0],tokens[1],tokens[2],labelMap.get(tokens[3]).toString()};
                        newTokensAssem.add(temp);
                    }else throw new UnknownLabel();
                }
            }else if (isXTypeInstr(specialKey, instr)){
                if (stringIsNum(tokens[1])) newTokensAssem.add(tokens);
                else{
                    if(labelMap.containsKey(tokens[1])){
                        String[] temp = {tokens[0], labelMap.get(tokens[1]).toString()};
                    newTokensAssem.add(temp);
                    }else throw new UnknownLabel();
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
            com.setMem(i, finalResult.get(i));
        }
    }

}
