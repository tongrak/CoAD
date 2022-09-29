import assembler.Assembler;
import computer.Computer;
import machine.Machine;

public class Main {

  private Assembler assembler;
  private Machine machine;
  private Computer computer;
  private String fileDst;

  private void initial(){
    // AssemblerInt
    // MachineInt  
    assembler = new Assembler();
    machine = new Machine();
    computer = new Computer();
  }

  public void runProgram(){
    initial();
    assembler.interpretAndSave(fileDst, computer);

    while(!computer.getEnd()){
      // machine.simulate(computer);
    }
  }
    public static void main(String[] args) {
      Main mainProgram = new Main();
    }
  }