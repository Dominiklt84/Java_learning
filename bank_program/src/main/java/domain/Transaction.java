package domain;

import java.time.LocalDateTime;
import java.util.Objects;
import Type.TransactionType;

public final class Transaction {
    private final int id;
    private final Integer fromAccountId;;
    private final Integer toAccountId;
    private final TransactionType type;
    private final int amount;
    private final LocalDateTime createdAt;
    private final String note;


    public Transaction(int id, Integer fromAccountId, Integer toAccountId, TransactionType type, int amount, LocalDateTime createdAt, String note) {
        if (amount <= 0) throw new IllegalArgumentException("amountCents must be > 0");
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.type = type;
        this.amount = amount;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
        this.note = note;
    }

    public int getId() { return id; }
    public Integer getFromAccountId() { return fromAccountId; }
    public Integer getToAccountId() { return toAccountId; }
    public TransactionType getType() { return type; }
    public int getAmount() { return amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getNote() { return note; }
}
