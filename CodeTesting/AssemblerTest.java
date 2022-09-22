package CodeTesting;

import assembler.*;
import computer.*;

public class AssemblerTest {
    static AssemblerInt assem = new Assembler();
    static ComputerInt simCom = new Computer();

    public static void main(String[] args) {
        System.out.println("Testing start");
        assem.interpretAndSave("C:\\Users\\DELL\\OneDrive - Chiang Mai University\\University File\\Year 65 (2022)\\1_65\\CPE304 [Sec001]\\Proj\\CoAD\\AssemblyCode\\TestingText.txt", simCom);
    }

}
