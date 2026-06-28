package lt.viko.eif.dalencinovic;

import lt.viko.eif.dalencinovic.model.Order;
import lt.viko.eif.dalencinovic.model.Product;
import lt.viko.eif.dalencinovic.service.OrderService;

public class Main {
    public static void main(String[] args) {
        Product p1 = new Product("Laptop", 1000, 2);
        Product p2 = new Product("Mouse", 50, 1);

        Order order = new Order();
        order.addProduct(p1);
        order.addProduct(p2);

        OrderService service = new OrderService();
        double total = service.calculateTotal(order);
        System.out.println(total);
    }
}