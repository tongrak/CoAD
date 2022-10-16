package computer;

import java.io.BufferedWriter;
import java.io.IOException;

import aux_use.Helper;

public class Computer implements ComputerInt{
    private Boolean end = false;
    private int PC = 0;
    private String[] mem;
    private String[] reg;
    private int numPrintLoop;

    public Computer(){
        end = false;
        mem = new String[65536];
        reg = new String[8];
    }

    /**
     * print all values in each memory.
     * @param wr this argument use to write text into file.
     */
    @Override
    public void printMemory(BufferedWriter wr){
        try{
            wr.newLine();
            for(int i = 0;i < numPrintLoop; i++){
                wr.write("memory[" + i + "]=" + Helper.binToInt(mem[i]));
            wr.newLine();
            }
            wr.newLine();
        }
        catch(IOException e){
            System.out.print("Can't show memory because of something.");
        }
    }

    /**
     * print a state.
     * @param wr this argument use for write text into file.
     */
    @Override
    public void printState(BufferedWriter wr){
        try{
            wr.newLine();
            wr.write("@@@");
            wr.newLine();
            wr.write("state:");
            wr.newLine();
            wr.write("        pc "+PC);
            wr.newLine();
            //print memory
            wr.write("        memory:");
            wr.newLine();
            for(int i = 0; i < numPrintLoop;i++){
                wr.write("                mem[ "+i+" ] "+Helper.binToInt(mem[i]));
                wr.newLine();
            }
            //print register
            wr.write("        registers:");
            wr.newLine();
            for(int i = 0; i < reg.length;i++){
                wr.write("                reg[ "+i+" ] " + Helper.binToInt(reg[i]));
                wr.newLine();
            }
            wr.write("end state");
            wr.newLine();
        }
        catch(IOException e){
            System.out.println("not correct text");
        }
    }

    /**
     * print summary amount of executed instructions if program normally.
     * @param wr this argument use for write text into file.
     * @param count amount of executed instructions.
     */
    @Override
    public void printSummaryState(BufferedWriter wr, int count) {
        try{
            wr.write("machine halted");
            wr.newLine();
            wr.write("total of " + count + " instructions executed");
            wr.newLine();
            wr.write("final state of machine:");
            wr.newLine();
        }   
        catch(IOException e){
            System.out.println("Somthing wrong in printSummaryState");
        }     
    }

    /**
     * get a value from specific memory.
     * @param index use for specify memory.
     */
    @Override
    public String getMem(int index) {
        // return mem[index];
        return mem[index];
    }

    /**
     * get a value from specific register.
     * @param index use for specify register. 
     */
    @Override
    public String getReg(int index) {
        return reg[index];
    }

    /**
     * get current Program Counter.
     */
    @Override
    public int getPC() {
        return PC;
    }

    /**
     * get status of program to check is it end yet.
     */
    @Override
    public Boolean getEnd() {
        return end;
    }

    /**
     * set a specific memory with value.
     * @param index use for specify memory.
     * @param binaryCode use as value to put in memory.
     */
    @Override
    public void setMem(int index, String binaryCode) {
        // mem[index] = binaryCode;
        mem[index] = binaryCode;
    }

    /**
     * set a specific memory with value.
     * @param index use for specify memory.
     * @param binaryCode use as value to put in memory.
     */
    @Override
    public void setReg(int index, String binaryCode) {
        reg[index] = binaryCode;
    }

    /**
     * set PC with a value
     * @param index use as value.
     */
    @Override
    public void setPC(int index) {
        PC = index;        
    }

    /**
     * set status of program.
     * @param booVal is wanted status to set.
     */
    @Override
    public void setEnd(Boolean boolVal) {
        end = boolVal;
    }

    /**
     * get size of memory.
     */
    @Override
    public int getMemLen() {
        return mem.length;
    }

    /**
     * I will be back.
     */
	@Override
	public int getNumPrintLoop() {
		return numPrintLoop;
	}

    /**
     * I will be back.
     */
	@Override
	public void setNumPrintLoop(int num) {
        numPrintLoop = num;
	}


}
