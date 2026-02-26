package Payment;

public class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount){
        System.out.printf("Paid %.2f using Credit Card%n",amount);
    }
}
