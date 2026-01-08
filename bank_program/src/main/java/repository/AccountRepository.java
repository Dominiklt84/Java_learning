package repository;

import domain.Account;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByUserId(int userId);
    Account insert(Account account);
    void updateBalance(int accountId,  int newBalance);
}
