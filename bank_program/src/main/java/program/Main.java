package program;

import data_base.DatabaseAccountRepository;
import repository.AccountRepository;
import repository.ConnectionRepository;
import data_base.Database;
import data_base.DatabaseConnectionRepository;
import data_base.DatabaseUserRepository;
import javafx.application.Application;
import javafx.stage.Stage;
import repository.UserRepository;
import service.AuthService;
import service.BankService;
import service.PasswordHasher;
import ui.SceneNavigator;
import java.sql.Connection;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ConnectionRepository cr = new DatabaseConnectionRepository("jdbc:sqlite:bankapp.db");

        try (Connection c = cr.getConnection()) {
            new Database(c).initialize();
        }

        UserRepository userRepo = new DatabaseUserRepository(cr);
        AccountRepository accountRepo = new DatabaseAccountRepository(cr);

        PasswordHasher hasher = new PasswordHasher();
        BankService bankService = new BankService(accountRepo);
        AuthService authService = new AuthService(userRepo, hasher);

        SceneNavigator nav = new SceneNavigator(stage, authService,bankService);
        nav.ShowLogin();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}