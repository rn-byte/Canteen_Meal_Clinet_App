package com.inim.canteenmeal;

public class MenuModel {
    String foodName;
    int foodPrice;
    MenuModel(){}
    MenuModel(String foodName, int foodPrice){
        this.foodName=foodName;
        this.foodPrice=foodPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

}
