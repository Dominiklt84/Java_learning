package lt.viko.eif.dalencinovic.first.spring.model;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "account")
public class Account extends BaseEntity{
    private BigInteger accountNumber;
    private Float balance;

    public Account() {
    }

    public Account(BigInteger accountNumber, Float balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public BigInteger getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(BigInteger accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "\n\t\tAccount: " +
                "\n\t\t\tid=" + getId() +
                "\n\t\t\taccountNumber="+ accountNumber+
                "\n\t\t\tbalance="+ balance;
    }
}
