package aux_use;

import java.lang.Math;

public class Helper {
    public static int binToInt(String bin) {
        if(bin == "" || bin == " ")
            return 0;
        String signBit = bin.substring(0,1);
        String data = bin.substring(1,bin.length());
        if(bin.length() <= 3)
            return Integer.parseInt(data,2);
        if(signBit.equals("0"))
        {
            return Integer.parseInt(data,2);
        }
        else if(signBit.equals("1"))
        {
            if(Integer.parseInt(data) == 0)
            {
                return -Integer.parseInt(ALU.two_complement(data),2)-1;
            }
            return -Integer.parseInt(ALU.two_complement(data),2);
        }
        return 0;
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
        String a = "01111111111111111111111111111111";
        String b = "11111111111111111111111111111111";
        // System.out.println(a);
        // System.out.println(ALU.two_complement(a));
        // System.out.println(binToInt(ALU.two_complement(a)));
        // System.out.println(Integer.parseInt("11"));
        // System.out.println(binToInt("01111111111111111111111111111111"));
        // System.out.println(binToInt("10000000000000000000000000000000"));
        System.out.println(binToInt(" "));
    }
}
