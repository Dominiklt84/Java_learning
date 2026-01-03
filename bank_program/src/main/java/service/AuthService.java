package service;

import domain.User;
import domain.ValidationException;
import repository.UserRepository;
import java.time.LocalDateTime;

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public AuthService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public User register(String username, char[] password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ValidationException("Username already exists");
        }
        String hash = passwordHasher.hash(password);
        User user = new User(0, username,hash,LocalDateTime.now());
        return userRepository.insert(user);
    }

    public User login(String username, char[] password) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ValidationException("Username or password incorrect")
        );;
        if (!passwordHasher.verify(password, user.getPasswordHash())) {
            throw new ValidationException("Username or password incorrect");
        }
        return user;
    }
}
