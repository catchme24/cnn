package GUI.window;

import GUI.FilePathTreeItem;
import GUI.model.SharedState;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

import java.io.IOException;

public class FileBrowserController {

    @FXML
    private Button choseFileButton;

    @FXML
    private Button cancelButton;


    public void init(FileBrowser currentWindow, SharedState sharedState) {

        choseFileButton.setOnAction(e -> {
            FilePathTreeItem selectedItem = (FilePathTreeItem) currentWindow.getTreeView().getSelectionModel().getSelectedItem();
            sharedState.setCurrentPath(selectedItem.getFullPath());
            currentWindow.close();
        });

        cancelButton.setOnAction(e -> {
            currentWindow.close();
        });
    }

    @FXML
    private void click(Event event) {

    }
}