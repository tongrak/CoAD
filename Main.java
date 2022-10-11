import assembler.Assembler;
import computer.Computer;
import machine.Machine;

public class Main {

  private Assembler assembler;
  private Machine machine;
  private Computer computer;
  private String fileDst = "..//CoAD//AssemblyCode//MultiplicationAssem.txt";
  private String finalDest = "..//CoAD//MachineCode//CurrentMachineCode.txt";

  private void initial(){
    assembler = new Assembler(finalDest);
    // machine = new Machine();
    computer = new Computer();
  }

  public void runProgram(){
    initial();
    assembler.interpretAndSave(fileDst, computer);
    int count = 0;
    while(!computer.getEnd()){
      int pc = computer.getPC();
      String instr = computer.getMem(pc);
      Machine.Inst_compute(instr, computer);
      computer.printState();
      count += 1;
    }
    System.out.println("summary run: " + count);
  }
    public static void main(String[] args) {
      Main mainProgram = new Main();
      mainProgram.runProgram();
    }
  }