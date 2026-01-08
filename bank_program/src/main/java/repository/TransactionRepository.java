package repository;

import domain.Transaction;

import java.sql.Connection;
import java.util.List;

public interface TransactionRepository {
    void insert(Transaction transaction);
    void insert(Connection c, Transaction transaction);
    List<Transaction> listForAccount(int accountId, int limit, int offset);
}

