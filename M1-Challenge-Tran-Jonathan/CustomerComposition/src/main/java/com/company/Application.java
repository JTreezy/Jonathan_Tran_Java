package com.company;

public class Application {
    public static void main(String[] args) {
        Address address = new Address("a","b","me","eight","zip");
        Customer myFirstCustomer = new Customer("Bob", "Bob","email","phone",true,address, address);
        System.out.println(myFirstCustomer);
    }
}
