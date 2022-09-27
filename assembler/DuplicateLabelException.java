package assembler;

public class DuplicateLabelException extends Exception {
    public DuplicateLabelException(String message) {
        super(message);
    }

    public DuplicateLabelException(){
        
    }
}
