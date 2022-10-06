package com.company.pointofsale;

public class IceCream {
    private String flavor;
    private double price;
    private int layers;

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLayers() {
        return layers;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }

    public double promotionalPrice(){
        return price * .75;
    }

    public double priceWithTax(){
        return price * 1.06;

    }
    public boolean hasLayers(){
        if (layers > 1){
            return true;
        } else{
            return false;
        }
    }
}
