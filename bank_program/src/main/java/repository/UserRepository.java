package repository;

import domain.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User insert(User user);
    Optional<User> findById(int id);
}
