package data_base;

import repository.ConnectionRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
public final class DatabaseConnectionRepository implements ConnectionRepository {
    private final String url;

    public DatabaseConnectionRepository(String url) {
        this.url = Objects.requireNonNull(url,"url");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
