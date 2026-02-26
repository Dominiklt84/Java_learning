package Payment;

public class BlikPayment implements PaymentMethod {
    @Override
    public void pay(double amount){
        System.out.printf("Paid %.2f using BLIK%n", amount);
    }
}
