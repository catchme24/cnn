package GUI.window.layer;

import GUI.model.SharedState;
import GUI.model.TableRowImpl;
import GUI.window.layer.AddPoolingLayerWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import network.layer.PoolingLayer;

public class AddPoolingLayerWindowController {

    @FXML
    private TextField kernelsSizeField;
    @FXML
    private TextField strideField;

    @FXML
    private Button addLayerButton;
    @FXML
    private Button cancelButton;

    public void init(AddPoolingLayerWindow currentWindow, SharedState sharedState) {

        addLayerButton.setOnAction(e -> {
            System.out.println("Adding layer...");
            String kernelsSize =  kernelsSizeField.getText();
            String stride =  strideField.getText();

            PoolingLayer poolingLayer = new PoolingLayer(Integer.parseInt(kernelsSize), Integer.parseInt(stride));

            String name = "PoolingLayer";
            StringBuilder desc = new StringBuilder()
                    .append("Размер фильтра: ")
                    .append(kernelsSize)
                    .append(", шаг: ")
                    .append(stride);

            if (sharedState.getNeedReplace()) {
                sharedState.getModel().replace(sharedState.getReplaceIndex(), poolingLayer);
                sharedState.getDescriptionTable().getItems().remove(sharedState.getReplaceIndex());
                sharedState.getDescriptionTable().getItems().add(sharedState.getReplaceIndex(), new TableRowImpl(name, desc.toString()));
            } else {
                sharedState.getModel().addLayer(poolingLayer);
                sharedState.getDescriptionTable().getItems().add(new TableRowImpl(name, desc.toString()));
            }
            sharedState.getDescriptionTable().refresh();
            currentWindow.close();
            System.out.println("End adding layer...");
        });

        cancelButton.setOnAction(e -> {
            currentWindow.close();
        });
    }
}
