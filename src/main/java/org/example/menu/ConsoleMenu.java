package org.example.menu;

import org.example.config.AppConfig;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.PaymentResult;
import org.example.payment.PaymentMethod;
import org.example.payment.PaymentMethodFactory;
import org.example.payment.PaymentProcessor;

import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();

    private Order currentOrder;

    public void start(){
        AppConfig config = AppConfig.getInstance();
        System.out.println("Welcome to " + config.getApplicationName());

        boolean running = true;
        while(running){
            printMenu();

            int option = Integer.parseInt(scanner.nextLine());

            switch (option){
                case 1 -> {
                    try{
                        createOrder();

                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("--------------------");
                    }

                }
                case 2 -> {
                    try {
                        addItem();

                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("--------------------");
                    }
                }
                case 3 ->{
                    try{
                        viewOrder();

                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("--------------------");
                    }

                }
                case 4 -> {
                    try {
                        payOrder();

                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("--------------------");
                    }

                }
                case 0 -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void createOrder() throws Exception {
        System.out.println("Customer name:");
        String customerName = scanner.nextLine();

        currentOrder = Order.builder()
                .customerName(customerName)
                .build();
        System.out.println("Order created for " + customerName);
    }

    private void addItem() throws Exception{
        if(currentOrder == null){
            throw new Exception("Order is empty");
        }

        System.out.println("Item name:");
        String itemName = scanner.nextLine();

        System.out.println("Price:");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("Quantity:");
        int quantity = Integer.parseInt(scanner.nextLine());

        currentOrder.addItem(new OrderItem(itemName, price, quantity));
        System.out.println("Item added to order");
    }

    private void viewOrder() throws Exception{
        if(currentOrder == null){
            throw new Exception("Order is empty");
        }

        System.out.println("Customer: " + currentOrder.getCustomerName());
        System.out.println("Status: " +  currentOrder.getStatus());
        System.out.println("Items:");

        for (OrderItem item : currentOrder.getItems()){
            System.out.println("- " + item);
        }

        System.out.println("Total: " + currentOrder.calculateTotal());
    }

    private void payOrder() throws Exception{
        if(currentOrder == null){
            throw new Exception("Order is empty");
        }

        System.out.println("""
                Select payment method:
                1. Credit Card
                2. PayPal
                3. Gift Card
                4. Bank Transfer
                """);
        int option = Integer.parseInt(scanner.nextLine());

        PaymentMethod paymentMethod = switch(option){
            case 1 -> createCreditCardPayment();
            case 2 -> createPaypalPayment();
            case 3 -> createGiftCardPayment();
            case 4 -> createBankTransferPayment();
            default -> throw new IllegalArgumentException("Invalid payment method");
        };

        PaymentResult result = paymentProcessor.process(currentOrder, paymentMethod);
        System.out.println(result.getMessage());
    }

    private PaymentMethod createCreditCardPayment(){
        System.out.println("Card number:");
        String cardNumber =  scanner.nextLine();

        System.out.println("Card holder name:");
        String cardHolderName =  scanner.nextLine();

        return PaymentMethodFactory.createCreditCardPayment(cardNumber,cardHolderName);
    }

    private PaymentMethod createPaypalPayment(){
        System.out.println("Email:");
        String email = scanner.nextLine();

        return PaymentMethodFactory.createPayPalPayment(email);
    }

    private PaymentMethod createGiftCardPayment(){
        System.out.println("Code:");
        String code = scanner.nextLine();

        System.out.println("Balance:");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        return PaymentMethodFactory.createGiftCardPayment(code, balance);
    }

    private PaymentMethod createBankTransferPayment(){
        System.out.println("Bank account number:");
        String bankAccountNumber = scanner.nextLine();

        return PaymentMethodFactory.createBankTransferPayment(bankAccountNumber);
    }

    private void printMenu(){
        System.out.println("""
                1. Create order
                2. Add item to order
                3. View order
                4. Pay order
                0. Exit
                """);
    }
}
