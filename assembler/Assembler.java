package assembler;

import computer.ComputerInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;


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

    private void printEachBiInstr(){
        for (String string : finalResult) {
            System.out.println(string);
        }
    }

    @Override
    public void interpretAndSave(String fileAddress, ComputerInt inObject) {
        init(fileAddress);
        readFile();
        // printTokenStip();
        labelFindiNRemove();
        // System.out.println("=============After finding and removing label=============");
        // printTokenStip();
        // System.out.println(labelMap);
        removeComment();
        // System.out.println("=============After removing comments=============");
        convertSymbolic();
        // System.out.println("=============After converting Symbolic=============");
        // printTokenStip();
        gettingBiStringConv();
        printEachBiInstr();
        // settingToReturn(inObject);
    }
     
    // *** Initialize file and br variable for other uses
    private void init(String fileAddress){
        ap = new AssemblyParser();
        file = new File(fileAddress);
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println(fileAddress + " text file not found");
            e.printStackTrace();
        }
    }

    // *** Read every line in br and store it in tokenAssem.
    private void readFile(){
        String currLine;
        try {
            while ((currLine = br.readLine()) != null){
                // System.out.println(currLine);
                tokenAssem.add(tokenizing(currLine));
            }
        } catch (IOException e) {
            System.out.println("Sometime went worng during file reading");
            e.printStackTrace();
        }

    }

    // *** Taken a line of assembly and break it down into tokens.
    private String[] tokenizing(String line){
        return line.trim().split("\\s+");
    }

    // *** Checking if given string is valid instruction header or not
    private Boolean isInstr(String str){
        for (String key : reservedKey) {if (str.equals(key)) return true;}
        return false;
    }

    private Boolean isXTypeInstr(String[] keys,String str){
        for (String key : keys) {if (str.equals(key)) return true;}
        return false;
    }

    private String getInstrType(String str){
        if(isXTypeInstr(instrRTypeKey, str)) return "R";
        else if(isXTypeInstr(instrITypeKey, str)) return "I";
        else if(isXTypeInstr(instrJTypeKey, str)) return "J";
        else if(isXTypeInstr(instrOTypeKey, str)) return "O";
        else if(isXTypeInstr(specialKey, str)) return "S";
        else return "NULL";
    }

    // *** returning true id given str is lavid label
    private Boolean isValidLabel(String str){
        return !isInstr(str) && (str.chars().count() <= 6);
    }

    // *** go throught tokenAssem and look for label. if label found add into labelMap.
    private void labelFindiNRemove(){
        int addressNum = 0;
        for (String[] tokens : tokenAssem) {
            if(isValidLabel(tokens[0])) {
                labelMap.put(tokens[0], addressNum);
                removeLabel(tokens);
            }
            addressNum++;
        }
    }

    // *** remove first Token in given Tokens
    private void removeLabel(String[] tokens){
        System.arraycopy(tokens, 1, tokens, 0, tokens.length-1);
    }

    private String[] removeEleFrom(String[] tokens, int fieldCount){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < fieldCount+1; i++) {
            result.add(tokens[i]);
        }
        return result.toArray(new String[fieldCount+1]);
    }

    private void removeComment(){
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
                // Error Detect
                    break;
            }
        }
        tokenAssem = newTokensAssem;
    }

    private Boolean stringIsNum(String str){
        try{
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException ex){
            return false;
        }
    }

    private void convertSymbolic(){
        List<String[]> newTokensAssem = new ArrayList<>();
        for (String[] tokens : tokenAssem) {
            String instr = tokens[0];
            if (isXTypeInstr(instrITypeKey, instr)){
                if (stringIsNum(tokens[3])) newTokensAssem.add(tokens);
                else{
                    String[] temp = {tokens[0],tokens[1],tokens[2],labelMap.get(tokens[3]).toString()};
                    newTokensAssem.add(temp);
                }
            }else if (isXTypeInstr(specialKey, instr)){
                if (stringIsNum(tokens[1])) newTokensAssem.add(tokens);
                else{
                    String[] temp = {tokens[0], labelMap.get(tokens[1]).toString()};
                    newTokensAssem.add(temp);
                }
            }else newTokensAssem.add(tokens);
        }
        tokenAssem = newTokensAssem;
    }

    private void gettingBiStringConv(){
        for (String[] Tokens : tokenAssem) {
            finalResult.add(ap.InstructionsTobin32(Tokens));
        }
    }

    private void settingToReturn(ComputerInt com){
        for (int i = 0; i < finalResult.size(); i++) {
            com.setMem(i, finalResult.get(i));
        }
    }

}
