package org.example.payment;

import org.example.model.PaymentResult;

public class BankTransferPayment extends PaymentMethod {
    private final String bankAccountNumber;

    public BankTransferPayment(String bankAccountNumber) {
        super("BankTransfer");
        this.bankAccountNumber = bankAccountNumber;
    }

    @Override
    public PaymentResult processPayment(double amount){
        if(bankAccountNumber == null || bankAccountNumber.isBlank()
                        || amount <= 0){
            return new PaymentResult(false, "Payment was not successful");
        }
        return new PaymentResult(true, "Paid " + amount + " with bank account ending with " + bankAccountNumber.substring(bankAccountNumber.length() - 4));
    }
}
