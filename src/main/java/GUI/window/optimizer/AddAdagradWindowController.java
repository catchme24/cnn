package GUI.window.optimizer;

import GUI.model.SharedState;
import GUI.model.TableRowImpl;
import GUI.window.layer.AddActivationLayerWindow;
import function.activation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import network.layer.Activation3DLayer;
import network.layer.ActivationLayer;
import network.layer.Layer;
import optimizer.Adagrad;

import java.util.ArrayList;
import java.util.List;

public class AddAdagradWindowController {

    @FXML
    private TextField learningRateField;

    @FXML
    private TextField epsilonField;

    @FXML
    private TextField weightDecayField;

    @FXML
    private Button addOptimizerButton;
    @FXML
    private Button cancelButton;

    public void init(AddAdagradWindow currentWindow, SharedState sharedState) {

        addOptimizerButton.setOnAction(e -> {
            System.out.println("Adding optimizer...");

            String weightDecayLine = weightDecayField.getText();
            String epsilonLine = epsilonField.getText();
            String learningRateLine = learningRateField.getText();

            Double weightDecay = !weightDecayLine.isEmpty() ? Double.parseDouble(weightDecayLine) : 0.0;
            Double epsilon = !epsilonLine.isEmpty() ? Double.parseDouble(epsilonLine) : 0.0000001;
            Double learningRate = !learningRateLine.isEmpty() ? Double.parseDouble(learningRateLine) : 0.001;

            Adagrad adagrad = new Adagrad(learningRate, epsilon, weightDecay);
            sharedState.getModel().setOptimizer(adagrad);

            System.out.println("End optimizer...");
            currentWindow.close();
        });

        cancelButton.setOnAction(e -> {
            if (sharedState.getModel().getOptimizer() == null) {
                sharedState.getOptimizersSelection().getSelectionModel().clearSelection();
            }
            currentWindow.close();
        });
    }
}
