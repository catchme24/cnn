package GUI.window.layer;

import GUI.model.SharedState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ErrorWindowController {

    @FXML
    private Button cancelButton;

    public void init(ErrorWindow currentWindow, SharedState sharedState) {


        cancelButton.setOnAction(e -> {
            currentWindow.close();
        });
    }
}
