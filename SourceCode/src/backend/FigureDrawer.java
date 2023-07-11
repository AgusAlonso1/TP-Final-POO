package backend;

import backend.figures.Format;

public interface FigureDrawer {
    void drawRectangle(Format format, double xUpperLeftBound, double yUpperLeftBound, double width, double height);
    void drawEllipse(Format format, double xUpperLeftBound, double yUpperLeftBound, double width, double height);
    void drawCircle(Format format, double xUpperLeftBound, double yUpperLeftBound,double diameter);
    void drawSquare(Format format, double xUpperLeftBound, double yUpperLeftBound, double side);
}
