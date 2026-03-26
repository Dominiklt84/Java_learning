package lt.viko.eif.dalencinovic;

public class Main {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount("Jan",1000);
        Operations operations = new Operations();

        operations.depositMoney(bankAccount,500);
    }
}
