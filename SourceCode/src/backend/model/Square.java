package backend.model;

public class Square extends Rectangle {

    public Square(FigureDrawer fg, Format format, Point topLeft, double size) {
        super(fg, format, topLeft,new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    public Square(FigureDrawer fg, Format format, Point startPoint, Point endPoint){
        this(fg,format, startPoint, Math.abs(endPoint.getX() - startPoint.getX()));
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }
    @Override
    public void drawFigureWithFormat(Format format) {
        getFigureDrawer().drawSquare(format, getTopLeft().getX(), getTopLeft().getY(), getHeight());
    }

}
