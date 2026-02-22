package Discount;

public class VipDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double price){
        return price*0.5;
    }
}
