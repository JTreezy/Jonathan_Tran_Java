package com.company.RESTwebservice.controllers;

import com.company.RESTwebservice.models.Month;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class MonthController extends Exception {

    private static List<Month> monthList = new ArrayList<>(Arrays.asList(
            new Month(1,"January"),
            new Month(2,"February"),
            new Month(3,"March"),
            new Month(4,"April"),
            new Month(5,"May"),
            new Month(6,"June"),
            new Month(7,"July"),
            new Month(8,"August"),
            new Month(9,"September"),
            new Month(10,"October"),
            new Month(11,"November"),
            new Month(12,"December")
    ));

//    @RequestMapping(value="/month", method = RequestMethod.POST)
//    @ResponseStatus(value= HttpStatus.CREATED)
//    public Month postMonth(@RequestBody Month month){
//        monthList.add(month);
//        return month;
//    }

//    @RequestMapping(value="/month", method = RequestMethod.GET)
//    @ResponseStatus(value = HttpStatus.OK)
//    public List<Month> getMonths(){
//        return monthList;
//    }

    @RequestMapping(value="/month/{monthNumber}", method= RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Month getMonthConvert(@PathVariable Integer monthNumber) throws IllegalArgumentException{
        //path variable is the property int number within month model
        //need to return the Month object containing the name and number of the requested month (1-January, 2-February, etc)
        for(Month month: monthList){
            if(month.getNumber() == monthNumber){
                return month;
            }
        }
        //how do I write an exception to be thrown out of the range of 1-12??
        throw new IllegalArgumentException("This is not a number that is in the calendar amount");

    }
    @RequestMapping(value="/randomMonth", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Month getRandomMonth(){
        //creating the random month number generator
        int min = 1;
        int max = 13;
        int random = (min + (int)(Math.random() * (max-min)));

        //how to get the number to select the month object and return it
        for(Month month: monthList){
            if(month.getNumber() == random){
                return month;
            }
        }
        System.out.println("Why is it out here, it shouldn't be");
        System.out.println(random);
        return null;

    }
}
