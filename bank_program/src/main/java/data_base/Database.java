package data_base;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Database {
    private final Connection connection;

    public Database(Connection connection) {
        this.connection = Objects.requireNonNull(connection,"connection");
    }

    public void initialize(){
        createUsersTable();
        createAccountsTable();
        createTransactionsTable();
    }
    private void createUsersTable(){
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              username TEXT NOT NULL UNIQUE,
              password_hash TEXT NOT NULL,
              created_at TEXT NOT NULL
            );
            """;
        executeDdl(sql, "users");
    }
    private void createAccountsTable(){
        String sql = """
            CREATE TABLE IF NOT EXISTS accounts (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              user_id INTEGER NOT NULL,
              balance_cents INTEGER NOT NULL,
              FOREIGN KEY(user_id) REFERENCES users(id)
            );
            """;
        executeDdl(sql, "accounts");
    }
    private void createTransactionsTable(){
        String sql = """
            CREATE TABLE IF NOT EXISTS transactions (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              from_account_id INTEGER,
              to_account_id INTEGER,
              type TEXT NOT NULL,
              amount_cents INTEGER NOT NULL,
              created_at TEXT NOT NULL,
              note TEXT,
              FOREIGN KEY(from_account_id) REFERENCES accounts(id),
              FOREIGN KEY(to_account_id) REFERENCES accounts(id)
            );
            """;
        executeDdl(sql, "transactions");
    }
    private void executeDdl(String sql, String name){
        try(Statement st =connection.createStatement()){
            st.execute(sql);
        }catch (SQLException e){
            throw new RuntimeException("Failed to create table: "+name,e);
        }
    }
}
