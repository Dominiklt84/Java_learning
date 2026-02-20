package lt.viko.eif.dalencinovic.first.spring.db;

import lt.viko.eif.dalencinovic.first.spring.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
