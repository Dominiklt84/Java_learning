package ui;
import domain.Transaction;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import service.MoneyParser;
import service.TransactionHistoryService;

import java.time.format.DateTimeFormatter;
import java.util.List;
public class HistoryController {
    @FXML private TableView<TxRow> txTable;
    @FXML private TableColumn<TxRow, String> dateCol;
    @FXML private TableColumn<TxRow, String> typeCol;
    @FXML private TableColumn<TxRow, String> amountCol;
    @FXML private TableColumn<TxRow, String> noteCol;

    private final ObservableList<TxRow> rows = FXCollections.observableArrayList();
    private int userId;
    private TransactionHistoryService transactionHistoryService;

    public void init(int userId, TransactionHistoryService transactionHistoryService) {
        this.userId = userId;
        this.transactionHistoryService = transactionHistoryService;
        txTable.setItems(rows);

        dateCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().date));
        typeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().type));
        amountCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().amount));
        noteCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().note));
    }

    public void refresh(){
        List<Transaction> txs = transactionHistoryService.listForUser(userId,100);
        rows.setAll(txs.stream().map(TxRow::from).toList());
    }
    public static final class TxRow {
        final String date;
        final String type;
        final String amount;
        final String note;

        private TxRow(String date, String type, String amount, String note) {
            this.date = date;
            this.type = type;
            this.amount = amount;
            this.note = note;
        }

        static TxRow from(Transaction t) {
            String date = t.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String type = t.getType().name();
            String amount = MoneyParser.formatFromCents(t.getAmount());
            String note = t.getNote() == null ? "" : t.getNote();
            return new TxRow(date, type, amount, note);
        }
    }

}
