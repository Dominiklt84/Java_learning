package Order;

import Discount.DiscountStrategy;

public class DineInOrder extends Order{
    public DineInOrder(DiscountStrategy discountStrategy) {
        super(discountStrategy);
    }
}
