package com.company;

abstract class Person {
    String name;
    int strength;
    int health;
    int stamina;
    int speed;
    int attackPower;
    boolean running;
    boolean arrested;

    public void attackAnotherCharacter(String target){
        System.out.println("Hello there, "+ target +" prepare to do battle, for I am " + name);
    }

    public void setArrested(boolean arrested) {
        this.arrested = arrested;
    }
}
