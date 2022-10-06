package com.company;

public class App {
    public static void main(String[] args) {
        Farmer steve = new Farmer();
        steve.name = "Steve";

        Constable john = new Constable();
        john.name = "John";

        steve.attackAnotherCharacter(john.name);

    }
}
