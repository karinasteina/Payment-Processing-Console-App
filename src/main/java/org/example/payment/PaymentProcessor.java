package org.example.payment;

import org.example.model.Order;
import org.example.model.PaymentResult;

public class PaymentProcessor {
    public PaymentResult process(Order order, PaymentMethod paymentMethod) throws Exception{
        if(order.isPaid()){
            throw new Exception("Order is already paid");
        } else if(order.getItems().isEmpty()){
            throw new Exception("Order has no items");
        }

        PaymentResult result = paymentMethod.pay(order.calculateTotal());

        if(result.isSuccessful()){
            order.markAsPaid();
        }

        return result;
    }
}
