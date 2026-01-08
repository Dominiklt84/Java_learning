package data_base;

import domain.Transaction;
import Type.TransactionType;
import repository.TransactionRepository;
import repository.ConnectionRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTransactionRepository implements TransactionRepository {

    private final ConnectionRepository connectionRepository;

    public DatabaseTransactionRepository(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Override
    public void insert(Transaction tx) {
        try (Connection c = connectionRepository.getConnection()) {
            insert(c, tx);
        } catch (SQLException e) {
            throw new RuntimeException("DB error in insert()", e);
        }
    }

    @Override
    public void insert(Connection c, Transaction tx) {
        String sql = """
        INSERT INTO transactions(from_account_id, to_account_id, type, amount_cents, created_at, note)
        VALUES(?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            setNullableInt(ps, 1, tx.getFromAccountId());
            setNullableInt(ps, 2, tx.getToAccountId());
            ps.setString(3, tx.getType().name());
            ps.setInt(4, tx.getAmount());
            ps.setString(5, tx.getCreatedAt().toString());
            ps.setString(6, tx.getNote());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error in insert()", e);
        }
    }


    @Override
    public List<Transaction> listForAccount(int accountId, int limit, int offset) {
        String sql = """
            SELECT id, from_account_id, to_account_id, type, amount_cents, created_at, note
            FROM transactions
            WHERE from_account_id = ? OR to_account_id = ?
            ORDER BY created_at DESC
            LIMIT ? OFFSET ?
            """;

        try (Connection c = connectionRepository.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.setInt(2, accountId);
            ps.setInt(3, limit);
            ps.setInt(4, offset);

            try (ResultSet rs = ps.executeQuery()) {
                List<Transaction> out = new ArrayList<>();
                while (rs.next()) out.add(map(rs));
                return out;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error in listForAccount()", e);
        }
    }

    private Transaction map(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Integer fromId = getNullableInt(rs, "from_account_id");
        Integer toId = getNullableInt(rs, "to_account_id");
        TransactionType type = TransactionType.valueOf(rs.getString("type"));
        int amount = rs.getInt("amount_cents");
        LocalDateTime createdAt = LocalDateTime.parse(rs.getString("created_at"));
        String note = rs.getString("note");

        return new Transaction(id, fromId, toId, type, amount, createdAt, note);
    }

    private static void setNullableInt(PreparedStatement ps, int idx, Integer value)
            throws SQLException {
        if (value == null) ps.setNull(idx, Types.INTEGER);
        else ps.setInt(idx, value);
    }

    private static Integer getNullableInt(ResultSet rs, String col)
            throws SQLException {
        int v = rs.getInt(col);
        return rs.wasNull() ? null : v;
    }
}
