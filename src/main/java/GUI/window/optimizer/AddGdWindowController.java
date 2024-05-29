package GUI.window.optimizer;

import GUI.model.SharedState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import optimizer.Adagrad;
import optimizer.GD;

public class AddGdWindowController {

    @FXML
    private TextField learningRateField;

    @FXML
    private TextField weightDecayField;

    @FXML
    private Button addOptimizerButton;
    @FXML
    private Button cancelButton;

    public void init(AddGdWindow currentWindow, SharedState sharedState) {

        addOptimizerButton.setOnAction(e -> {
            System.out.println("Adding optimizer...");

            String weightDecayLine = weightDecayField.getText();
            String learningRateLine = learningRateField.getText();

            Double weightDecay = !weightDecayLine.isEmpty() ? Double.parseDouble(weightDecayLine) : 0.0;
            Double learningRate = !learningRateLine.isEmpty() ? Double.parseDouble(learningRateLine) : 0.001;

            GD gd = new GD(learningRate, weightDecay);
            sharedState.getModel().setOptimizer(gd);

            System.out.println("End optimizer...");
            currentWindow.close();
        });

        cancelButton.setOnAction(e -> {
//            if (sharedState.getModel().getOptimizer() == null) {
//                sharedState.getOptimizersSelection().getSelectionModel().clearSelection();
//            }
            currentWindow.close();
        });
    }
}
