package aux_use;


public class ALU {
    private static String zero;

    public static String operation(String a,String b,String op)
    {
        String res;
        if(op.equals("add"))
            res = add(a, b);
        return "";
    }

    private static String add(String a, String b)
    {
        int carry = 0;
        String res = "";
        String b1 = String.valueOf('1');
        String b0 = String.valueOf('0');
        for(int i=a.length()-1;i>=0;i--)
        {
            char ai = a.charAt(i);
            char bi = b.charAt(i);
            System.out.println("a: "+ai+" b: "+bi+" c: "+carry);
            if(ai=='1' && bi=='1')
            {
                if(carry == 0)
                    res = b0+res;
                else
                    res = b1+res;
                carry = 1;
            }
            else if(ai =='1'|| bi=='1')
            {
                if(carry == 0)
                    res = b1+res;
                else
                {
                    res = b0+res;
                    carry = 1;
                }
            }
            else
            {
                if(carry == 1)
                {
                    res = b1+res;
                    carry = 0;
                }
                else
                    res = b0+res;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String a = "00000000000000000000000000000001";
        String b = "00000000000000000000000000000001";
        System.out.println(add(a,b));
    }
}
