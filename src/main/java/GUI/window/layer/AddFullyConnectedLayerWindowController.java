package GUI.window.layer;

import GUI.model.SharedState;
import GUI.model.TableRowImpl;
import GUI.window.layer.AddFullyConnectedLayerWindow;
import function.initializer.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import network.layer.FullyConnected;

import java.util.ArrayList;
import java.util.List;

public class AddFullyConnectedLayerWindowController {

    @FXML
    private TextField neurosCountField;

    @FXML
    private TextField inputCountField;

    @FXML
    private ChoiceBox initializersSelection;
    private List<String> initializersNames = new ArrayList(List.of("HeInitializer",
                                                                    "LecunItializer",
                                                                    "NormalInitializer",
                                                                    "RandomInitializer",
                                                                    "XavierInitializer"
    ));

    @FXML
    private Button addLayerButton;
    @FXML
    private Button cancelButton;

    public void init(AddFullyConnectedLayerWindow currentWindow, SharedState sharedState) {

        initializersSelection.getItems().addAll(initializersNames);

        addLayerButton.setOnAction(e -> {
            System.out.println("Adding layer...");
            String neurosCount =  neurosCountField.getText();
            String inputCount =  inputCountField.getText();


            String selectedItem = (String) initializersSelection.getSelectionModel().getSelectedItem();

            Initializer initializer = null;
            switch (selectedItem) {
                case "HeInitializer":
                    initializer = new HeInitializer();
                    break;
                case "LecunItializer":
                    initializer = new LecunItializer();
                    break;
                case "NormalInitializer":
                    initializer = new NormalInitializer();
                    break;
                case "RandomInitializer":
                    initializer = new RandomInitializer();
                    break;
                case "XavierInitializer":
                    initializer = new XavierInitializer();
                    break;
                default:
                    initializer = new HeInitializer();
            }

            FullyConnected fullyConnected;
            if (inputCount.isBlank()) {
                fullyConnected = new FullyConnected(
                        Integer.parseInt(neurosCount),
                                        initializer
                );
            } else {
                fullyConnected = new FullyConnected(
                        Integer.parseInt(neurosCount),
                        Integer.parseInt(inputCount),
                        initializer
                );
            }

            String name = "FullyConnected";
            StringBuilder desc = new StringBuilder()
                    .append("Кол-во нейронов: ")
                    .append(neurosCount);

            if (!inputCount.isBlank()) {
                desc.append(" размер входа: ")
                        .append(inputCount);
            }
            if (sharedState.getNeedReplace()) {
                sharedState.getModel().replace(sharedState.getReplaceIndex(), fullyConnected);
                sharedState.getDescriptionTable().getItems().remove(sharedState.getReplaceIndex());
                sharedState.getDescriptionTable().getItems().add(sharedState.getReplaceIndex(), new TableRowImpl(name, desc.toString()));
            } else {
                sharedState.getModel().addLayer(fullyConnected);
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
