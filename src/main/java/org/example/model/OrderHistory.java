package org.example.model;

import java.time.LocalDate;


public class OrderHistory {
    private Order order;
    private LocalDate orderDate;

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString(){
        return "Order items: " + order.getItems() + " | Customer name : "
                + order.getCustomerName() + " | Date : " + orderDate;
    }

    public OrderHistory(Order order, LocalDate orderDate){
        this.order = order;
        this.orderDate = orderDate;
    }
}
