package aux_use;
import java.lang.Math;

public class Helper {
    public static int binToInt(String bin)
    {
        double res = 0;
        for(int i=0;i<bin.length();i++)
        {
            char bit = bin.charAt(i);
            if(bit == '1')
            {
                res = res + Math.pow(2, bin.length()-i-1);
            }
        }
        return (int) res;
    }
}
