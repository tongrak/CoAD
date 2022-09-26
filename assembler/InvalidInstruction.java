package assembler;

public class InvalidInstruction extends Exception {
    public InvalidInstruction(String msg){
        super(msg);
    }
}
