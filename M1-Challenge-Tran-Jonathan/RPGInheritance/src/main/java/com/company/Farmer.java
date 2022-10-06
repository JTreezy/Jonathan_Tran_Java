package com.company;

public class Farmer extends Person {
    int strength = 75;
    int health = 100;
    int stamina = 75;
    int speed = 10;
    int attackPower = 1;
    boolean running = false;
    public boolean arrested = false;
    boolean plowing = false;
    boolean harvesting = false;

    public void setArrested(boolean arrested) {
        this.arrested = arrested;
    }
}
