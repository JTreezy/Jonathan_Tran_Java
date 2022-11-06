package com.company;

public class Warrior extends Person {
    int shieldStrength = 100;

    public Warrior() {
        this.setStrength(75);
        this.setHealth(100);
        this.setStamina(100);
        this.setSpeed(50);
        this.setAttackPower(10);
        this.setRunning(false);
        this.setArrested(false);
    }

    public int getShieldStrength(int shieldStrength){
        return shieldStrength;
    }

    public void setShieldStrength(int shieldStrength){
        this.shieldStrength = shieldStrength;
    }


}
