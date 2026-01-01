package domain;
import java.time.LocalDate;
import java.util.Objects;

public final class Transaction {
    private final long id;
    private final LocalDate date;
    private final Transaction_Type type;
    private final String category;
    private final String description;
    private final Money amount;

    public Transaction(long id,
                       LocalDate date,
                       Transaction_Type type,
                       String category,
                       String description,
                       Money amount) {

        this.id = id;
        this.date = Objects.requireNonNull(date, "date");
        this.type = Objects.requireNonNull(type, "type");
        this.category = Objects.requireNonNull(category, "category").trim();
        this.description = description == null ? "" : description.trim();
        this.amount = Objects.requireNonNull(amount, "amount");
    }

    public long id() { return id; }
    public LocalDate date() { return date; }
    public Transaction_Type type() { return type; }
    public String category() { return category; }
    public String description() { return description; }
    public Money amount() { return amount; }

    public Transaction withId(long newId) {
        return new Transaction(newId, date, type, category, description, amount);
    }
}
