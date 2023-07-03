package backend.model;

public class Square extends Rectangle {

    public Square(Point topLeft, double size) {
        super(topLeft,new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    public Square(Point startPoint, Point endPoint){
        this(startPoint, Math.abs(endPoint.getX() - startPoint.getX()));
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

}
