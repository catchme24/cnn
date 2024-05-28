package GUI.window.optimizer;

import GUI.model.SharedState;
import GUI.window.Window;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;

import java.io.IOException;

public class AddGdWindow implements Window {

    private VBox mainElement;

    private Stage currentWindow;

    private Stage currentOwner;

    private AddGdWindowController controller;

    @Getter
    private SharedState sharedState;


    public AddGdWindow(SharedState sharedState) throws IOException {
        this.sharedState = sharedState;
        String nameOfModel = "/fxmls/optimizers/gdWindow.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(nameOfModel));

        Parent panel = loader.load();
        mainElement = (VBox) panel;

        currentWindow = new Stage();
        controller = loader.getController();
    }
    @Override
    public void init() {
        controller.init(this, sharedState);
    }

    @Override
    public void display(Stage owner) {
        //setup and show the window
        currentOwner = owner;
        Scene scene = new Scene(mainElement);
        currentWindow.setTitle("Добавление оптимизатора");
        currentWindow.initModality(Modality.WINDOW_MODAL);
        currentWindow.initOwner(currentOwner);
        currentWindow.setScene(scene);
        currentWindow.show();
    }

    @Override
    public void setOnCloseHandler(EventHandler<WindowEvent> closeHandler) {

    }

    @Override
    public void close() {
        currentWindow.close();
    }
}
