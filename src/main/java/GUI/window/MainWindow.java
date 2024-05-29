package GUI.window;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainWindow extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        String nameOfModel = "/fxmls/mainWindow.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(nameOfModel));
        Parent panel = loader.load();

        MainWindowController controller = loader.getController();
        controller.init(stage);
        stage.setTitle("Application");

        //set up main scene
        Scene scene = new Scene(panel);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}