package frontend.customTools;

import backend.FigureDrawer;
import backend.model.Format;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class FrontFigureDrawer implements FigureDrawer {
    private final GraphicsContext gc;
    public FrontFigureDrawer(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void drawEllipse(Format format, double xUpperLeftBound, double yUpperLeftBound, double width, double height) {
        setFormat(format);
        gc.fillOval(xUpperLeftBound,yUpperLeftBound,width,height);
        gc.strokeOval(xUpperLeftBound,yUpperLeftBound,width,height);
    }

    @Override
    public void drawCircle(Format format, double xUpperLeftBound, double yUpperLeftBound, double diameter) {
        drawEllipse(format,xUpperLeftBound,yUpperLeftBound,diameter,diameter);
    }

    @Override
    public void drawRectangle(Format format, double xUpperLeftBound, double yUpperLeftBound, double width, double height) {
        setFormat(format);
        gc.fillRect(xUpperLeftBound,yUpperLeftBound,width,height);
        gc.strokeRect(xUpperLeftBound,yUpperLeftBound,width,height);
    }

    @Override
    public void drawSquare(Format format, double xUpperLeftBound, double yUpperLeftBound, double side) {
        drawRectangle(format,xUpperLeftBound,yUpperLeftBound,side,side);
    }

    private void setFormat(Format format) {
        gc.setStroke(Color.web(format.getLineColor()));
        gc.setFill(Color.web(format.getFillColor()));
        gc.setLineWidth(format.getLineWidth());
    }

}
