package machine;

// import aux_use.ALU;

public class Machine {

    public void RType(String instruction) {
        String opcode = instruction.substring(7, 10);
        String rs = instruction.substring(10, 13);
        String rt = instruction.substring(13, 16);
        String rd = instruction.substring(29, 32);
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
