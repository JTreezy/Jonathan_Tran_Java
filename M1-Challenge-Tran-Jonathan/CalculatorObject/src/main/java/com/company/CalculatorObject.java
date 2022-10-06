package com.company;


public class CalculatorObject {
    public int add(int a, int b){return a +b;}
//    public long add(long a, long b){return a+b;}
//    public float add(float a, float b){return a+b;}
    public double add(double a, double b){return a+b;}

    public int subtract(int a, int b){return a-b;}
//    public long subtract(long a, long b){return a-b;}
//    public float subtract(float a, float b){return a-b;}
    public double subtract(double a, double b){return a-b;}

    public int multiply(int a, int b){return a*b;}
//    public long multiply(long a, long b){return a*b;}
//    public float multiply(float a, float b){return a*b;}
    public double multiply(double a, double b){return a*b;}

    public int divide(int a, int b){return a/b;}
//    public long divide(long a, long b){return a/b;}
//    public float divide(float a, float b){return a/b;}
    public double divide(double a, double b){return a/b;}

    public static void main(String[] args) {
        CalculatorObject myCalculator = new CalculatorObject();

        System.out.println("The answer to 1 + 1 is " + myCalculator.add(1,1) );

        System.out.println("The answer to 23 - 52 is " + myCalculator.subtract(23,52));

        System.out.println("The answer to 34 * 2 is " + myCalculator.multiply(34,2));

        System.out.println("The answer to 12 / 3 is " + myCalculator.divide(12,3));

        System.out.println("The answer to 12 / 7 is " + myCalculator.divide(12,7));

        System.out.println("The answer to 3.4 + 2.3 is " + String.format("%.1f", myCalculator.add(3.4,2.3)));

        System.out.println("The answer to 6.7 * 4.4 is " + String.format("%.1f", myCalculator.multiply(6.7, 4.4)));

        System.out.println("The answer to 5.5 - 0.5 is " + myCalculator.subtract(5.5,0.5));

        System.out.println("The answer to 10.8 / 2.2 is " + String.format("%.1f", myCalculator.divide(10.8, 2.2)));

    }
}
