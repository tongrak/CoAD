package CodeTesting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import aux_use.ALU;

public class ALUTest {
    
    //test add
    @Test
    public void testAdd(){
        // (+,+) case
        assertEquals("00000000000000000000000000000011", ALU.validateAdd("00000000000000000000000000000001", "00000000000000000000000000000010"));
        assertEquals("00000000000000000000000000000100", ALU.validateAdd("00000000000000000000000000000001", "00000000000000000000000000000011"));

        // (-,+) case
        assertEquals("00000000000000000000000000001010", ALU.validateAdd("11111111111111111111111111011000", "00000000000000000000000000110010"));
        assertEquals("11111111111111111111111111001110", ALU.validateAdd("11111111111111111111111110011100", "00000000000000000000000000110010"));

        // (+,-) case
        assertEquals("00000000000000000000000000011011", ALU.validateAdd("00000000000000000000000000011110", "11111111111111111111111111111101"));

        //(-,-) case
        assertEquals("11111111111111111111111111111100", ALU.validateAdd("11111111111111111111111111111111", "11111111111111111111111111111101") );
    }

    @Test
    public void testSub(){
        // (+,+) case
        assertEquals("00000000000000000000000001011010", ALU.validateSub("00000000000000000000000001100100", "00000000000000000000000000001010"));
        assertEquals("11111111111111111111111111101100", ALU.validateSub("00000000000000000000000000000000", "00000000000000000000000000010100"));
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 

        // (-,+) case
        assertEquals("11111111111111111111111111011000", ALU.validateSub("11111111111111111111111111110110", "00000000000000000000000000011110"));
        assertEquals("11111111111111111111111101111110", ALU.validateSub("11111111111111111111111110110000", "00000000000000000000000000110010"));
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 

        // (+,-) case
        assertEquals("00000000000000000000000001011010", ALU.validateSub("00000000000000000000000000101000", "11111111111111111111111111001110"));
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 

        // (-,-) case
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 
        // assertEquals("", ALU.validateSub("", "")); 
    }

    @Test
    public void testNand(){
        assertEquals("01011011", ALU.validateNand("10110110", "11101100"));
        assertEquals("11101010", ALU.validateNand("01011101", "10010101"));
        assertEquals("10110101", ALU.validateNand("01011011", "11101010"));
        assertEquals("01110111", ALU.validateNand("11011011", "10001000"));
        assertEquals("11111111", ALU.validateNand("11100101", "00011010"));
        assertEquals("10001000", ALU.validateNand("11111111", "01110111"));
    }
}
