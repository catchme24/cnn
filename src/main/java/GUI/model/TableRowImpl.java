package GUI.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class TableRowImpl {
    private String name;
    private String description;

    public TableRowImpl(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
