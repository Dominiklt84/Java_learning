package ui;
import domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.BankService;
import service.MoneyParser;

import java.util.Objects;
public final class CashController {
    @FXML private TextField amountField;

    private User user;
    private BankService bankService;

    private Runnable onAfterChange = () -> {};

    public void init(User user, BankService bankService, Runnable onAfterChange) {
        this.user = Objects.requireNonNull(user,"user");
        this.bankService = Objects.requireNonNull(bankService,"bankService");
        this.onAfterChange = Objects.requireNonNull(onAfterChange,"onAfterChange");
    }

    @FXML
    private void onDeposit() {
        try {
            int cents = MoneyParser.parseToCents(amountField.getText());

            bankService.deposit(user.getId(), cents, null);

            amountField.clear();
            onAfterChange.run();
        } catch (RuntimeException e) {
            System.out.println("Deposit error: " + e.getMessage());
        }
    }

    @FXML
    private void onWithdraw() {
        try {
            int cents = MoneyParser.parseToCents(amountField.getText());
            bankService.withdraw(user.getId(), cents, null);

            amountField.clear();
            onAfterChange.run();
        } catch (RuntimeException e) {
            System.out.println("Withdraw error: " + e.getMessage());
        }
    }
}
