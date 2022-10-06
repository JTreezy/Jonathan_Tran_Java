package com.company;

import java.sql.SQLOutput;

public class Constable extends Person {
    int strength = 60;
    int health = 100;
    int stamina = 60;
    int speed = 20;
    int attackPower = 5;
    boolean running = false;
    boolean arrested = false;

    String hasJurisdiction;


    public void arrestAnotherCharacter(Person person){
        System.out.println("Halt, " + person.name + " you are under arrest!");
        person.setArrested(true);
    }
}
