package assembler;

public class AssemblyParser {

    public AssemblyParser() {
        // this.labelMap = labelMap;
    }

    private String two_complement(String bin, int bit) {
        // System.out.println("int_bin: " + bin);
        if (bin.length() < bit) bin = String.format("%" + String.valueOf(bit) + "s", bin).replaceAll(" ", "0");
        String new_bin = new String();
        String[] int_bin = bin.split("\\s+|");
        boolean status = false;
        for (int i=bin.length()-1;i>=0;i--) {
            if (status) {
                if (int_bin[i].equals("1")) int_bin[i] = "0";
                else if (int_bin[i].equals("0")) int_bin[i] = "1";
            }
            if (int_bin[i].equals("1")) status = true;
        }
        for (int i=0;i<int_bin.length;i++) {
            new_bin += int_bin[i];
        }
        // for check
        // System.out.println("new_bin.length(): " + new_bin.length());
        // System.out.println("new_bin: " + new_bin);
        return new_bin;
    }

    /** Converting given set of token of string into binary string.
     * @param Token
     * @return binary string
     */
    public String InstructionsTobin32(String[] Token){
        String bin_32 = new String();
        if(Token[0].equals("add")  || Token[0].equals("nand")) {
            bin_32 = R_Type(Token);
        }else if (Token[0].equals("lw")  || Token[0].equals("sw") || Token[0].equals("beq")) {
            bin_32 = I_Type(Token);
        }else if (Token[0].equals("jalr")) {
            bin_32 = J_Type(Token);
        }else if (Token[0].equals("halt") || Token[0].equals("noop")) {
            bin_32 = O_Type(Token);
        }else if (Token[0].equals(".fill")) {
            bin_32 = fill(Token);
        }

        // for check
        // System.out.println(bin_32.length());
        // System.out.println(bin_32);
        return bin_32;
    }

    ////////////////////////    R_Type    ///////////////////////////////
    /** Converting checked tokens according to R format
     * @param R_Token
     * @return
     */
    private String R_Type(String[] R_Token) {
        String binary = new String();
        String hexadecimal = new String();
        String rd, rs1, rs2;

        // get value from R_Token
        rd = Integer.toBinaryString(Integer.parseInt(R_Token[1]));
        rd = String.format("%3s", rd).replaceAll(" ", "0");
        rs2 = Integer.toBinaryString(Integer.parseInt(R_Token[3]));
        rs2 = String.format("%3s", rs2).replaceAll(" ", "0");
        rs1 = Integer.toBinaryString(Integer.parseInt(R_Token[2]));
        rs1 = String.format("%3s", rs1).replaceAll(" ", "0");

        // set bit[31:25] to 0
        binary = String.format("%7s", binary).replaceAll(" ", "0");

        // set opcode bit[24:22]
        switch(R_Token[0]) {
            case "add" -> {
                // set opcode bit[24:22] to 000
                binary += "000";
            }
            case "nand" -> {
                // set opcode bit[24:22] to 001
                binary += "001";
            }
        }

        // set rs2 bit[21:19]
        binary += rs2;
        // set rs1 bit[18:15]
        binary += rs1;
        // set bit[15:3] to 0 
        binary += "0000000000000";
        // set rd bit[2:0]
        binary += rd;
        // get hexadecimal
        hexadecimal = Integer.toHexString(Integer.parseInt(binary,2));

         // for check
        //  System.out.println("rd: " + rd);
        //  System.out.println("rs1: " + rs1);
        //  System.out.println("rs2: " + rs2);
        //  System.out.println("bin32.length(): " + binary.length());
        //  System.out.println("intruction(bin): " + binary);
        //  System.out.println("intruction(hex): " + "0x" + hexadecimal);
        return binary;
    }

    ////////////////////////    I_Type    ///////////////////////////////
    /** Converting checked tokens according to I format
     * @param R_Token
     * @return
     */
    private String I_Type(String[] I_Token) {
        String binary = new String();
        String hexadecimal = new String();
        String rs2, rs1, imm;

        // get value from R_Token
        if(Integer.parseInt(I_Token[3]) < 0) {
            imm = Integer.toBinaryString(Integer.parseInt(I_Token[3])*(-1));
            imm = two_complement(imm, 16);
        }else {
            imm = Integer.toBinaryString(Integer.parseInt(I_Token[3]));
            imm = String.format("%16s", imm).replaceAll(" ", "0");
        }
        rs2 = Integer.toBinaryString(Integer.parseInt(I_Token[1]));
        rs2 = String.format("%3s", rs2).replaceAll(" ", "0");
        rs1 = Integer.toBinaryString(Integer.parseInt(I_Token[2]));
        rs1 = String.format("%3s", rs1).replaceAll(" ", "0");

        // set bit[31:25] to 0
        binary = String.format("%7s", binary).replaceAll(" ", "0");

        // set opcode bit[24:22]
        switch(I_Token[0]) {
            case "lw" -> {
                // set opcode bit[24:22] to 010
                binary += "010";
            }
            case "sw" -> {
                // set opcode bit[24:22] to 011
                binary += "011";
            }
            case "beq" -> {
                // set opcode bit[24:22] to 100
                binary += "100";
            }
        }

        // set rs2 bit[21:19]
        binary += rs2;
        // set rs1 bit[18:15]
        binary += rs1;
        // set imm bit[15:0] 
        binary += imm;
      
        // get hexadecimal
        hexadecimal = Integer.toHexString(Integer.parseInt(binary,2));

         // for check
        //  System.out.println("rs1: " + rs1);
        //  System.out.println("rs2: " + rs2);
        //  System.out.println("bin32.length(): " + binary.length());
        //  System.out.println("intruction(bin): " + binary);
        //  System.out.println("intruction(hex): " + "0x" + hexadecimal);
        return binary;
    }

