package ui;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import domain.Money;
import domain.Transaction;
import domain.Transaction_Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public final class Transaction_Dialog {
    private Transaction_Dialog() {}

    public static Optional<Transaction> showAddDialog() {
        return showDialog("Add a transaction", null);
    }

    public static Optional<Transaction> showEditDialog(Transaction existing) {
        return showDialog("Edit transaction", existing);
    }

    private static Optional<Transaction> showDialog(String title, Transaction existing) {
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.setTitle(title);

        ButtonType okType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okType, ButtonType.CANCEL);

        DatePicker datePicker = new DatePicker(existing != null ? existing.date() : LocalDate.now());
        ComboBox<Transaction_Type> typeBox = new ComboBox<>();
        typeBox.getItems().addAll(Transaction_Type.INCOME, Transaction_Type.EXPENSE);
        typeBox.setValue(existing != null ? existing.type() : Transaction_Type.EXPENSE);

        TextField categoryField = new TextField(existing != null ? existing.category() : "");
        TextField descField = new TextField(existing != null ? existing.description() : "");
        TextField amountField = new TextField(existing != null ? existing.amount().toString() : "");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        grid.addRow(0, new Label("Date:"), datePicker);
        grid.addRow(1, new Label("Type:"), typeBox);
        grid.addRow(2, new Label("Category:"), categoryField);
        grid.addRow(3, new Label("Description:"), descField);
        grid.addRow(4, new Label("Amount:"), amountField);

        dialog.getDialogPane().setContent(grid);

        Button okBtn = (Button) dialog.getDialogPane().lookupButton(okType);
        okBtn.disableProperty().bind(
                categoryField.textProperty().isEmpty()
                        .or(amountField.textProperty().isEmpty())
                        .or(datePicker.valueProperty().isNull())
                        .or(typeBox.valueProperty().isNull())
        );

        dialog.setResultConverter(btn -> {
            if (btn != okType) return null;

            long id = existing == null ? 0 : existing.id();
            LocalDate date = datePicker.getValue();
            Transaction_Type type = typeBox.getValue();
            String category = categoryField.getText();
            String description = descField.getText();

            BigDecimal amount;
            try {
                amount = new BigDecimal(amountField.getText().replace(",", "."));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid amount (use e.g. 12.50)");
            }

            return new Transaction(id, date, type, category, description, Money.of(amount));
        });

        return dialog.showAndWait();
    }
}
