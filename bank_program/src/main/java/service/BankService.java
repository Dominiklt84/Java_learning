package service;

import domain.Account;
import repository.AccountRepository;

import java.util.Objects;

public class BankService {
    private AccountRepository accountRepository;

    public BankService(AccountRepository accountRepository) {
    this.accountRepository = Objects.requireNonNull(accountRepository, "accountRepository");
    }
    public Account getOrCreateAccountForUser(int userId) {
        return accountRepository.findByUserId(userId)
                .orElseGet(() -> accountRepository.insert(new Account(0, userId, 0)));
    }
    public int getBalance(int userId) {
        return getOrCreateAccountForUser(userId).getBalance();
    }
    public int deposit(int userId, int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");

        Account acc = getOrCreateAccountForUser(userId);
        int newBalance = acc.getBalance() + amount;

        accountRepository.updateBalance(acc.getId(), newBalance);
        return newBalance;
    }
    public int withdraw(int userId, int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");

        Account acc = getOrCreateAccountForUser(userId);
        int current = acc.getBalance();

        if (amount > current) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        int newBalance = current - amount;
        accountRepository.updateBalance(acc.getId(), newBalance);
        return newBalance;
    }
}
