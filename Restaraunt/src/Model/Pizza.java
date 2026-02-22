package Model;

public class Pizza extends Product {
    private PizzaSize size;

    public Pizza(String name, double basePrice, PizzaSize size) {
        super(name, basePrice);
        this.size = size;
    }

    @Override
    public double calculatePrice(){

        switch (size){
            case PizzaSize.MEDIUM -> {
                return basePrice+2;
            }
            case PizzaSize.LARGE -> {
                return basePrice+4;
            }
            default -> {
                return basePrice;
            }
        }
    }
}
