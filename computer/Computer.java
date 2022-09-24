package computer;

import java.util.ArrayList;

public class Computer implements ComputerInt{
    private Boolean end = false;
    private int PC = 0;
    private ArrayList<String> mem;
    private String[] reg;

    public Computer(){
        mem = new ArrayList<String>();
    }

    @Override
    public String getMem(int index) {
        return mem.get(index);
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
        mem.set(index, binaryCode);        
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


}
