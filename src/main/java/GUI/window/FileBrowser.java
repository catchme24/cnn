package GUI.window;

import GUI.FilePathTreeItem;
import GUI.model.SharedState;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class FileBrowser implements Window {

    @Getter
    private TreeView<String> treeView;
    private VBox mainElement;
    private Stage currentWindow;

    private FileBrowserController controller;

    @Getter
    private SharedState sharedState;

    public FileBrowser(SharedState sharedState) throws IOException {
        this.sharedState = sharedState;
        String nameOfModel = "/fxmls/fileBrowser1.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(nameOfModel));

        Parent panel = loader.load();

        mainElement = (VBox) panel;

        currentWindow = new Stage();
        controller = loader.getController();
    }

    @Override
    public void init() {
        controller.init(this, sharedState);
    }

    @Override
    public void display(Stage owner) {
        String hostName = "computer";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException x) {};

        TreeItem<String> rootNode = new TreeItem<>(hostName,new ImageView(new Image(ClassLoader.getSystemResourceAsStream("images/computer.png"))));
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();

        for (Path path: rootDirectories) {
            FilePathTreeItem treeNode = new FilePathTreeItem(path);
            rootNode.getChildren().add(treeNode);
        }
        //create the tree view
        treeView = new TreeView<>(rootNode);
        StackPane node = (StackPane) mainElement.getChildren().get(0);
        node.getChildren().add(treeView);

        //setup and show the window
        Scene scene = new Scene(mainElement);
        currentWindow.setTitle("Выбор файла");
        currentWindow.initModality(Modality.WINDOW_MODAL);
        currentWindow.initOwner(owner);
        currentWindow.setScene(scene);
        currentWindow.show();
    }

    @Override
    public void setOnCloseHandler(EventHandler<WindowEvent> closeHandler) {
        this.currentWindow.setOnHiding(closeHandler);
    }

    @Override
    public void close() {
        currentWindow.close();
    }

}
