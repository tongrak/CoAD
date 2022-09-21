public interface ComputerInt {
    
    //get method
    public String getInstMem(int index);
    public String getMem(int index);
    public String getReg(int index);
    public int getPC();
    public Boolean getEnd();

    //set method
    public void setInstMem(int index, String binaryCode);
    public void setMem(int index, String binaryCode);
    public void setReg(int index, String binaryCode);
    public void setPC(int index);
    public void setEnd(Boolean boolVal);
}
