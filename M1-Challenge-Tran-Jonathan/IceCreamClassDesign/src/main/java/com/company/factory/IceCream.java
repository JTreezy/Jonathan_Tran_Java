package com.company.factory;

import java.util.Arrays;

public class IceCream {
    private String flavor;
    private int salePrice;
    private double productionCost;
    private double productionTime;
    private String[] ingredients;

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public double getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(double productionCost) {
        this.productionCost = productionCost;
    }

    public double getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(double productionTime) {
        this.productionTime = productionTime;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    //test to call method and see expected result
    public boolean containsNuts(){
        if(Arrays.asList(ingredients).contains("nuts")){
            return true;
        } else{
            return false;
        }
    }

    public boolean isVegan(){
        if(Arrays.asList(ingredients).contains("milk")){
            return true;
        } else{
            return false;
        }
    }
    public double productionValue(){
        return salePrice - productionCost;
    }

}
