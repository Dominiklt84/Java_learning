package ui;
import domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.MoneyParser;
import service.TransferService;

import java.util.Objects;
public class TransferController {
    @FXML private TextField toUserField;
    @FXML private TextField amountField;
    @FXML private TextField noteField;

    private User user;
    private TransferService transferService;

    private Runnable onAfterTransfer = () -> {};

    public void init(User user, TransferService transferService, Runnable onAfterTransfer) {
        this.user = Objects.requireNonNull(user);
        this.transferService = Objects.requireNonNull(transferService);
        this.onAfterTransfer = Objects.requireNonNull(onAfterTransfer);
    }
    @FXML
    private void onTransfer() {
        try {
            String toUser = toUserField.getText().trim();
            int cents = MoneyParser.parseToCents(amountField.getText());
            String note = noteField.getText().trim();
            if (note.isBlank()) note = null;

            transferService.transfer(user.getId(), toUser, cents, note);

            toUserField.clear();
            amountField.clear();
            noteField.clear();

            onAfterTransfer.run();
        } catch (RuntimeException e) {
            System.out.println("Transfer error: " + e.getMessage());
        }
    }
}
