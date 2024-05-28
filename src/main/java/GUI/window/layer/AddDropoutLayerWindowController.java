package GUI.window.layer;

import GUI.model.SharedState;
import GUI.model.TableRowImpl;
import GUI.window.layer.AddDropoutLayerWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import network.layer.DropoutLayer;

public class AddDropoutLayerWindowController {

    @FXML
    private TextField dropoutField;

    @FXML
    private Button addLayerButton;
    @FXML
    private Button cancelButton;

    public void init(AddDropoutLayerWindow currentWindow, SharedState sharedState) {

        addLayerButton.setOnAction(e -> {
            System.out.println("Adding layer...");
            String dropout = dropoutField.getText();


            DropoutLayer dropoutLayer;
            if (dropout.isBlank()) {
                dropoutLayer = new DropoutLayer(0.5);
            } else {
                dropoutLayer = new DropoutLayer(Double.parseDouble(dropout));
            }

            String name = "DropoutLayer";
            StringBuilder desc = new StringBuilder()
                    .append("Дропаут: ");

            if (dropout.isBlank()) {
                desc.append("0.5");
            } else {
                desc.append(dropout);
            }
            if (sharedState.getNeedReplace()) {
                sharedState.getModel().replace(sharedState.getReplaceIndex(), dropoutLayer);
                sharedState.getDescriptionTable().getItems().remove(sharedState.getReplaceIndex());
                sharedState.getDescriptionTable().getItems().add(sharedState.getReplaceIndex(), new TableRowImpl(name, desc.toString()));
            } else {
                sharedState.getModel().addLayer(dropoutLayer);
                sharedState.getDescriptionTable().getItems().add(new TableRowImpl(name, desc.toString()));
            }
            sharedState.getDescriptionTable().refresh();
            sharedState.setNeedReplace(false);
            currentWindow.close();
            System.out.println("End adding layer...");
        });

        cancelButton.setOnAction(e -> {
            currentWindow.close();
        });
    }
}
