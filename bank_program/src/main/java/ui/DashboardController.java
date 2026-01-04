package ui;

import domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public final class DashboardController {

    @FXML private Label userLabel;
    @FXML private Label amountLabel;

    private SceneNavigator nav;
    private User user;

    public void init(User user, SceneNavigator nav) {
        this.user = user;
        this.nav = nav;

        userLabel.setText(user.getUsername());
        amountLabel.setText("0.00");
    }

}
