package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.StringTokenizer;

public class TagsBar extends HBox {
    private static double SPACING = 10;
    private RadioButton allTagsButton, specificTagsButton;
    private TextField specificTag;
    public TagsBar() {
        setStyle("-fx-background-color: #999");
        setAlignment(Pos.CENTER);
        Label label = new Label("Mostrar Etiquetas:");
        this.allTagsButton = new RadioButton("Todas");
        allTagsButton.setSelected(true);
        this.specificTagsButton = new RadioButton("Solo");
        ToggleGroup toggleGroup = new ToggleGroup();
        allTagsButton.setToggleGroup(toggleGroup);
        specificTagsButton.setToggleGroup(toggleGroup);
        this.specificTag = new TextField();
        this.getChildren().addAll(label, allTagsButton, specificTagsButton, specificTag);
        setSpacing(SPACING);
    }
    public RadioButton getAllTagsButton() {
        return allTagsButton;
    }
    public RadioButton getSpecificTagsButton() {
        return specificTagsButton;
    }
    public String getSpecificTagText() {
        StringTokenizer tokenizer = new StringTokenizer(specificTag.getText()," ");
        return tokenizer.nextToken();
    }
}
