package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class ActionMenu extends HBox {
    private Button undo, redo;
    private Label undoLabel, redoLabel;

    public ActionMenu(){
        setStyle("-fx-background-color: #999");
        setSpacing(10);
        setAlignment(Pos.CENTER);
        undo = new Button("Deshacer");
        redo = new Button("Rehacer");

        undoLabel = new Label();
        undoLabel.setAlignment(Pos.TOP_LEFT);

        redoLabel = new Label();
        redoLabel.setAlignment(Pos.TOP_RIGHT);

        getChildren().addAll(undoLabel,undo, redo,redoLabel);
    }

    public void setUndoLabel(String label){
        undoLabel.setText(label);
    }

    public void setRedoLabel(String label){
        redoLabel.setText(label);
    }

    public Button getUndo(){
        return undo;
    }

    public Button getRedo(){
        return redo;
    }

}
