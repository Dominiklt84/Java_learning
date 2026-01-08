package ui;

import domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import service.BankService;
import service.MoneyParser;
import service.TransactionHistoryService;
import service.TransferService;

import java.util.Objects;

public final class DashboardController {

    @FXML private Label userLabel;
    @FXML private Label amountLabel;

    @FXML private HistoryController historyViewController;
    @FXML private CashController cashViewController;
    @FXML private TransferController transferViewController;

    private User user;
    private BankService bankService;

    public void init(User user,
                     SceneNavigator nav,
                     BankService bankService,
                     TransferService transferService,
                     TransactionHistoryService transactionHistoryService) {

        this.user = Objects.requireNonNull(user,"user");
        this.bankService = Objects.requireNonNull(bankService,"bankService");
        Objects.requireNonNull(nav,"nav");
        Objects.requireNonNull(transferService,"transferService");
        Objects.requireNonNull(transactionHistoryService,"transactionHistoryService");

        userLabel.setText(user.getUsername());
        refreshBalance();

        historyViewController.init(user.getId(), transactionHistoryService);
        historyViewController.refresh();

        cashViewController.init(user, bankService, () -> {
            refreshBalance();
            historyViewController.refresh();
        });

        transferViewController.init(user, transferService, () -> {
            refreshBalance();
            historyViewController.refresh();
        });
    }

    public void refreshBalance() {
        int cents = bankService.getBalanceCents(user.getId());
        amountLabel.setText(MoneyParser.formatFromCents(cents));
    }
}
