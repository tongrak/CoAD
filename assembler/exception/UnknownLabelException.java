package assembler.exception;

public class UnknownLabelException extends Exception {
    public UnknownLabelException(String message) {
        super(message);
    }

    public UnknownLabelException(){
        
    }
}
