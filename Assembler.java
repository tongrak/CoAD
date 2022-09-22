

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Assembler implements AssemblerInt {

    File file;
    BufferedReader br;    

    List<String[]> tokenAssem = new ArrayList<String[]>();
    Map <String, Integer> labelMap = new HashMap<String, Integer>();



    // @Override
    public void interpretAndSave(String fileAddress, ComputerInt inObject) {
        init(fileAddress);
        readFile();
    }
     
    // *** Initialize file and br variable for other uses
    private void init(String fileAddress){
        file = new File(fileAddress);
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println(fileAddress + " text file not found");
            e.printStackTrace();
        }
    }

    // *** Read every line in br and store it in rawAssem.
    private void readFile(){
        String currLine;
        try {
            while ((currLine = br.readLine()) != null){
                System.out.println(currLine);
            }
        } catch (IOException e) {
            System.out.println("Sometime went worng during file reading");
            e.printStackTrace();
        }

    }

    // *** Taken a line of assembly and break it down into tokens.
    private String[] tokenizer(String line){
        String[] simple = {""};
        return simple;
    }

    // ** Looping
    private void labelFinding(){

    }

    private void fillFinding(){

    }
}
