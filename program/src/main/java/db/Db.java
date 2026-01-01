package db;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public final class Db {
    private static final String DB_FILE = "finapp.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE;

    private Db() {}

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to database", e);
        }
    }

    public static void init() {
        try {
            if (!Files.exists(Path.of(DB_FILE))) {
                Files.createFile(Path.of(DB_FILE));
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not create database file: " + DB_FILE, e);
        }

        String ddl = """
            CREATE TABLE IF NOT EXISTS transactions (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              date TEXT NOT NULL,
              type TEXT NOT NULL,
              category TEXT NOT NULL,
              description TEXT NOT NULL,
              amount REAL NOT NULL
            );

            CREATE INDEX IF NOT EXISTS idx_transactions_date ON transactions(date);
            """;

        try (Connection c = connect(); Statement st = c.createStatement()) {
            st.executeUpdate(ddl);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create tables/indexes", e);
        }
    }
}
