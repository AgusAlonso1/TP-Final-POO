package frontend;

import backend.model.FigureDrawer;
import backend.model.Format;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class FrontFigureDrawer implements FigureDrawer {
    private final GraphicsContext gc;
    public FrontFigureDrawer(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void drawRectangle(Format format, double xUpperLeftBound, double yUpperLeftBound, double width, double height) {

    }

    @Override
    public void drawEllipse(Format format, double xUpperLeftBound, double yUpperLeftBound, double width, double height) {

    }

    @Override
    public void drawCircle(Format format, double xUpperLeftBound, double yUpperLeftBound, double diameter) {

    }

    @Override
    public void drawSquare(Format format, double xUpperLeftBound, double yUpperLeftBound, double height) {

    }




}
