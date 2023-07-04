package frontend;

import backend.CanvasState;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {

    public MainFrame(CanvasState canvasState) {
        StatusPane statusPane = new StatusPane();
        getChildren().addAll(new AppMenuBar(), new PaintPane(canvasState, statusPane), statusPane);
    }

}
