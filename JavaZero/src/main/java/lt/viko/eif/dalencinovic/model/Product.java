package lt.viko.eif.dalencinovic.model;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private int stock;

    public Product(String name, double price, int quantity, int stock) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.stock=stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    public int getStock(){
        return stock;
    }


    @Override
    public String toString() {
        return "Product: " +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity+
                ", stock="+ stock;
    }
}
