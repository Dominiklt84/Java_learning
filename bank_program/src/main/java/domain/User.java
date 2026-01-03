package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private final int id;
    private final String username;
    private final String password;
    private final LocalDateTime createdAt;

    public User(int id, String username, String password, LocalDateTime createdAt) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username cannot be empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("passwordHash cannot be empty");
        }
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
    }
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public User withId(int newId) {
        return new User(newId, username, password, createdAt);
    }
}
