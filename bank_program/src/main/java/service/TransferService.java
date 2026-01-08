package service;

import Type.TransactionType;
import domain.Account;
import domain.Transaction;
import repository.AccountRepository;
import repository.ConnectionRepository;
import repository.TransactionRepository;
import repository.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

public final class TransferService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ConnectionRepository connectionRepository;

    public TransferService(UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, ConnectionRepository connectionRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.accountRepository = Objects.requireNonNull(accountRepository);
        this.transactionRepository = Objects.requireNonNull(transactionRepository);
        this.connectionRepository = Objects.requireNonNull(connectionRepository);
    }

    public void transfer(int fromUserId, String toUsername, int amount, String note) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");

        var toUser = userRepository.findByUsername(toUsername)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        if (toUser.getId() == fromUserId) {
            throw new IllegalArgumentException("Cannot transfer to yourself");
        }

        Account fromAcc = accountRepository.findByUserId(fromUserId)
                .orElseThrow(() -> new IllegalStateException("Sender account missing"));
        Account toAcc = accountRepository.findByUserId(toUser.getId())
                .orElseThrow(() -> new IllegalStateException("Receiver account missing"));

        try (java.sql.Connection c = connectionRepository.getConnection()) {
            c.setAutoCommit(false);
            try {
                int fromBal = fromAcc.getBalance();
                if (amount > fromBal) throw new IllegalArgumentException("Insufficient funds");

                int newFrom = fromBal - amount;
                int newTo = toAcc.getBalance() + amount;

                updateBalance(c, fromAcc.getId(), newFrom);
                updateBalance(c, toAcc.getId(), newTo);


                transactionRepository.insert(c,new Transaction(
                        0,
                        fromAcc.getId(),
                        toAcc.getId(),
                        TransactionType.TRANSFER,
                        amount,
                        LocalDateTime.now(),
                        note
                ));

                c.commit();
            } catch (RuntimeException ex) {
                c.rollback();
                throw ex;
            } catch (SQLException ex) {
                c.rollback();
                throw new RuntimeException("Transfer failed", ex);
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error in transfer()", e);
        }
    }

    private void updateBalance(Connection c, int accountId, int newBalance) throws SQLException {
        try (var ps = c.prepareStatement("UPDATE accounts SET balance_cents=? WHERE id=?")) {
            ps.setInt(1, newBalance);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        }
    }
}
