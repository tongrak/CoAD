import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import assembler.Assembler;
import computer.Computer;
import machine.Machine;

public class Main {

  private Assembler assembler;
  private Computer computer;
  private FileWriter file;
  private BufferedWriter writer;
  private String outputDst = "..//CoAd//output.txt";
  private String fileDst = "..//CoAD//AssemblyCode//MultiplicationAssem.txt";
  private String finalDest = "..//CoAD//MachineCode//CurrentMachineCode.txt";

  private void initial() {
    try{
      assembler = new Assembler(finalDest);
      computer = new Computer();
  
      //for writer
      file = new FileWriter(outputDst);
      writer = new BufferedWriter(file);
    }
    catch(IOException e){
      System.out.println("File location is not correct.");
    }
  }

  public void runProgram() {
    try{
      initial();
      assembler.interpretAndSave(fileDst, computer);
      int count = 0;
      computer.printMemory(writer);
      //do all instruction.
      while(!computer.getEnd()){
        int pc = computer.getPC();
        String instr = computer.getMem(pc);
        //show a state before execution.
        computer.printState(writer);
        //do a instruction
        Machine.Inst_compute(instr, computer);
        count += 1;
      }
      computer.printSummaryState(writer, count);

      //show a state after finish program.
      computer.printState(writer);
      writer.close();
    }
    catch(IOException e){
      System.out.println("Writer can't close because of @variable writer in runProgram()");
    }
  }
    public static void main(String[] args) {
      Main mainProgram = new Main();
      mainProgram.runProgram();
    }
  }