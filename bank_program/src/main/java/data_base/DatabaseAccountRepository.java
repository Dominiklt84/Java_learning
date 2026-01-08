package data_base;

import domain.Account;
import repository.AccountRepository;
import repository.ConnectionRepository;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public final class DatabaseAccountRepository implements AccountRepository {
    private final ConnectionRepository connectionRepository;

    public DatabaseAccountRepository(ConnectionRepository connectionRepository) {
        this.connectionRepository = Objects.requireNonNull(connectionRepository, "connectionRepository");
    }
    @Override
    public Optional<Account> findByUserId(int userId) {
        String sql = """
            SELECT id, user_id, balance_cents
            FROM accounts
            WHERE user_id = ?
            """;

        try (java.sql.Connection c = connectionRepository.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error in findByUserId()", e);
        }
    }
    @Override
    public Account insert(Account account) {
        String sql = """
            INSERT INTO accounts(user_id, balance_cents)
            VALUES(?, ?)
            """;

        try (java.sql.Connection c = connectionRepository.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, account.getUserId());
            ps.setLong(2, account.getBalance());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new RuntimeException("Insert succeeded but no generated key returned");
                }
                int newId = keys.getInt(1);
                return new Account(newId, account.getUserId(), account.getBalance());
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error in insert()", e);
        }
    }

    @Override
    public void updateBalance(int accountId, int newBalance) {
        String sql = """
            UPDATE accounts
            SET balance_cents = ?
            WHERE id = ?
            """;

        try (Connection c = connectionRepository.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, newBalance);
            ps.setInt(2, accountId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error in updateBalance()", e);
        }
    }

    private Account map(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        int balanceCents = rs.getInt("balance_cents");
        return new Account(id, userId, balanceCents);
    }
}
