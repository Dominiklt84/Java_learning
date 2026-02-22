package Model;

public class Drink extends Product {
    private int capacity;

    public Drink(String name, double basePrice, int capacity) {
        super(name, basePrice);
        this.capacity = capacity;
    }

    @Override
    public double calculatePrice(){
        return basePrice+capacity*0.01;
    }
}
