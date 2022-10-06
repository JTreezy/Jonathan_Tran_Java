package com.company.interfaces;

public class ConverterIf implements Converter {
    public String convertMonth(int monthNumber){
        String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String errorMessage = "Not a valid month number!";
        if (monthNumber <= monthArray.length && monthNumber > 0){
            return monthArray[monthNumber-1];
        } else{
            return errorMessage;
        }
    }

    public String convertDay(int dayNumber) {
        String[] dayArray = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String errorMessage = "Not a valid day number!";
        if (dayNumber <= dayArray.length && dayNumber > 0){
            return dayArray[dayNumber-1];
        } else{
            return errorMessage;
        }


    }
}
