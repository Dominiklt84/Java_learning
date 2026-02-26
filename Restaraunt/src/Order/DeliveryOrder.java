package Order;

import Discount.DiscountStrategy;

public class DeliveryOrder extends Order{
    private double deliveryFee;

    public DeliveryOrder(DiscountStrategy discountStrategy, double deliveryFee) {
        super(discountStrategy);
        this.deliveryFee = deliveryFee;
    }

    @Override
    protected double additionalCost(){
        return deliveryFee;
    }
}
