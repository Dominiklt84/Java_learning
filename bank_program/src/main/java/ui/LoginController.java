package ui;

import domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.AuthService;

public final class LoginController {
    @FXML private TextField LoginField;
    @FXML private PasswordField PasswordField;

    private AuthService authService;
    private SceneNavigator nav;

    public void init(AuthService authService, SceneNavigator nav) {
        this.authService = authService;
        this.nav = nav;
    }

    @FXML
    private void onLogin() {
        String username = LoginField.getText().trim();
        char[] pass = PasswordField.getText().toCharArray();

        try {
            User user = authService.login(username, pass);
            nav.showDashboard(user);
        } catch (RuntimeException ex) {
            System.out.println("Login error: " + ex.getMessage());
        } finally {
            PasswordField.clear();
        }
    }
    @FXML
    private void onGoRegister() {
        nav.showRegister();
    }
}