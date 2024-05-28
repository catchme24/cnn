package GUI.window.layer;

import GUI.model.SharedState;
import GUI.model.TableRowImpl;
import GUI.window.layer.AddActivationLayerWindow;
import function.activation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import network.layer.*;

import java.util.ArrayList;
import java.util.List;

public class AddActivationLayerWindowController {

    @FXML
    private ChoiceBox activationFuncSelection;
    private List<String> functionsNames = new ArrayList(List.of("LeakyReLu",
                                                                    "Logistic",
                                                                    "ReLu",
                                                                    "Sin",
                                                                    "Softmax",
                                                                    "Tang"
    ));

    @FXML
    private Button addLayerButton;
    @FXML
    private Button cancelButton;

    public void init(AddActivationLayerWindow currentWindow, SharedState sharedState) {

        activationFuncSelection.getItems().addAll(functionsNames);

        addLayerButton.setOnAction(e -> {
            System.out.println("Adding layer...");

            String selectedItem = (String) activationFuncSelection.getSelectionModel().getSelectedItem();

            ActivationFunc func = null;
            switch (selectedItem) {
                case "LeakyReLu":
                    func = new LeakyReLu();
                    break;
                case "Logistic":
                    func = new Logistic();
                    break;
                case "ReLu":
                    func = new ReLu();
                    break;
                case "Sin":
                    func = new Sin();
                    break;
                case "Softmax":
                    func = new Softmax();
                case "Tang":
                    func = new Tang();
                    break;
                default:
                    func = new ReLu();
            }

            Layer activationLayer;
            String layerName = null;
            if (sharedState.getNeedReplace()) {
                layerName = sharedState.getDescriptionTable().getSelectionModel().getSelectedItem().getName();

                if (layerName.equals("Activation3DLayer")) {
                    activationLayer = new Activation3DLayer(func);
                } else {
                    activationLayer = new ActivationLayer(func);
                }
            } else {
                layerName = (String) sharedState.getLayersSelection().getSelectionModel().getSelectedItem();

                if (layerName.equals("Activation3DLayer")) {
                    activationLayer = new Activation3DLayer(func);
                } else {
                    activationLayer = new ActivationLayer(func);
                }
            }


            String desc = new StringBuilder()
                    .append("Функция активации: ")
                    .append(selectedItem)
                    .toString();
            if (sharedState.getNeedReplace()) {
                sharedState.getModel().replace(sharedState.getReplaceIndex(), activationLayer);
                sharedState.getDescriptionTable().getItems().remove(sharedState.getReplaceIndex());
                sharedState.getDescriptionTable().getItems().add(sharedState.getReplaceIndex(), new TableRowImpl(layerName, desc));
            } else {
                sharedState.getModel().addLayer(activationLayer);
                sharedState.getDescriptionTable().getItems().add(new TableRowImpl(layerName, desc));
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
