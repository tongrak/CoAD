package computer;

import java.io.BufferedWriter;
import java.nio.Buffer;
import java.util.List;

public interface ComputerInt {

    public void printMemory(BufferedWriter wr);
    public void printState(BufferedWriter wr);
    public void printSummaryState(BufferedWriter wr, int count);
    
    //get method
    public String getMem(int index);
    public String getReg(int index);
    public int getPC();
    public Boolean getEnd();
    public int getMemLen();
    public int getNumPrintLoop();

    //set method
    public void setMem(int index, String binaryCode);
    public void setReg(int index, String binaryCode);
    public void setPC(int index);
    public void setEnd(Boolean boolVal);
    public void setNumPrintLoop(int num);
}
