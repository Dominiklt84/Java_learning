package ui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import domain.Transaction;
import domain.Validation_Exception;
import service.Transaction_Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public final class Main_View {
    private final Transaction_Service service;

    private final BorderPane root = new BorderPane();
    private final ObservableList<Transaction_Row> rows = FXCollections.observableArrayList();

    private final TableView<Transaction_Row> table = new TableView<>(rows);

    private final DatePicker fromPicker = new DatePicker(LocalDate.now().withDayOfMonth(1));
    private final DatePicker toPicker = new DatePicker(LocalDate.now());

    private final Label incomeLbl = new Label("Przychody: 0.00");
    private final Label expenseLbl = new Label("Wydatki: 0.00");
    private final Label balanceLbl = new Label("Saldo: 0.00");

    public Main_View(Transaction_Service service) {
        this.service = service;
        buildUi();
    }

    public Parent getRoot() {
        return root;
    }

    public void refresh() {
        loadWithRangeOrAll();
    }

    private void buildUi() {
        root.setPadding(new Insets(10));

        TableColumn<Transaction_Row, LocalDate> cDate = new TableColumn<>("Data");
        cDate.setCellValueFactory(v -> v.getValue().dateProperty());

        TableColumn<Transaction_Row, String> cType = new TableColumn<>("Typ");
        cType.setCellValueFactory(v -> v.getValue().typeProperty());

        TableColumn<Transaction_Row, String> cCat = new TableColumn<>("Kategoria");
        cCat.setCellValueFactory(v -> v.getValue().categoryProperty());

        TableColumn<Transaction_Row, String> cDesc = new TableColumn<>("Opis");
        cDesc.setCellValueFactory(v -> v.getValue().descriptionProperty());

        TableColumn<Transaction_Row, Number> cAmount = new TableColumn<>("Kwota");
        cAmount.setCellValueFactory(v -> v.getValue().amountProperty());

        table.getColumns().addAll(cDate, cType, cCat, cDesc, cAmount);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        Button btnAll = new Button("Pokaż wszystko");
        btnAll.setOnAction(e -> loadAll());

        Button btnFilter = new Button("Filtruj zakres");
        btnFilter.setOnAction(e -> loadRange());

        Button btnAdd = new Button("Dodaj");
        btnAdd.setOnAction(e -> onAdd());

        Button btnEdit = new Button("Edytuj");
        btnEdit.setOnAction(e -> onEdit());

        Button btnDelete = new Button("Usuń");
        btnDelete.setOnAction(e -> onDelete());

        HBox filters = new HBox(10,
                new Label("Od:"), fromPicker,
                new Label("Do:"), toPicker,
                btnFilter, btnAll
        );
        filters.setPadding(new Insets(5));

        HBox actions = new HBox(10, btnAdd, btnEdit, btnDelete);
        actions.setPadding(new Insets(5));

        VBox top = new VBox(8, filters, actions);
        root.setTop(top);

        HBox summary = new HBox(20, incomeLbl, expenseLbl, balanceLbl);
        summary.setPadding(new Insets(10));
        root.setBottom(summary);

        root.setCenter(table);
    }

    private void loadAll() {
        List<Transaction> list = service.listAll();
        setRowsAndSummary(list);
    }

    private void loadRange() {
        try {
            List<Transaction> list = service.listByDateRange(fromPicker.getValue(), toPicker.getValue());
            setRowsAndSummary(list);
        } catch (Validation_Exception ex) {
            showError("Błąd filtra", ex.getMessage());
        }
    }

    private void loadWithRangeOrAll() {
        if (fromPicker.getValue() != null && toPicker.getValue() != null) loadRange();
        else loadAll();
    }

    private void setRowsAndSummary(List<Transaction> list) {
        rows.setAll(list.stream().map(Transaction_Row::new).toList());

        var s = service.summarize(list);
        incomeLbl.setText("Przychody: " + s.income());
        expenseLbl.setText("Wydatki: " + s.expense());
        balanceLbl.setText("Saldo: " + s.balance());
    }

    private void onAdd() {
        try {
            Optional<Transaction> created = Transaction_Dialog.showAddDialog();
            created.ifPresent(tx -> {
                try {
                    service.add(tx);
                    refresh();
                } catch (Validation_Exception ex) {
                    showError("Błąd walidacji", ex.getMessage());
                }
            });
        } catch (IllegalArgumentException ex) {
            showError("Błąd", ex.getMessage());
        }
    }

    private void onEdit() {
        Transaction_Row selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Edytuj", "Zaznacz transakcję w tabeli.");
            return;
        }

        Transaction original = service.listAll().stream()
                .filter(t -> t.id() == selected.id())
                .findFirst()
                .orElse(null);

        if (original == null) {
            showError("Błąd", "Nie znaleziono transakcji do edycji.");
            return;
        }

        try {
            Optional<Transaction> edited = Transaction_Dialog.showEditDialog(original);
            edited.ifPresent(tx -> {
                try {
                    service.edit(tx);
                    refresh();
                } catch (Validation_Exception ex) {
                    showError("Błąd walidacji", ex.getMessage());
                }
            });
        } catch (IllegalArgumentException ex) {
            showError("Błąd", ex.getMessage());
        }
    }

    private void onDelete() {
        Transaction_Row selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Usuń", "Zaznacz transakcję w tabeli.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Potwierdzenie");
        confirm.setHeaderText("Usunąć transakcję?");
        confirm.setContentText("To jest nieodwracalne.");

        Optional<ButtonType> res = confirm.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            try {
                service.delete(selected.id());
                refresh();
            } catch (Validation_Exception ex) {
                showError("Błąd", ex.getMessage());
            }
        }
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
