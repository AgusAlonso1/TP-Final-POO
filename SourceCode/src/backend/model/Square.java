package backend.model;

public class Square extends Rectangle {

    public Square(Format format, Point topLeft, double size) {
        super(format, topLeft,new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    public Square(Format format, Point startPoint, Point endPoint){
        this(format, startPoint, Math.abs(endPoint.getX() - startPoint.getX()));
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

}
