package com.dt.demo.sampleusecase.domain;

import java.util.Date;

public class Payment {

    private String userId;
    private double amount;
    private String method;
    private Date paymentTime;
    private String status;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "userId='" + userId + '\'' +
                ", amount=" + amount +
                ", method='" + method + '\'' +
                ", paymentTime=" + paymentTime +
                ", status='" + status + '\'' +
                '}';
    }
}
