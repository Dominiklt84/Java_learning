package lt.viko.eif.dalencinovic;

    public class Operations {

        public void depositMoney(BankAccount account, double amount){
            account.setBalance(account.getBalance() + amount);
        }

        public void withdrawMoney(BankAccount account, double amount){
            if(account.getBalance() >= amount){
                account.setBalance(account.getBalance() - amount);
            }else{
                System.out.println("Not enough money");
            }
        }

    }

