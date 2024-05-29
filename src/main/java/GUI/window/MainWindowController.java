package GUI.window;

import GUI.model.Model;
import GUI.model.SharedState;
import GUI.model.TableRowImpl;
import GUI.window.layer.*;
import GUI.window.optimizer.AddAdagradWindow;
import GUI.window.optimizer.AddGdWindow;
import GUI.window.optimizer.AddRmspropWindow;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import network.layer.Flatten;
import optimizer.Adagrad;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {

    //Выбор данных
    @FXML
    private Button pickTrainDataButton;
    @FXML
    private TextField trainDataField;

    @FXML
    private Button pickValidDataButton;
    @FXML
    private TextField validDataField;

    @FXML
    private Button pickTestDataButton;
    @FXML
    private TextField testDataField;

    @FXML
    private Button downloadDataButton;

    //Обучение
    @FXML
    private Button pickLogFileButton;
    @FXML
    private TextField logFileField;
    @FXML
    private CheckBox loggingFlag;
    @FXML
    private ChoiceBox optimizersSelection;
    private List<String> optimizersNames = new ArrayList(List.of(
            "Adagrad",
            "GD",
            "RMSprop"
    ));

    @FXML
    private TextField epochCountField;
    @FXML
    private CheckBox validationFlag;
    @FXML
    private CheckBox labelSmoothingFlag;
    @FXML
    private CheckBox weightDecayFlag;
    @FXML
    private Button trainButton;
    @FXML
    private Button testButton;

    //Меню
    @FXML
    private MenuItem createNetwork;
    @FXML
    private MenuItem importNetwork;
    @FXML
    private MenuItem exportNetwork;
    @FXML
    private StackPane mainStackPane;

    //Конфигурация сети
    @FXML
    private ChoiceBox layersSelection;
    @FXML
    private TableView<TableRowImpl> descriptionTable;
    @FXML
    private Button addLayerButton;
    @FXML
    private Button changeLayerButton;

    @FXML
    private Button deleteLayerButton;
    private List<String> layersNames = new ArrayList(List.of("Activation3DLayer",
                                                                "ActivationLayer",
                                                                "ConvolutionLayer",
                                                                "DropoutLayer",
                                                                "Flatten",
                                                                "FullyConnected",
                                                                "PoolingLayer"
    ));


    //TabPane
    @FXML
    private VBox tabConfig;
    @FXML
    private AnchorPane tabData;

    @FXML
    private MenuItem errorMenuItem;


    //Модель данных
    private SharedState sharedState;
    private Stage currentWindow;

    public void init(Stage mainWindow) {
        currentWindow = mainWindow;
        Model model = new Model();
        sharedState = new SharedState();
        sharedState.setModel(model);
        sharedState.setLayersSelection(layersSelection);
        sharedState.setOptimizersSelection(optimizersSelection);
        trainButton.setDisable(true);
        testButton.setDisable(true);

        //Настройка таблицы со слоями
        TableColumn<TableRowImpl, String> nameCol = new TableColumn<>("Название");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<TableRowImpl, String> descCol = new TableColumn<>("Описание");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionTable.getColumns().clear();
        descriptionTable.getColumns().addAll(nameCol, descCol);
        sharedState.setDescriptionTable(descriptionTable);
        descriptionTable.refresh();

        mainStackPane.setDisable(true);

        layersSelection.getItems().clear();
        layersSelection.getItems().addAll(layersNames);
        layersSelection.getSelectionModel().select(0);
        optimizersSelection.getItems().clear();
        optimizersSelection.getItems().addAll(optimizersNames);

        loggingFlag.setOnAction(e -> {
            logFileField.setDisable(!loggingFlag.isSelected());
            pickLogFileButton.setDisable(!loggingFlag.isSelected());
        });
        loggingFlag.setSelected(false);
        logFileField.setDisable(true);
        pickLogFileButton.setDisable(true);

        pickTrainDataButton.setOnAction(e -> {
            FileBrowser fileBrowser = null;
            try {
                fileBrowser = new FileBrowser(sharedState);
                fileBrowser.setOnCloseHandler(e2 -> {
                    trainDataField.setText(sharedState.getCurrentPath());
                });
                fileBrowser.init();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            fileBrowser.display(mainWindow);
        });

        pickValidDataButton.setOnAction(e -> {
            FileBrowser fileBrowser = null;
            try {
                fileBrowser = new FileBrowser(sharedState);
                fileBrowser.setOnCloseHandler(e2 -> {
                    validDataField.setText(sharedState.getCurrentPath());
                });
                fileBrowser.init();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            fileBrowser.display(mainWindow);
        });

        pickTestDataButton.setOnAction(e -> {
            FileBrowser fileBrowser = null;
            try {
                fileBrowser = new FileBrowser(sharedState);
                fileBrowser.setOnCloseHandler(e2 -> {
                    testDataField.setText(sharedState.getCurrentPath());
                });
                fileBrowser.init();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            fileBrowser.display(mainWindow);
        });

        pickLogFileButton.setOnAction(e -> {
            FileBrowser fileBrowser = null;
            try {
                fileBrowser = new FileBrowser(sharedState);
                fileBrowser.setOnCloseHandler(e2 -> {
                    logFileField.setText(sharedState.getCurrentPath());
                });
                fileBrowser.init();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            fileBrowser.display(mainWindow);
        });

        createNetwork.setOnAction(e -> {
            init(mainWindow);
            trainButton.setDisable(true);
            mainStackPane.setDisable(false);
        });

        optimizersSelection.setOnAction(e -> {
            String selectedItem = (String) optimizersSelection.getSelectionModel().getSelectedItem();

            switch (selectedItem) {
                case "Adagrad":
                    openAdagradWindow();
                    break;
                case "GD":
                    openGdWindow();
                    break;
                case "RMSprop":
                    openRMSpropWindow();
                    break;
            }
        });

        importNetwork.setOnAction(e -> {
                FileBrowser fileBrowser = null;
                try {
                    fileBrowser = new FileBrowser(sharedState);
                    fileBrowser.setOnCloseHandler(e2 -> {
                        sharedState.setLoadPath(sharedState.getCurrentPath());
                        if (sharedState.getLoadPath() != null) {
                            mainStackPane.setDisable(false);
                            testButton.setDisable(false);
                        }
                    });
                    fileBrowser.init();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                fileBrowser.display(mainWindow);
        });

        exportNetwork.setOnAction(e -> {
            FileBrowser fileBrowser = null;
            try {
                fileBrowser = new FileBrowser(sharedState);
                fileBrowser.setOnCloseHandler(e2 -> {
                    sharedState.setSavePath(sharedState.getCurrentPath());
                    if (sharedState.getSavePath() != null) {
                        sharedState.getModel().saveInFile(new File(sharedState.getSavePath()));
                    }
                });
                fileBrowser.init();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            fileBrowser.display(mainWindow);
        });

        addLayerButton.setOnAction(e -> {
            String selectedItem = (String) layersSelection.getSelectionModel().getSelectedItem();
            sharedState.setNeedReplace(false);
            switch (selectedItem) {
                case "ActivationLayer":
                    openActivationWindow();
                    break;
                case "Activation3DLayer":
                    openActivationWindow();
                    break;
                case "ConvolutionLayer":
                    openConvolutionWindow();
                    break;
                case "DropoutLayer":
                    openDropoutWindow();
                    break;
                case "Flatten":
                    Flatten flatten = new Flatten();
                    model.addLayer(flatten);
                    String name = "Flatten";
                    descriptionTable.getItems().add(new TableRowImpl(name, ""));
                    sharedState.getDescriptionTable().refresh();
                    break;
                case "FullyConnected":
                    openFullyConnectedWindow();
                    break;
                case "PoolingLayer":
                    openPoolingWindow();
                    break;
            }

        });

        changeLayerButton.setOnAction(e -> {
            int selectedItemIndex = descriptionTable.getSelectionModel().getSelectedIndex();
            String selectedItem = descriptionTable.getSelectionModel().getSelectedItem().getName();
            sharedState.setNeedReplace(true);
            sharedState.setReplaceIndex(selectedItemIndex);


            System.out.println(selectedItem);
            switch (selectedItem) {
                case "ActivationLayer":
                    openActivationWindow();
                    break;
                case "Activation3DLayer":
                    openActivationWindow();
                    break;
                case "ConvolutionLayer":
                    openConvolutionWindow();
                    break;
                case "DropoutLayer":
                    openDropoutWindow();
                    break;
                case "Flatten":
                    Flatten flatten = new Flatten();
                    model.replace(selectedItemIndex, flatten);
                    String name = "Flatten";
                    descriptionTable.getItems().remove(selectedItemIndex);
                    descriptionTable.getItems().add(selectedItemIndex, new TableRowImpl(name, ""));
                    sharedState.getDescriptionTable().refresh();
                    break;
                case "FullyConnected":
                    openFullyConnectedWindow();
                    break;
                case "PoolingLayer":
                    openPoolingWindow();
                    break;
            }
        });

        deleteLayerButton.setOnAction(e -> {
            int selectedItemIndex = descriptionTable.getSelectionModel().getSelectedIndex();
            String selectedItem = descriptionTable.getSelectionModel().getSelectedItem().getName();

            sharedState.getModel().deleteLayer(selectedItemIndex);
            descriptionTable.getItems().remove(selectedItemIndex);
        });

        downloadDataButton.setOnAction(e -> {
            downloadDataButton.setDisable(true);

            String testPath = testDataField.getText();
            String trainPath = trainDataField.getText();
            String validPath = validDataField.getText();

            model.setTestPath(testPath);
            model.setValidPath(trainPath);
            model.setTrainPath(validPath);

            new Thread(() -> {
                try {
                    model.downloadData();
                    trainButton.setDisable(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                downloadDataButton.setDisable(false);
            }).start();
        });


        trainButton.setOnAction(e -> {
            tabConfig.setDisable(true);
            tabData.setDisable(true);

            if (loggingFlag.isSelected() && !logFileField.getText().isBlank()) {
                model.setLogs(new File(logFileField.getText()));
            }

            if (sharedState.getLoadPath() != null) {
                sharedState.getModel().setLoad(new File(sharedState.getLoadPath()));
            }

            model.endBuild();
            model.getTrainableNetwork().setOptimizer(model.getOptimizer());
            trainButton.setDisable(true);

            new Thread(() -> {
                model.getTrainableNetwork().train(
                        Integer.parseInt(epochCountField.getText()),
                             model.getDataset(),
                        Boolean.parseBoolean(validationFlag.getText()));

                tabData.setDisable(false);
                testButton.setDisable(false);
            }).start();

        });

        errorMenuItem.setOnAction(e -> {
            ErrorWindow errorWindow = null;
            try {
                errorWindow = new ErrorWindow(sharedState);
                errorWindow.setOnCloseHandler(e2 -> {
                    sharedState.setLoadPath(sharedState.getCurrentPath());
                    if (sharedState.getLoadPath() != null) {
                        mainStackPane.setDisable(false);
                        testButton.setDisable(false);
                    }
                });
                errorWindow.init();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            errorWindow.display(mainWindow);
        });
    }

    private void openActivationWindow() {
        AddActivationLayerWindow addLayer = null;
        try {
            addLayer = new AddActivationLayerWindow(sharedState);
            addLayer.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        addLayer.display(currentWindow);
    }

    private void openConvolutionWindow() {
        AddConvolutionLayerWindow addLayer = null;
        try {
            addLayer = new AddConvolutionLayerWindow(sharedState);
            addLayer.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        addLayer.display(currentWindow);
    }

    private void openFullyConnectedWindow() {
        AddFullyConnectedLayerWindow addLayer = null;
        try {
            addLayer = new AddFullyConnectedLayerWindow(sharedState);
            addLayer.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        addLayer.display(currentWindow);
    }

    private void openPoolingWindow() {
        AddPoolingLayerWindow addLayer = null;
        try {
            addLayer = new AddPoolingLayerWindow(sharedState);
            addLayer.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        addLayer.display(currentWindow);
    }

    private void openDropoutWindow() {
        AddDropoutLayerWindow addLayer = null;
        try {
            addLayer = new AddDropoutLayerWindow(sharedState);
            addLayer.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        addLayer.display(currentWindow);
    }

    private void openAdagradWindow() {
        AddAdagradWindow addOptimizer = null;
        try {
            addOptimizer = new AddAdagradWindow(sharedState);
            addOptimizer.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        addOptimizer.display(currentWindow);
    }

    private void openRMSpropWindow() {
        AddRmspropWindow addOptimizer = null;
        try {
            addOptimizer = new AddRmspropWindow(sharedState);
            addOptimizer.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        addOptimizer.display(currentWindow);
    }

    private void openGdWindow() {
        AddGdWindow addOptimizer = null;
        try {
            addOptimizer = new AddGdWindow(sharedState);
            addOptimizer.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        addOptimizer.display(currentWindow);
    }

}
