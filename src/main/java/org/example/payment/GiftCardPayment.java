package org.example.payment;

import org.example.model.PaymentResult;

public class GiftCardPayment extends PaymentMethod{
    private final String code;
    private double balance;

    public GiftCardPayment(String code, double balance) {
        super("GiftCard");
        this.code = code;
        this.balance = balance;
    }

    @Override
    public PaymentResult processPayment(double amount){
        if(balance < amount || amount <= 0){
            return new PaymentResult(false, "Not enough balance");
        }
        return new PaymentResult(true, "Paid " + amount + " using gift card");
    }
}
