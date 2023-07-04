package frontend;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class LayerSelector extends AnchorPane {
    private final CheckBox layer1, layer2, layer3;
    private final Label label;
    public LayerSelector() {
        setStyle("-fx-background-color: #FFFFFF");
        this.label = new Label("Mostrar Capas: ");
        this.layer1 = new CheckBox("Layer 1");
        this.layer2 = new CheckBox("Layer 2");
        this.layer3 = new CheckBox("Layer 3");
    }
}