    ////////////////////////    J_Type    ///////////////////////////////
    /** Converting checked tokens according to J format
     * @param R_Token
     * @return
     */
    private String J_Type(String[] J_Token) {
        String binary = new String();
        String hexadecimal = new String();
        String rs2, rs1;

        // get value from R_Token
        rs2 = Integer.toBinaryString(Integer.parseInt(J_Token[1]));
        rs2 = String.format("%3s", rs2).replaceAll(" ", "0");
        rs1 = Integer.toBinaryString(Integer.parseInt(J_Token[2]));
        rs1 = String.format("%3s", rs1).replaceAll(" ", "0");

        // set bit[31:25] to 0
        binary = String.format("%7s", binary).replaceAll(" ", "0");

        // set opcode bit[24:22]
        switch(J_Token[0]) {
            case "jalr" -> {
                // set opcode bit[24:22] to 101
                binary += "101";
            }
        }

        // set rs2 bit[21:19]
        binary += rs2;
        // set rs1 bit[18:15]
        binary += rs1;  
        // set bit[15:0] to 0 
        binary += "0000000000000000";
      
        // get hexadecimal
        hexadecimal = Integer.toHexString(Integer.parseInt(binary,2));

         // for check
        //  System.out.println("rs1: " + rs1);
        //  System.out.println("rs2: " + rs2);
        //  System.out.println("bin32.length(): " + binary.length());
        //  System.out.println("intruction(bin): " + binary);
        //  System.out.println("intruction(hex): " + "0x" + hexadecimal);
        return binary;
    }
   
    ////////////////////////    O_Type    ///////////////////////////////
    /** Converting checked tokens according to O format
     * @param R_Token
     * @return
     */
    private String O_Type(String[] O_Token) {
        String binary = new String();
        String hexadecimal = new String();

        // set bit[31:25] to 0
        binary = String.format("%7s", binary).replaceAll(" ", "0");

        // set opcode bit[24:22]
        switch(O_Token[0]) {
            case "halt" -> {
                // set opcode bit[24:22] to 110
                binary += "110";
            }
            case "noop" -> {
                // set opcode bit[24:22] to 111
                binary += "111";
            }
        }

        // set bit[15:0] to 0 
        binary += "0000000000000000000000";
      
        // get hexadecimal
        hexadecimal = Integer.toHexString(Integer.parseInt(binary,2));

         // for check
        //  System.out.println("bin32.length(): " + binary.length());
        //  System.out.println("intruction(bin): " + binary);
        //  System.out.println("intruction(hex): " + "0x" + hexadecimal);
        return binary;
    }

    ////////////////////////    fill    ///////////////////////////////
    /** Converting checked tokens according to .fill
     * @param R_Token
     * @return
     */
    private String fill(String[] fill_Token) {
        String binary = new String();
        String bit_0to27 = new String();
        String bit_28to31 = new String();
        String hexadecimal = new String();
        String imm;

        // get value from R_Token
        if(Integer.parseInt(fill_Token[1]) < 0) {
            imm = Integer.toBinaryString(Integer.parseInt(fill_Token[1])*(-1));
            bit_0to27 = two_complement(imm, 32);
            bit_28to31 += bit_0to27.charAt(0);
            bit_28to31 += bit_0to27.charAt(1);
            bit_28to31 += bit_0to27.charAt(2);
            bit_28to31 += bit_0to27.charAt(3);
            bit_0to27 = two_complement(imm, 28);
            // get hexadecimal
            hexadecimal = Integer.toHexString(Integer.parseInt(bit_28to31,2));
            hexadecimal += Integer.toHexString(Integer.parseInt(bit_0to27,2));
            // get binary
            binary = bit_28to31 + bit_0to27;
        }else {
            imm = Integer.toBinaryString(Integer.parseInt(fill_Token[1]));
            binary = String.format("%32s", imm).replaceAll(" ", "0");
            // get hexadecimal
            hexadecimal = Integer.toHexString(Integer.parseInt(binary,2));
        }
         // for check
        //  System.out.println("bin32.length(): " + binary.length());
        //  System.out.println("intruction(bin): " + binary);
        //  System.out.println("intruction(hex): " + "0x" + hexadecimal);
        return binary;
    }
}
