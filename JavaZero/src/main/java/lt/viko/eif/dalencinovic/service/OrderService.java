package lt.viko.eif.dalencinovic.service;

import lt.viko.eif.dalencinovic.model.Inventory;
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
        double total= calculateTotal(order);
        double discount = total*percent/100;

        return total-discount;
    }

    public boolean canCheckout( Order order){
        if (order.getProducts().size()==0) {
            return false;
        }
        for (int i=0;i<order.getProducts().size();i++){
                Product product= order.getProducts().get(i);
                 if (product.getQuantity()<=0){
                    return false;
                }
            }
        return true;
    }

    public boolean hasEnoughStock(Order order){
        for (int i=0;i<order.getProducts().size();i++){
            Product product= order.getProducts().get(i);
            if (product.getQuantity()>product.getStock()){
                return false;
            }
        }
        return true;
    }

    public boolean checkout(Order order, Inventory inventory){
        for (int i=0;i<order.getProducts().size();i++){
            Product product= order.getProducts().get(i);
            int stock=inventory.getStock(product.getName());
            if (product.getQuantity()>stock){
                return false;
            }

        }

        for (int i=0;i<order.getProducts().size();i++){
            Product product= order.getProducts().get(i);

            inventory.reduceStock(product.getName(), product.getQuantity());
        }

        return true;
    }
}
