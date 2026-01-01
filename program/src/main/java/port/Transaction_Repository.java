package port;
import domain.Transaction;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface Transaction_Repository {
    List<Transaction> findAll();
    List<Transaction> findByDateRange(LocalDate fromInclusive, LocalDate toInclusive);
    Optional<Transaction> findById(long id);
    Transaction insert(Transaction tx);
    void update(Transaction tx);
    void deleteById(long id);
}
