package program;

import repository.ConnectionRepository;
import data_base.Database;
import data_base.DatabaseConnectionRepository;
import data_base.DatabaseUserRepository;
import javafx.application.Application;
import javafx.stage.Stage;
import repository.UserRepository;
import service.AuthService;
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
        AuthService authService = new AuthService(userRepo, new PasswordHasher());

        SceneNavigator nav = new SceneNavigator(stage, authService);
        nav.ShowLogin();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}