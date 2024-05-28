package GUI.window;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public interface Window {

    public void init();
    public void display(Stage owner);

    public void setOnCloseHandler(EventHandler<WindowEvent> closeHandler);

    public void close();
}
