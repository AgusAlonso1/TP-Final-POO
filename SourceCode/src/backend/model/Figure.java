package backend.model;

public interface Figure {

    boolean pointIsIn(Point point);

    void moveFigure(double diffX, double diffY);

}
