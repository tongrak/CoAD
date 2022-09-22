package assembler;

import java.util.Map;

// A -> Head Body Tail
// Head -> Label Instr | Instr
// Body -> none | Reg Reg | Reg Reg Reg | Reg Reg Val
// Tail -> none | comment
// Val -> offset | symbolic
public class AssemblyParser {
    
    Map <String, Integer> labelMap;

    public AssemblyParser(Map<String, Integer> labelMap) {
        this.labelMap = labelMap;
    }

    public String parseA(String[] Token){
        return "";
    }

    private String parseHead(){
        return null;
    }

    private String parseBody(){
        return null;
    }

    private String parseTail(){
        return null;

    }

    private String parseVal(){
        return null;

    }
}
