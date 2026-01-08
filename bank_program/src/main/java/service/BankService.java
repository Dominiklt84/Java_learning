package service;

import domain.Account;
import domain.Transaction;
import Type.TransactionType;
import repository.AccountRepository;
import repository.ConnectionRepository;
import repository.TransactionRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

public final class BankService {

    private final ConnectionRepository cp;
    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public BankService(ConnectionRepository cp, AccountRepository accountRepo, TransactionRepository txRepo) {
        this.cp = Objects.requireNonNull(cp);
        this.accountRepo = Objects.requireNonNull(accountRepo);
        this.txRepo = Objects.requireNonNull(txRepo);
    }

    public Account getOrCreateAccountForUser(int userId) {
        return accountRepo.findByUserId(userId)
                .orElseGet(() -> accountRepo.insert(new Account(0, userId, 0)));
    }

    public int getBalanceCents(int userId) {
        return getOrCreateAccountForUser(userId).getBalance();
    }

    public int deposit(int userId, int amountCents, String note) {
        if (amountCents <= 0) throw new IllegalArgumentException("Amount must be > 0");

        Account acc = getOrCreateAccountForUser(userId);

        try (Connection c = cp.getConnection()) {
            c.setAutoCommit(false);
            try {
                int newBalance = acc.getBalance() + amountCents;
                updateBalance(c, acc.getId(), newBalance);

                Transaction tx = new Transaction(
                        0,
                        null,
                        acc.getId(),
                        TransactionType.DEPOSIT,
                        amountCents,
                        LocalDateTime.now(),
                        normalizeNote(note)
                );

                txRepo.insert(c,tx);

                c.commit();
                return newBalance;

            } catch (RuntimeException ex) {
                c.rollback();
                throw ex;
            } catch (SQLException ex) {
                c.rollback();
                throw new RuntimeException("Deposit failed", ex);
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error in deposit()", e);
        }
    }


    public int withdraw(int userId, int amountCents, String note) {
        if (amountCents <= 0) throw new IllegalArgumentException("Amount must be > 0");

        Account acc = getOrCreateAccountForUser(userId);

        try (Connection c = cp.getConnection()) {
            c.setAutoCommit(false);
            try {
                int current = acc.getBalance();
                if (amountCents > current) throw new IllegalArgumentException("Insufficient funds");

                int newBalance = current - amountCents;
                updateBalance(c, acc.getId(), newBalance);

                Transaction tx = new Transaction(
                        0,
                        acc.getId(),
                        null,
                        TransactionType.WITHDRAW,
                        amountCents,
                        LocalDateTime.now(),
                        normalizeNote(note)
                );

                txRepo.insert(c,tx);

                c.commit();
                return newBalance;

            } catch (RuntimeException ex) {
                c.rollback();
                throw ex;
            } catch (SQLException ex) {
                c.rollback();
                throw new RuntimeException("Withdraw failed", ex);
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error in withdraw()", e);
        }
    }


    private static void updateBalance(Connection c, int accountId, int newBalance) throws SQLException {
        try (var ps = c.prepareStatement("UPDATE accounts SET balance_cents=? WHERE id=?")) {
            ps.setInt(1, newBalance);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        }
    }

    private static String normalizeNote(String note) {
        if (note == null) return null;
        String s = note.trim();
        return s.isBlank() ? null : s;
    }
}
