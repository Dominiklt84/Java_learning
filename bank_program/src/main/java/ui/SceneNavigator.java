package ui;
import domain.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.AuthService;
import service.BankService;

import java.io.IOException;
import java.util.Objects;

public final class SceneNavigator {
    private final Stage stage;
    private final AuthService authService;
    private final BankService bankService;

    public SceneNavigator(Stage stage, AuthService authService, BankService bankService) {
        this.stage = Objects.requireNonNull(stage);
        this.authService = Objects.requireNonNull(authService);
        this.bankService = Objects.requireNonNull(bankService);
    }

    public void ShowLogin(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login.fxml"));
            Scene scene = new Scene(loader.load(), 700, 400);

            LoginController controller = loader.getController();
            controller.init(authService, this);

            stage.setTitle("Bank App - Login");
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load login.fxml", e);
        }
    }
    public void showDashboard(User user){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 700, 400);

            DashboardController controller = loader.getController();
            controller.init(user, this, bankService);

            stage.setTitle("Bank App - Dashboard");
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load dashboard.fxml", e);
        }
    }

    public void showRegister(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/registration.fxml"));
            Scene scene = new Scene(loader.load(), 700, 400);
            RegisterController controller = loader.getController();
            controller.init(authService, this);

            stage.setTitle("Bank App - Registration");
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load registration.fxml", e);
        }

        }
    }

