package UI;

import Model.Burger;
import Model.Pizza;
import Model.PizzaSize;
import Order.Order;
import Payment.BlikPayment;
import Payment.PaymentMethod;

import java.sql.SQLOutput;
import java.util.Scanner;

public class RestarauntApp {
    private Scanner scanner = new Scanner(System.in);
    public void start(){
        Order order = chooseOrderType();
        boolean running = true;
        while (running){
            System.out.println("""
                    1. Add Pizza
                    2. Add Burger
                    3. Add Drink
                    4. Checkout""");
            int choice=scanner.nextInt();
            switch (choice){
                case 1 -> order.addProduct(createPizza());
                case 2 -> order.addProduct(createBurger());
                case 3 -> order.addProduct(createDrink());
                case 4 -> running = false;
            }
        }
        PaymentMethod paymentMethod = choosePaymnetMethod();
        OrderService service = new OrderService();
        service.checkout(order,paymentMethod);
    }

    private Pizza createPizza(){
        System.out.println("Choose size: 1. SMALL 2. MEDIUM 3. LARGE");
        int size = scanner.nextInt();

        PizzaSize pizzaSize = switch (size){
            case 2 -> PizzaSize.MEDIUM;
            case 3 -> PizzaSize.LARGE;
            default -> PizzaSize.SMALL;
        };
        return new Pizza("Pizza",10,pizzaSize);
    }

    private Burger createBurger(){
        System.out.println("Extra cheese? 1. Yes 2. No");
        int cheese = scanner.nextInt();
        boolean extraCheese = cheese == 1;

        return new Burger("Burger",8,extraCheese);
    }
}
