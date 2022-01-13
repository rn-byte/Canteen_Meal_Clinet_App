package com.inim.canteenmeal;

public class YourOrderCartModel {

        String Food, Date,uName;
        String qty,Price,orderId;
        long TotalPrice;

        public YourOrderCartModel(){

        }

        public YourOrderCartModel(String Food, String qty, String Price, long TotalPrice,String orderId,String Date ) {
            this.Food = Food;
            this.qty = qty;
            this.Price = Price;
            this.TotalPrice = TotalPrice;
            this.orderId = orderId;
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

        public String getqty() {
            return qty;
        }

        public void setQqy(String qtty) {
            qty = qtty;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public long getTotalPrice() {
            return TotalPrice;
        }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setTotalPrice(long totalPrice) {
            TotalPrice = totalPrice;
        }


}
