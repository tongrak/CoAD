package computer;

import java.util.ArrayList;

public class Computer implements ComputerInt{
    private Boolean end = false;
    private int PC = 0;
    private String[] mem;
    private String[] reg;
    private int numPrintLoop = 10;
    private int stackAddress = 0;

    public Computer(){
        end = false;
        mem = new String[65536];
        reg = new String[8];
        for(int i = 0; i < reg.length; i++){
            reg[i] = "0";
        }
    }

    public void printState(){
        System.out.println("@@@");
        System.out.println("state:");
        System.out.println("        pc "+PC);
        //print memory
        System.out.println("        memory:");
        for(int i = 0; i < numPrintLoop;i++){
            System.out.println("                mem[ "+i+" ] "+mem[i]);
        }
        //print register
        System.out.println("        registers:");
        for(int i = 0; i < reg.length;i++){
            System.out.println("                reg[ "+i+" ] "+reg[i]);
        }
        System.out.println("end state");
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
