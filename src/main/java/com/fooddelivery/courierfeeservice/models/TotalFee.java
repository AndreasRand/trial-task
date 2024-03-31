package com.fooddelivery.courierfeeservice.models;

public class TotalFee {
    private double amount;

    public TotalFee(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
