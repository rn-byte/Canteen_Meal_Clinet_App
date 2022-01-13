package com.inim.canteenmeal;

public class CartModel {
    String Food, Date,uName;
    int qty,Price,OrderNum;
    long TotalPrice;

    public CartModel(){

    }

    public CartModel(String Food, int qty, int Price, long TotalPrice,int OrderNum,String Date ) {
        this.Food = Food;
        this.qty = qty;
        this.Price = Price;
        this.TotalPrice = TotalPrice;
        this.OrderNum = OrderNum;
        this.Date = Date;
    }



    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFood() {
        return Food;
    }

    public void setFood(String food) {
        Food = food;
    }

    public int getqty() {
        return qty;
    }

    public void setQqy(int qtty) {
        qty = qtty;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public long getTotalPrice() {
        return TotalPrice;
    }

    public int getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    public void setTotalPrice(long totalPrice) {
        TotalPrice = totalPrice;
    }
}
