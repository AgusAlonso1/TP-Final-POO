package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LayerSelector extends HBox {

    private final static int amountOfLayers = 3;
    List<CheckBox> checkBoxes = new ArrayList<>();

    public LayerSelector() {
        setStyle("-fx-background-color: #999");
        getChildren().add(new Label("Mostra Capas: "));
        for (int i=1; i <= amountOfLayers; i++) {
            CheckBox checkBox = new CheckBox("Layer %d ".formatted(i));
            checkBox.setIndeterminate(false); //Set undetermined to false
            checkBox.setSelected(true);
            checkBoxes.add(checkBox);
            getChildren().add(checkBox);
        }
        this.setAlignment(Pos.CENTER);
    }

    public List<String> layersName() {
        Stream<CheckBox> stream = checkBoxes.stream();
        return stream.map(Labeled::getText).toList();
    }

    public List<CheckBox> getLayers() {
        return checkBoxes;
    }
}
