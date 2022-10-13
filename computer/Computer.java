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

    @Override
    public String getMem(int index) {
        // return mem[index];
        return mem[index];
    }

    @Override
    public String getReg(int index) {
        return reg[index];
    }

    @Override
    public int getPC() {
        return PC;
    }

    @Override
    public Boolean getEnd() {
        return end;
    }

    @Override
    public void setMem(int index, String binaryCode) {
        // mem[index] = binaryCode;
        mem[index] = binaryCode;
    }

    @Override
    public void setReg(int index, String binaryCode) {
        reg[index] = binaryCode;
    }

    @Override
    public void setPC(int index) {
        PC = index;        
    }

    @Override
    public void setEnd(Boolean boolVal) {
        end = boolVal;
    }

    @Override
    public int getMemLen() {
        return mem.length;
    }

	@Override
	public int getNumPrintLoop() {
		return numPrintLoop;
	}

	@Override
	public void setNumPrintLoop(int num) {
        numPrintLoop = num;
	}


}
