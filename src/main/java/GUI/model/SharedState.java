package GUI.model;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import lombok.Data;
import lombok.Getter;

@Data
public class SharedState {

    private Boolean needReplace;
    private TableView<TableRowImpl> descriptionTable;
    private ChoiceBox optimizersSelection;
    private ChoiceBox layersSelection;
    private Model model;
    private int replaceIndex = -1;

    private String currentPath;

    private String loadPath;
    private String savePath;
}
