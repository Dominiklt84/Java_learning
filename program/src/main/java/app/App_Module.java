package app;
import db.Db;
import db.Sqlite_Transaction_Repository;
import port.Transaction_Repository;
import service.Transaction_Service;

public final class App_Module {

    private final Transaction_Service transactionService;

    public App_Module() {
        Db.init();

        Transaction_Repository repo = new Sqlite_Transaction_Repository();
        this.transactionService = new Transaction_Service(repo);
    }

    public Transaction_Service transactionService() {
        return transactionService;
    }
}
