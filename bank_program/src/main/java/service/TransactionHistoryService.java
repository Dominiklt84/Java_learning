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

    public int getAccountIdForUser(int userId){
        return accountRepository.findByUserId(userId)
                .orElseThrow(()-> new IllegalStateException("Account missing"))
                .getId();
    }

    public List<Transaction> listForUser(int userId, int limit) {
        int accountId = getAccountIdForUser(userId);
    return transactionRepository.listForAccount(accountId,limit,0);
    }
}
