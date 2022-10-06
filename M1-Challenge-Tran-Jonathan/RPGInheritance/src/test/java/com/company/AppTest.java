package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void shouldMakeAFarmer(){
        Farmer bob = new Farmer();
        int actualResult = bob.attackPower;
        int expectedResult = 1;
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void shouldMakeAConstable(){
        Constable tommy = new Constable();
        int actualResult = tommy.speed;
        int expectedResult = 20;
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void shouldMakeAWarrior(){
        Warrior dave = new Warrior();
        int actualResult = dave.shieldStrength;
        int expectedResult = 100;
        assertEquals(expectedResult,actualResult);
    }
    @Test
    public void shouldAttackAnotherPerson(){}
    @Test
    public void shouldArrestPerson() {
        Farmer steve = new Farmer();
        steve.name = "Steve";
        Constable john = new Constable();
        john.name = "John";
        john.arrestAnotherCharacter(steve);
        boolean expectedResult = true;
        assertEquals(expectedResult, steve.arrested);
    }
}