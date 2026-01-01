package service;
import domain.Money;
import domain.Transaction;
import domain.Transaction_Type;
import domain.Validation_Exception;
import port.Transaction_Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class Transaction_Service {
    private final Transaction_Repository repo;

    public Transaction_Service(Transaction_Repository repo) {
        this.repo = Objects.requireNonNull(repo, "repo");
    }

    public List<Transaction> listAll() {
        return repo.findAll();
    }

    public List<Transaction> listByDateRange(LocalDate fromInclusive, LocalDate toInclusive) {
        if (fromInclusive == null || toInclusive == null) {
            throw new Validation_Exception("Date range cannot be empty");
        }
        if (toInclusive.isBefore(fromInclusive)) {
            throw new Validation_Exception("The 'to' date cannot be earlier than the 'from' date");
        }
        return repo.findByDateRange(fromInclusive, toInclusive);
    }

    public Transaction add(Transaction tx) {
        validate(tx, true);
        return repo.insert(tx);
    }

    public void edit(Transaction tx) {
        validate(tx, false);
        repo.update(tx);
    }

    public void delete(long id) {
        if (id <= 0) throw new Validation_Exception("Incorrect id");
        repo.deleteById(id);
    }

    public Summary summarize(List<Transaction> list) {
        Money income = Money.of(BigDecimal.ZERO);
        Money expense = Money.of(BigDecimal.ZERO);

        for (Transaction t : list) {
            if (t.type() == Transaction_Type.INCOME) income = income.add(t.amount());
            else expense = expense.add(t.amount());
        }
        Money balance = income.subtract(expense);
        return new Summary(income, expense, balance);
    }

    private static void validate(Transaction tx, boolean allowIdZero) {
        if (tx == null) throw new Validation_Exception("Transaction cannot be null");
        if (!allowIdZero && tx.id() <= 0) throw new Validation_Exception("No transaction ID (you are editing a record without an ID)");
        if (tx.date() == null) throw new Validation_Exception("Date is required");
        if (tx.category() == null || tx.category().isBlank()) throw new Validation_Exception("Category is required");
        if (tx.amount() == null) throw new Validation_Exception("The amount is required");

        if (tx.amount().asBigDecimal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Validation_Exception("The amount must be greater than zero\"");
        }
    }

    public record Summary(Money income, Money expense, Money balance) {}
}
