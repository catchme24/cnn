package GUI.window.layer;

import GUI.model.SharedState;
import GUI.model.TableRowImpl;
import GUI.window.layer.AddConvolutionLayerWindow;
import function.initializer.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import network.layer.ConvolutionLayer;
import network.layer.Dimension;

import java.util.ArrayList;
import java.util.List;

public class AddConvolutionLayerWindowController {

    @FXML
    private TextField kernelsCountField;
    @FXML
    private TextField kernelsSizeField;
    @FXML
    private TextField strideField;

    @FXML
    private TextField channelSize;
    @FXML
    private TextField heightSize;
    @FXML
    private TextField widthSize;

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

    public void init(AddConvolutionLayerWindow currentWindow, SharedState sharedState) {

        initializersSelection.getItems().addAll(initializersNames);

        addLayerButton.setOnAction(e -> {
            System.out.println("Adding layer...");
            String kernelsCount = kernelsCountField.getText();
            String kernelsSize =  kernelsSizeField.getText();
            String stride =  strideField.getText();

            String channelSizeValue = channelSize.getText();
            String heightSizeValue = heightSize.getText();
            String widthSizeValue = widthSize.getText();

            Dimension dimension = null;
            if (!channelSizeValue.isBlank() &  !heightSizeValue.isBlank() & !widthSizeValue.isBlank()) {
                dimension = new Dimension(Integer.parseInt(channelSizeValue),
                                        Integer.parseInt(heightSizeValue),
                                        Integer.parseInt(widthSizeValue));
            }

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

            ConvolutionLayer convolutionLayer;
            if (dimension == null) {
                convolutionLayer = new ConvolutionLayer(
                        Integer.parseInt(kernelsCount),
                        Integer.parseInt(kernelsSize),
                        Integer.parseInt(stride),
                        initializer
                );
            } else {
                convolutionLayer = new ConvolutionLayer(
                        Integer.parseInt(kernelsCount),
                        Integer.parseInt(kernelsSize),
                        Integer.parseInt(stride),
                        dimension,
                        initializer
                );
            }

            String name = "ConvolutionLayer";
            StringBuilder desc = new StringBuilder()
                    .append("Кол-во фильтров: ")
                    .append(kernelsCount)
                    .append(", размер: ")
                    .append(kernelsSize)
                    .append(", шаг: ")
                    .append(stride);

            if (!channelSizeValue.isBlank() & !heightSizeValue.isBlank() & !widthSizeValue.isBlank()) {
                desc.append(" размер входа: ")
                        .append(channelSizeValue)
                        .append("x")
                        .append(heightSizeValue)
                        .append("x")
                        .append(widthSizeValue);
            }

            if (sharedState.getNeedReplace()) {
                sharedState.getModel().replace(sharedState.getReplaceIndex(), convolutionLayer);
                sharedState.getDescriptionTable().getItems().remove(sharedState.getReplaceIndex());
                sharedState.getDescriptionTable().getItems().add(sharedState.getReplaceIndex(), new TableRowImpl(name, desc.toString()));
            } else {
                sharedState.getModel().addLayer(convolutionLayer);
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
