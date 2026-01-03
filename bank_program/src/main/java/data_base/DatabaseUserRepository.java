package data_base;

import domain.User;
import repository.ConnectionRepository;
import repository.UserRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Objects;
public final class DatabaseUserRepository implements UserRepository {
    private final ConnectionRepository connectionRepository;

    public DatabaseUserRepository(ConnectionRepository connectionRepository) {
        this.connectionRepository = Objects.requireNonNull(connectionRepository, "connectionRepository");
    }
    @Override
    public Optional<User> findByUsername(String username) {
        String sql = """
            SELECT id, username, password_hash, created_at
            FROM users
            WHERE username = ?
            """;

        try (Connection c = connectionRepository.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(mapUser(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error in findByUsername()", e);
        }
    }
    @Override
    public Optional<User> findById(int id) {
        String sql = """
            SELECT id, username, password_hash, created_at
            FROM users
            WHERE id = ?
            """;

        try (Connection c = connectionRepository.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(mapUser(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error in findById()", e);
        }
    }
    @Override
    public User insert(User user) {
        String sql = """
            INSERT INTO users(username, password_hash, created_at)
            VALUES(?,?,?)
            """;

        try (Connection c = connectionRepository.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getCreatedAt().toString());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new RuntimeException("Insert succeeded but no generated key returned");
                }
                int newId = keys.getInt(1);
                return user.withId(newId);
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error in insert()", e);
        }
    }
    private User mapUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String passwordHash = rs.getString("password_hash");
        LocalDateTime createdAt = LocalDateTime.parse(rs.getString("created_at"));

        return new User(id, username, passwordHash, createdAt);
    }
}
