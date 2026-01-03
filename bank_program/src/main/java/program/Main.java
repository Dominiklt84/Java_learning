package program;

import data_base.DatabaseConnectionRepository;
import data_base.DatabaseUserRepository;
import repository.ConnectionRepository;
import repository.UserRepository;
import service.AuthService;
import service.PasswordHasher;

public class Main {
    ConnectionRepository cr = new DatabaseConnectionRepository("jdbc:sqlite:bankapp.db");
    UserRepository userRepo = new DatabaseUserRepository(cr);

    PasswordHasher hasher = new PasswordHasher();
    AuthService auth = new AuthService(userRepo, hasher);
}
