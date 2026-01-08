package program;

import data_base.*;
import javafx.application.Application;
import javafx.stage.Stage;
import repository.*;
import service.*;
import ui.SceneNavigator;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ConnectionRepository cp = new DatabaseConnectionRepository("jdbc:sqlite:bankapp.db");

        try (Connection c = cp.getConnection()) {
            new Database(c).initialize();
        }

        UserRepository userRepository = new DatabaseUserRepository(cp);
        AccountRepository accountRepo = new DatabaseAccountRepository(cp);
        TransactionRepository txRepo = new DatabaseTransactionRepository(cp);

        PasswordHasher hasher = new PasswordHasher();
        AuthService authService = new AuthService(userRepository, hasher);

        BankService bankService = new BankService(cp, accountRepo, txRepo);
        TransactionHistoryService historyService = new TransactionHistoryService(accountRepo, txRepo);
        TransferService transferService = new TransferService(userRepository, accountRepo, txRepo, cp);

        SceneNavigator nav = new SceneNavigator(stage, authService, bankService, transferService, historyService);

        nav.ShowLogin();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
