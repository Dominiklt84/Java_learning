package Order;

import Discount.DiscountStrategy;
import Model.Product;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> products = new ArrayList<>();
    DiscountStrategy discountStrategy;

    public Order(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void addProduct(Product product){
        products.add(product);
    }
    public double getTotalPrice(){
        double total = 0;
        for (Product product:products){
            total+=product.calculatePrice();
        }
        return discountStrategy.applyDiscount(total+additionalCost());
    }
    protected double additionalCost(){
        return 0;
    }
}
