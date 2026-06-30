package org.example.model;

import org.example.config.AppConfig;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String customerName;
    private final List<OrderItem> items;
    private OrderStatus status;
    private Discount discount = new NoDiscount();

    public Order(Builder builder) {
        this.customerName = builder.customerName;
        this.items = builder.items;
        this.status = OrderStatus.NEW;
    }

    public void addItem(OrderItem item) throws Exception{
        if(status.equals(OrderStatus.PAID)){
            throw new Exception("Cannot add new items, because order is already paid");
        }
        items.add(item);
    }

    public double calculateTotal() throws Exception{
        if(items == null || items.isEmpty()){
            throw new Exception("Incorrect params");
        }
        double total = 0;

        for(OrderItem item: items){
            total += item.calculateTotal();
        }

        double discountTotal = discount.apply(total);

        return discountTotal + (discountTotal * AppConfig.getInstance().getTaxRate());

    }

    public void markAsPaid() throws Exception{
        if(items.isEmpty()){
            throw new Exception("Order is empty");
        }
        this.status = OrderStatus.PAID;
    }

    public void applyDiscount(Discount discount){
        this.discount = discount;
    }

    public boolean isPaid(){
        return this.status == OrderStatus.PAID;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String customerName;
        private List<OrderItem> items = new ArrayList<>();
        public Builder customerName(String customerName){
            this.customerName = customerName;
            return this;
        }

        public Builder addItem(OrderItem item){
            this.items.add(item);
            return this;
        }

        public Order build() throws Exception{
            if(customerName == null || customerName.isBlank()){
                throw new Exception("Incorrect params");
            }

            return new Order(this);
        }
    }
}
