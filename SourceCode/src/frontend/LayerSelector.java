package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class LayerSelector extends HBox {
    public LayerSelector() {
        setStyle("-fx-background-color: #999");
        CheckBox layer1 = new CheckBox("Layer 1 ");
        CheckBox layer2 = new CheckBox("Layer 2 ");
        CheckBox layer3 = new CheckBox("Layer 3 ");
        getChildren().addAll(new Label("Mostra Capas: "), layer1, layer2, layer3);
        this.setAlignment(Pos.CENTER);

    }
}
