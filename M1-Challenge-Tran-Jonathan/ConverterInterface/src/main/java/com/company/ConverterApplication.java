package com.company;

import com.company.interfaces.Converter;
import com.company.interfaces.ConverterIf;
import com.company.interfaces.ConverterSwitch;


public class ConverterApplication{

    public static void main(String[] args) {
        Converter myConverter = new ConverterIf();

        for (int i = 1; i < 13; i++){
            System.out.println("This month converter is using the Converter If java class!");
            System.out.println(myConverter.convertMonth(i));
        }

        for (int i = 1; i < 8; i++){
            System.out.println("This day converter is using the Converter If java class!");
            System.out.println(myConverter.convertDay(i));
        }


        Converter myConverter2 = new ConverterSwitch();

        for (int i = 1; i < 13; i++){
            System.out.println("This month converter is using the Converter Switch java class!");
            System.out.println(myConverter2.convertMonth(i));
        }

        for (int i = 1; i < 8; i++){
            System.out.println("This day converter is using the Converter Switch java class!");
            System.out.println(myConverter2.convertDay(i));
        }

    }



}