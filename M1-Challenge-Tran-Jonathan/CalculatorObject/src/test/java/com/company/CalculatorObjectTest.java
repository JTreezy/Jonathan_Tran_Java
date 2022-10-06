package com.company;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorObjectTest {
    CalculatorObject myCalculator = null;
    @Before
    public void init(){
        myCalculator = new CalculatorObject();
    }
    @Test
    public void threePlusThreeShouldEqualSix(){
        assertEquals(6,myCalculator.add(3,3));
    }
    @Test
    public void fiveMinusThreeShouldEqualTwo(){
        assertEquals(2, myCalculator.subtract(5,3));
    }
    @Test
    public void sevenTimesThreeShouldEqualTwentyOne(){
        assertEquals(21, myCalculator.multiply(7,3));
    }
    @Test
    public void sixteenDividedByFourShouldEqualFour(){
        assertEquals(4, myCalculator.divide(16,4));
    }



}