package com.company;

import com.company.interfaces.Converter;
import com.company.interfaces.ConverterIf;
import com.company.interfaces.ConverterSwitch;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConverterApplicationTest {
    @Test
    public void shouldConvertIfIntToMonthString(){
        Converter myConverter = new ConverterIf();
        String actualResult = myConverter.convertMonth(3);
        String expectedResult = "March";
        assertEquals(expectedResult, actualResult);

    }
    @Test
    public void shouldConvertIfIntToDayString(){
        Converter myConverter = new ConverterIf();
        String actualResult = myConverter.convertDay(5);
        String expectedResult = "Thursday";
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void shouldConvertSwitchIntToMonthString(){
        Converter myConverter = new ConverterSwitch();
        String actualResult = myConverter.convertMonth(10);
        String expectedResult = "October";
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void shouldConvertSwitchIntToDayString(){
        Converter myConverter = new ConverterSwitch();
        String actualResult = myConverter.convertDay(1);
        String expectedResult = "Sunday";
        assertEquals(expectedResult, actualResult);
    }

}