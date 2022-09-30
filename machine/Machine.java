package machine;

import aux_use.ALU;
import aux_use.Helper;
import computer.ComputerInt;

// import aux_use.ALU;

public class Machine {
    private static ComputerInt com;

    public static void read_store(ComputerInt comRead) {
        com = comRead;
    }

    public static void Inst_compute(String inst) {
        String opcode = inst.substring(7, 10);
        if (opcode.equals("000") || opcode.equals("001")) {
            RType(inst, opcode);
        } else if (opcode.equals("101")) {
            JType(inst, opcode);
        } else {
            OType(inst, opcode);
        }

    }

    public static void RType(String instruction, String opcode) {
        String rs = instruction.substring(10, 13);
        String rt = instruction.substring(13, 16);
        String rd = instruction.substring(29, 32);

        if (!rd.equals("000")) {
            String rs1 = com.getReg(Helper.binToInt(rs));
            String rs2 = com.getReg(Helper.binToInt(rt));
            String res = "";
            String op = "";
            if (opcode.equals("000"))
                op = "add";
            else
                op = "nand";
            res = ALU.operation(rs1, rs2, op);
            com.setReg(Helper.binToInt(rd), res);
        }
    }

    public void IType(String instruction) {
        String opcode = instruction.substring(7, 10);
        String rs = instruction.substring(10, 13);
        String rt = instruction.substring(13, 16);
        String offset = instruction.substring(16, 32);

    }

    public static void JType(String instruction, String opcode) {
        String rs = instruction.substring(10, 13);
        String rt = instruction.substring(13, 16);

        String regA = com.getReg(Helper.binToInt(rs));
        String regB = com.getReg(Helper.binToInt(rt));
        int PC = com.getPC() + 1;
        com.setReg(Helper.binToInt(rt), Helper.IntTobin(PC));

        if (regA.equals(regB)) {
            com.setPC(PC);
        } else {
            com.setPC(Helper.binToInt(rs));
        }
    }

    public static void OType(String instruction, String opcode) {

        if (opcode.equals("110")) {
            com.setPC(com.getPC() + 1);
            com.setEnd(true);
        }
    }
}
