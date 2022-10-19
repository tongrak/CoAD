package aux_use;

public class ALU {
    public static String operation(String a, String b, String op) {
        String res = "";
        String A = signExtend(a);
        String B = signExtend(b);
        if (op.equals("add"))
            res = add(A, B);
        else if(op.equals("sub"))
            res = sub(A, B);
        else if(op.equals("nand"))
            res = nand(A, B);
        else if(op.equals("compare"))
            res = compare(A, B);
        return res;
    }

    // compare same return 1 else 0
    public static String compare(String a,String b)
    {
        if(a.equals(b))
            return "1";
        return "0";
    }

    //extend to 32 bit
    public static String signExtend(String a)
    {
        if(a == null)
            return "00000000000000000000000000000000";
        int l = a.length();
        String res = a;
        if(a.charAt(0) == '0')
        {
            for(int i=0;i<32-l;i++)
            {
                res = "0"+res;
            }
        }
        else
            for(int i=0;i<32-l;i++)
            {
                res = "1"+res;
            }
        return res;
    }

    // simple add binary 
    private static String add(String a, String b) {
        int carry = 0;
        String res = "";
        String b1 = String.valueOf('1');
        String b0 = String.valueOf('0');
        for (int i = a.length() - 1; i >= 0; i--) {
            char ai = a.charAt(i);
            char bi = b.charAt(i);
            if (ai == '1' && bi == '1') {
                if (carry == 0)
                    res = b0 + res;
                else
                    res = b1 + res;
                carry = 1;
            } else if (ai == '1' || bi == '1') {
                if (carry == 0)
                    res = b1 + res;
                else {
                    res = b0 + res;
                    carry = 1;
                }
            } else {
                if (carry == 1) {
                    res = b1 + res;
                    carry = 0;
                } else
                    res = b0 + res;
            }
        }
        return res;
    }

    //use for test add()
    public static String validateAdd(String a, String b){
        return add(a,b);
    }
    
    // sub is add but b is negative
    private static String sub(String a, String b) {
        String nb = two_complement(b);
        String res = add(a,nb);
        return res;
    }

    //use for test sub()
    public static String validateSub(String a, String b){
        return sub(a, b);
    }

    // simple nand
    private static String nand(String a,String b)
    {
        String res = "";
        String b1 = String.valueOf('1');
        String b0 = String.valueOf('0');
        for (int i = a.length() - 1; i >= 0; i--)
        {
            char ai = a.charAt(i);
            char bi = b.charAt(i);
            if(ai == '1' && bi == '1')
                res = b0+res;
            else
                res = b1+res;
        }
        return res;
    }

    //use for test nand()
    public static String validateNand(String a, String b){
        return nand(a, b);
    }

    // not+1
    public static String two_complement(String a) {
        String res = add(not(a),"00000000000000000000000000000001");
        return res;
    }
    // simple not
    private static String not(String a) {
        String res = "";
        String b1 = String.valueOf('1');
        String b0 = String.valueOf('0');
        for (int i = a.length() - 1; i >= 0; i--) {
            char ai = a.charAt(i);
            if(ai == '1')
                res = b0+res;
            else
                res = b1+res;
        }
        return res;
    }

    // use for test not()
    public static String validateNot(String a){
        return not(a);
    }

    public static void main(String[] args) {
        String a = "010001";
        String b = "10001";
        System.out.println(operation(a, b, "add"));
        System.out.println(Integer.toBinaryString(32767));
        System.out.println(Helper.IntTobin(32767));
        System.out.println(Helper.IntTobin(-32768));
    }
}
