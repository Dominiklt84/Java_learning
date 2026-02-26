package Order;

import Payment.PaymentMethod;

public class OrderService {
    public void checkout(Order order, PaymentMethod paymentMethod){
        double totalPrice = order.getTotalPrice();
        paymentMethod.pay(totalPrice);
    }
}
