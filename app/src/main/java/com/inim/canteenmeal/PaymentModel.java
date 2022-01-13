package com.inim.canteenmeal;

public class PaymentModel {
    long amount;
    String mobile;
    String product_identity;
    String product_name;
    String token;
    PaymentModel()
    {}
    public PaymentModel(long amount, String mobile, String product_identity, String product_name, String token) {
        this.amount = amount;
        this.mobile = mobile;
        this.product_identity = product_identity;
        this.product_name = product_name;
        this.token = token;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProduct_identity() {
        return product_identity;
    }

    public void setProduct_identity(String product_identity) {
        this.product_identity = product_identity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
