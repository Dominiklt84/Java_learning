package lt.viko.eif.dalencinovic.service;

import lt.viko.eif.dalencinovic.model.Order;
import lt.viko.eif.dalencinovic.model.Product;


public class OrderService {

    public double calculateTotal(Order order){
        double total=0;
            for (int i=0;i<order.getProducts().size();i++){
                Product product = order.getProducts().get(i);
                total += product.getPrice()* product.getQuantity();
            }
       return total;
    }

    public void addProductToOrder(Order order, Product product){
        order.addProduct(product);
    }

    public void removeProductFromOrder(Order order, String name){
        order.removeProductByName(name);
    }

    public double applyDiscount(Order order, double percent){


    }
}
