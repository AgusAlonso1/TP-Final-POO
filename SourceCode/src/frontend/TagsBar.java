package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class TagsBar extends HBox {
    private static double SPACING = 10;
    private RadioButton allTagsButton, specificTagsButton;
    private TextField specificTags;
    public TagsBar() {
        setStyle("-fx-background-color: #999");
        setAlignment(Pos.CENTER);
        Label label = new Label("Mostrar Etiquetas:");
        this.allTagsButton = new RadioButton("Todas");
        this.specificTagsButton = new RadioButton("Solo");
        ToggleGroup toggleGroup = new ToggleGroup();
        allTagsButton.setToggleGroup(toggleGroup);
        this.specificTags = new TextField();
        this.getChildren().addAll(label,allTagsButton,specificTagsButton,specificTags);
        setSpacing(SPACING);
    }
}
