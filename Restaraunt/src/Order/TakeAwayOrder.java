package Order;

import Discount.DiscountStrategy;

public class TakeAwayOrder extends Order{
    public TakeAwayOrder(DiscountStrategy discountStrategy) {
        super(discountStrategy);
    }
}
