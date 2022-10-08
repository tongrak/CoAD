import assembler.Assembler;
import computer.Computer;
import machine.Machine;

public class Main {

  private Assembler assembler;
  private Machine machine;
  private Computer computer;
  private String fileDst = ".\\AssemblyCode\\TestingText.txt";

  private void initial(){
    assembler = new Assembler();
    // machine = new Machine();
    computer = new Computer();
  }

  public void runProgram(){
    initial();
    assembler.interpretAndSave(fileDst, computer);

    while(!computer.getEnd()){
      int pc = computer.getPC();
      String instr = computer.getMem(pc);
      Machine.Inst_compute(instr);
      computer.printState();
    }
  }
    public static void main(String[] args) {
      Main mainProgram = new Main();
      mainProgram.runProgram();
    }
  }