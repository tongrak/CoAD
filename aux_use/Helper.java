package aux_use;

import java.lang.Math;

public class Helper {
    public static int binToInt(String bin) {
        int res = 0;
        String albin = ALU.two_complement(bin);
        String signBit = bin.substring(0,1);
        if(signBit.equals("1"))
        {
            res = -Integer.parseInt(albin,2);
        }
        else res = Integer.parseInt(bin,2);
        return res;
    }

    public static String IntTobin(int inT) {
        String res = Integer.toBinaryString(inT);
        if(res.length() < 32)
        {
            res = "0"+res;
        }
        return ALU.signExtend(res);
    }
    public static void main(String[] args) {
        String a = "11111111111111111111111111111110";
        // System.out.println(a);
        // System.out.println(ALU.two_complement(a));
        // System.out.println(binToInt(ALU.two_complement(a)));
        System.out.println(binToInt(a));
    }
}
