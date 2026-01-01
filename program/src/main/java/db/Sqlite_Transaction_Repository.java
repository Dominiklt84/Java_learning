package db;
import domain.Money;
import domain.Transaction;
import domain.Transaction_Type;
import port.Transaction_Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Sqlite_Transaction_Repository implements Transaction_Repository{
    @Override
    public List<Transaction> findAll() {
        String sql = """
            SELECT id, date, type, category, description, amount
            FROM transactions
            ORDER BY date DESC, id DESC
            """;

        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Transaction> out = new ArrayList<>();
            while (rs.next()) out.add(map(rs));
            return out;

        } catch (Exception e) {
            throw new RuntimeException("DB: findAll() failed", e);
        }
    }

    @Override
    public List<Transaction> findByDateRange(LocalDate fromInclusive, LocalDate toInclusive) {
        String sql = """
            SELECT id, date, type, category, description, amount
            FROM transactions
            WHERE date >= ? AND date <= ?
            ORDER BY date DESC, id DESC
            """;

        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, fromInclusive.toString());
            ps.setString(2, toInclusive.toString());

            try (ResultSet rs = ps.executeQuery()) {
                List<Transaction> out = new ArrayList<>();
                while (rs.next()) out.add(map(rs));
                return out;
            }

        } catch (Exception e) {
            throw new RuntimeException("DB: findByDateRange() failed", e);
        }
    }

    @Override
    public Optional<Transaction> findById(long id) {
        String sql = """
            SELECT id, date, type, category, description, amount
            FROM transactions
            WHERE id = ?
            """;

        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("DB: findById() failed", e);
        }
    }

    @Override
    public Transaction insert(Transaction tx) {
        String sql = """
            INSERT INTO transactions(date, type, category, description, amount)
            VALUES(?,?,?,?,?)
            """;

        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tx.date().toString());
            ps.setString(2, tx.type().name());
            ps.setString(3, tx.category());
            ps.setString(4, tx.description());
            ps.setDouble(5, tx.amount().asBigDecimal().doubleValue());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    return tx.withId(id);
                }
            }
            throw new RuntimeException("DB: insert() nie zwrócił id");

        } catch (Exception e) {
            throw new RuntimeException("DB: insert() failed", e);
        }
    }

    @Override
    public void update(Transaction tx) {
        String sql = """
            UPDATE transactions
            SET date = ?, type = ?, category = ?, description = ?, amount = ?
            WHERE id = ?
            """;

        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, tx.date().toString());
            ps.setString(2, tx.type().name());
            ps.setString(3, tx.category());
            ps.setString(4, tx.description());
            ps.setDouble(5, tx.amount().asBigDecimal().doubleValue());
            ps.setLong(6, tx.id());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("DB: update() failed", e);
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("DB: deleteById() failed", e);
        }
    }

    private static Transaction map(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        LocalDate date = LocalDate.parse(rs.getString("date"));
        Transaction_Type type = Transaction_Type.valueOf(rs.getString("type"));
        String category = rs.getString("category");
        String description = rs.getString("description");
        Money amount = Money.of(rs.getDouble("amount"));

        return new Transaction(id, date, type, category, description, amount);
    }
}
