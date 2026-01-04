package ui;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.AuthService;

public final class RegisterController {
    @FXML private TextField LoginField;
    @FXML private PasswordField PasswordField;

    private AuthService authService;
    private SceneNavigator nav;

    public void init(AuthService authService, SceneNavigator nav) {
        this.authService = authService;
        this.nav = nav;
    }

    @FXML
    private void onRegister() {
        String username = LoginField.getText().trim();
        char[] pass = PasswordField.getText().toCharArray();

        try {
            authService.register(username, pass);
            nav.ShowLogin();
        } catch (RuntimeException ex) {
            System.out.println("Register error: " + ex.getMessage());
        } finally {
            PasswordField.clear();
        }
    }
}
