package ui;
import javafx.beans.property.*;
import domain.Transaction;
import java.time.LocalDate;

public final class Transaction_Row {
    private final long id;
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final DoubleProperty amount = new SimpleDoubleProperty();

    public Transaction_Row(Transaction tx) {
        this.id = tx.id();
        this.date.set(tx.date());
        this.type.set(tx.type().name());
        this.category.set(tx.category());
        this.description.set(tx.description());
        this.amount.set(tx.amount().asBigDecimal().doubleValue());
    }

    public long id() { return id; }

    public ObjectProperty<LocalDate> dateProperty() { return date; }
    public StringProperty typeProperty() { return type; }
    public StringProperty categoryProperty() { return category; }
    public StringProperty descriptionProperty() { return description; }
    public DoubleProperty amountProperty() { return amount; }
}
