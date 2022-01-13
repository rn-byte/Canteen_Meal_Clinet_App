package com.inim.canteenmeal;

public class ModelClassForOrder {
    String date;
    String food;
    String name,phone;
    String orderId;
    String price;
    String qty;
    long totalPrice;
    String status;

    public ModelClassForOrder() {
    }

    public ModelClassForOrder(String date, String food, String orderId, String price, String qty, long totalPrice,String name,String phone,String status) {
        this.date = date;
        this.food = food;
        this.orderId = orderId;
        this.price = price;
        this.qty = qty;
        this.totalPrice = totalPrice;
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
