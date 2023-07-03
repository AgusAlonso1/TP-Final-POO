package frontend.DrawFigures;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;

public abstract class DrawFigure {
    private Figure figure;
    public DrawFigure(Figure figure){
        this.figure = figure;
    }
    public abstract void drawFigureOnCanvas(GraphicsContext gc);

}
