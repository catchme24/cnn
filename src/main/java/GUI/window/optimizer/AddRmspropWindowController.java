package GUI.window.optimizer;

import GUI.model.SharedState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import optimizer.Adagrad;
import optimizer.RMSprop;

public class AddRmspropWindowController {

    @FXML
    private TextField learningRateField;

    @FXML
    private TextField epsilonField;

    @FXML
    private TextField weightDecayField;
    @FXML
    private TextField gammaField;
    @FXML
    private TextField initValueField;

    @FXML
    private Button addOptimizerButton;
    @FXML
    private Button cancelButton;

    public void init(AddRmspropWindow currentWindow, SharedState sharedState) {

        addOptimizerButton.setOnAction(e -> {
            System.out.println("Adding optimizer...");

            String weightDecayLine = weightDecayField.getText();
            String epsilonLine = epsilonField.getText();
            String learningRateLine = learningRateField.getText();
            String initValueLine = initValueField.getText();
            String gammaLine = gammaField.getText();

            Double weightDecay = !weightDecayLine.isEmpty() ? Double.parseDouble(weightDecayLine) : 0.0;
            Double epsilon = !epsilonLine.isEmpty() ? Double.parseDouble(epsilonLine) : 0.0000001;
            Double learningRate = !learningRateLine.isEmpty() ? Double.parseDouble(learningRateLine) : 0.001;
            Double initValue = !initValueLine.isEmpty() ? Double.parseDouble(initValueLine) : 0.001;
            Double gamma = !gammaLine.isEmpty() ? Double.parseDouble(gammaLine) : 0.001;

            RMSprop rmSprop = new RMSprop(learningRate, epsilon, gamma, initValue, weightDecay);
            sharedState.getModel().setOptimizer(rmSprop);

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
