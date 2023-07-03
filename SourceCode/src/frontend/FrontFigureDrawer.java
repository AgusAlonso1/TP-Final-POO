package frontend;

import backend.model.FigureDrawer;
import javafx.scene.canvas.GraphicsContext;

public class FrontFigureDrawer implements FigureDrawer {
    private final GraphicsContext gc;
    public FrontFigureDrawer(GraphicsContext gc) {
        this.gc = gc;
    }


}
