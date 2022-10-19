package CodeTesting;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import aux_use.Helper;

public class HelperTest {

    @Test
    public void TestBitToInt() {
        assertEquals(1, Helper.binToInt("00000000000000000000000000000001"));
        assertEquals(2, Helper.binToInt("00000000000000000000000000000010"));
        assertEquals(0, Helper.binToInt("00000000000000000000000000000000"));
        assertEquals(15, Helper.binToInt("00000000000000000000000000001111"));
        assertEquals(17, Helper.binToInt("00000000000000000000000000010001"));
        assertEquals(-1, Helper.binToInt("11111111111111111111111111111111"));
        assertEquals(-1432354816, Helper.binToInt("10101010101000000000000000000000"));
        assertEquals(1067188239, Helper.binToInt("00111111100111000000000000001111"));
        assertEquals(-22315, Helper.binToInt("11111111111111111010100011010101"));
        assertEquals(338034, Helper.binToInt("00000000000001010010100001110010"));
    }

    @Test
    public void TestIntToBit() {
        assertEquals("00000000000000000000000000000000", Helper.IntTobin(0));
        assertEquals("00000000000000000000000000000010", Helper.IntTobin(2));
        assertEquals("11111111111111111111111111110001", Helper.IntTobin(-15));
        assertEquals("00000000001000110110101101010001", Helper.IntTobin(2321233));
        assertEquals("00000000000001001110011110000100", Helper.IntTobin(321412));
        assertEquals("00000000000000000000000000010100", Helper.IntTobin(20));
        assertEquals("11111111111111111111110000000001", Helper.IntTobin(-1023));
        assertEquals("11111111111111111100111111100111", Helper.IntTobin(-12313));
        assertEquals("00000000000000000000000001111011", Helper.IntTobin(123));
        assertEquals("00000000000000000001001110010100", Helper.IntTobin(5012));

    }
}
