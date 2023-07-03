package backend.model;

public interface FigureDrawer {
    void drawRectagle(double xUpperLeftBound, double yUpperLeftBound, double width, double height);
    void drawEllipse(double xUpperLeftBound, double yUpperLeftBound, double width, double height);
    void drawCircle(double xUpperLeftBound, double yUpperLeftBound,double diameter);
    void drawSquare(double xUpperLeftBound, double yUpperLeftBound, double height);
}
