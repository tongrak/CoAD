public interface ComputerInt {
    
    public String getInstMem(int index);
    public String getMem(int index);
    public String getReg(int index);
    public int getPC();

    public void setInstMem(int index, String binaryCode)
}
