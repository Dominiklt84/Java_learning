package service;

import domain.Account;
import domain.Transaction;
import repository.AccountRepository;
import repository.TransactionRepository;
import java.util.List;
import java.util.Objects;

public class TransactionHistoryService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionHistoryService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = Objects.requireNonNull(accountRepository);
        this.transactionRepository = Objects.requireNonNull(transactionRepository);
    }

    public List<Transaction> listForUser(int userId, int limit) {
        Account acc = accountRepository.findByUserId(userId).orElseThrow(()->new IllegalStateException("Account missing"));
    return transactionRepository.listForAccount(acc.getId(),limit,0);
    }
}
