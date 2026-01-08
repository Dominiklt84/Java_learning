package domain;

public final class Account {
    private int id;
    private int userId;
    private int balance;

    public Account(int id, int userId, int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }
    public int getBalance() {
        return balance;
    }
    public Account withBalance(int balance) {
        return new Account(this.id, this.userId, balance);
    }
}
