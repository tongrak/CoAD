package machine;

import computer.ComputerInt;

// import aux_use.ALU;

public class Machine {
    private static ComputerInt com;

    public static void read_store(ComputerInt comRead)
    {
        com = comRead;
    }

    public static void Inst_compute(String inst)
    {
        String opcode = inst.substring(7, 10);
        if(opcode.equals("000") || opcode.equals("001"))
            RType(inst,opcode);
    }

    public static void RType(String instruction,String opcode) {
        String rs = instruction.substring(10, 13);
        String rt = instruction.substring(13, 16);
        String rd = instruction.substring(29, 32);

        if(! rd.equals("000"))
        {
            if(opcode.equals("000"))
            {
                String rs1 = com.getReg(0);
                String rs2 = com.getReg(0);
                com.setReg(0, "0000");
            }
        }
    }

    public void IType(String instruction) {
        String opcode = instruction.substring(7, 10);
        String rs = instruction.substring(10, 13);
        String rt = instruction.substring(13, 16);
        String offset = instruction.substring(16, 32);

    }

    public void JType(String instruction) {
        String opcode = instruction.substring(7, 10);
        String rs = instruction.substring(10, 13);
        String rt = instruction.substring(13, 16);
    }

    public void OType(String instruction) {
        String opcode = instruction.substring(7, 10);
    }
}
