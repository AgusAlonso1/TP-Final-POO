package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class LayerSelector extends HBox {
    private final CheckBox layer1, layer2, layer3;
    private final Label label;
    public LayerSelector() {
        setStyle("-fx-background-color: #999");
        this.label = new Label("Mostrar Capas: ");
        this.layer1 = new CheckBox("Layer 1");
        this.layer2 = new CheckBox("Layer 2");
        this.layer3 = new CheckBox("Layer 3");
        this.getChildren().addAll(label,layer1,layer2,layer3);
        this.setAlignment(Pos.CENTER);
    }
}
