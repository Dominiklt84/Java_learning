package Model;

public class Burger extends Product {
    private boolean extraCheese;

    public Burger(String name, double basePrice, boolean extraCheese) {
        super(name, basePrice);
        this.extraCheese = extraCheese;
    }

    @Override
    public double calculatePrice(){
        double finalPrice = basePrice;
        if(extraCheese){
            finalPrice+=0.5;
        }
        return finalPrice;
    }
}
