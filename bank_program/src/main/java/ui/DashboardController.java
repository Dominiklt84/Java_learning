package ui;

import domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.BankService;
import service.MoneyParser;

import java.util.Objects;

public final class DashboardController {

    @FXML private Label userLabel;
    @FXML private Label amountLabel;
    @FXML private TextField amountField;

    private BankService bankService;
    private SceneNavigator nav;
    private User user;

    public void init(User user, SceneNavigator nav, BankService bankService) {
        this.user = Objects.requireNonNull(user,"user");
        this.nav = Objects.requireNonNull(nav,"nav");
        this.bankService = Objects.requireNonNull(bankService,"bankService");

        userLabel.setText(user.getUsername());
        int cents = bankService.getBalance(user.getId());
        amountLabel.setText(MoneyParser.formatFromCents(cents));
    }
    @FXML
    private void onDeposit() {
        try {
            int cents = MoneyParser.parseToCents(amountField.getText());
            int newBal = bankService.deposit(user.getId(), cents);
            amountLabel.setText(MoneyParser.formatFromCents(newBal));
            amountField.clear();
        } catch (RuntimeException e) {
            System.out.println("Deposit error: " + e.getMessage());
        }
    }

    @FXML
    private void onWithdraw() {
        try {
            int cents = MoneyParser.parseToCents(amountField.getText());
            int newBal = bankService.withdraw(user.getId(), cents);
            amountLabel.setText(MoneyParser.formatFromCents(newBal));
            amountField.clear();
        } catch (RuntimeException e) {
            System.out.println("Withdraw error: " + e.getMessage());
        }
    }
}
