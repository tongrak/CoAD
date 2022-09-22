package assembler;

import computer.ComputerInt;

public interface AssemblerInt {

    // read and interet assembly file in fileAddress and store it in inObject.InstucMem.
    void interpretAndSave(String fileAddress ,ComputerInt inObject);
}
