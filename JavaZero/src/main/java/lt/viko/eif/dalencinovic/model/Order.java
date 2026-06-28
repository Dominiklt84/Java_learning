package lt.viko.eif.dalencinovic.model;

import java.util.ArrayList;

public class Order {
    private ArrayList<Product> products= new ArrayList<>();

    public Order() {

    }

    public void addProduct(Product product){
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void removeProductByName(String name){
        for (int i=0;i<products.size();i++){
            Product product= products.get(i);
            if(product.getName().equals(name)){
                products.remove(i);
                break;
            }
        }
    }

    public Product findProductByName(String name){
        for (int i=0;i<products.size();i++) {
            Product product = products.get(i);
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }


}
