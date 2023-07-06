package frontend;

import backend.CanvasState;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {

    public MainFrame(CanvasState canvasState) {
        StatusPane statusPane = new StatusPane();
        ActionMenu undoAndRedo = new ActionMenu();
        TagsBar tagsBar = new TagsBar();
        getChildren().addAll(new AppMenuBar(), undoAndRedo, new PaintPane(canvasState, statusPane, undoAndRedo, tagsBar), tagsBar, statusPane);
    }

}
