package Payment;

public class CashPayment implements PaymentMethod {
    @Override
    public void pay(double amount){
        System.out.printf("Paid %.2f with Cash%n", amount);
    }
}
