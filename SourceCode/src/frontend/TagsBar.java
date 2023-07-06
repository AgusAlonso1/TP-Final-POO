package frontend;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class TagsBar extends HBox {
    private static double SPACING = 10;
    private RadioButton allTagsButton, specificTagsButton;
    private TextField specificTags;
    public TagsBar() {
        Label label = new Label("Mostrar Etiquetas:");
        this.allTagsButton = new RadioButton("Todas");
        this.specificTagsButton = new RadioButton("Solo");
        this.specificTags = new TextField();
        this.getChildren().addAll(label,allTagsButton,specificTagsButton,specificTags);
        setSpacing(SPACING);
    }
}
