package machine;

import aux_use.ALU;
import aux_use.Helper;
import computer.ComputerInt;

// import aux_use.ALU;

public class Machine {
    // private static ComputerInt com;

    public static void read_store(ComputerInt comRead) {
        // com = comRead;
    }

    public static void Inst_compute(String inst,ComputerInt com) {
        String opcode = inst.substring(7, 10);
        if (opcode.equals("000") || opcode.equals("001")) {
            RType(inst, opcode,com);
        } else if (opcode.equals("010") || opcode.equals("011") || opcode.equals("100")) {
            IType(inst,opcode,com);
            if(opcode.equals("100"))
                return;
        } else if (opcode.equals("101")) {
            JType(inst, opcode,com);
            return;
        } else {
            OType(inst, opcode,com);
            if(opcode.equals("110"))
                return;
        }
        com.setPC(com.getPC()+1);
    }

    public static void RType(String instruction, String opcode,ComputerInt com) {
        String rs = instruction.substring(10, 13);
        String rt = instruction.substring(13, 16);
        String rd = instruction.substring(29, 32);

        if (!rd.equals("000")) {
            String regA = com.getReg(Helper.binToInt(rs));
            String regB = com.getReg(Helper.binToInt(rt));
            String res = "";
            String op = "";
            if (opcode.equals("000"))
                op = "add";
            else
                op = "nand";
            res = ALU.operation(regA, regB, op);
            com.setReg(Helper.binToInt(rd), res);
        }
    }

    public static void IType(String instruction,String opcode,ComputerInt com) {
        String rs = instruction.substring(10, 13);
        String rt = instruction.substring(13, 16);
        String offset = instruction.substring(16, 32);
        String regA = com.getReg(Helper.binToInt(rs));
        String regB = com.getReg(Helper.binToInt(rt));
        String loc = ALU.operation(regA, offset,"add");
        if(opcode.equals("010") && !rt.equals("000"))
        {
            com.setReg(Helper.binToInt(rt), com.getMem(Helper.binToInt(loc)));
        }
        else if(opcode.equals("011"))
        {
            com.setMem(Helper.binToInt(loc),com.getReg(Helper.binToInt(rt)));
        }
        else if(opcode.equals("100"))
        {
            if(ALU.operation(regA, regB, "compare").equals("1"))
            {
                int offsetField = Helper.binToInt(ALU.signExtend(offset));
                com.setPC(com.getPC()+1+offsetField);
            }
            else
                com.setPC(com.getPC()+1);
        }
    }

    public static void JType(String instruction, String opcode,ComputerInt com) {
        String rs = instruction.substring(10, 13);
        String rt = instruction.substring(13, 16);

        String regA = com.getReg(Helper.binToInt(rs));
        int PC = com.getPC() + 1;
        if(!rt.equals("000"))
            com.setReg(Helper.binToInt(rt), Helper.IntTobin(PC));

        if (rs.equals(rt)) {
            com.setPC(PC);
        } else {
            com.setPC(Helper.binToInt(regA));
        }
    }

    public static void OType(String instruction, String opcode,ComputerInt com) {

        if (opcode.equals("110")) {
            com.setPC(com.getPC() + 1);
            com.setEnd(true);
        }
    }
}
