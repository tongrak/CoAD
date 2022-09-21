import java.util.ArrayList;

public class Computer {
    private Boolean end = false;
    private int PC = 0;
    private ArrayList<String> instMem;
    private String[] mem;
    private String[] reg;

    public Computer(){
        mem = new String[10];
        reg = new String[8];
        instMem = new ArrayList<String>();
    }


}
