package app;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Main_View;
public class Main_App extends Application{
    @Override
    public void start(Stage stage) {
        App_Module module = new App_Module();

        Main_View view = new Main_View(module.transactionService());
        Scene scene = new Scene(view.getRoot(), 980, 600);

        stage.setTitle("Financial Management");
        stage.setScene(scene);
        stage.show();

        view.refresh();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
