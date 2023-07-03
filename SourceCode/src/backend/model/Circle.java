package backend.model;

public class Circle extends Ellipse {
    public Circle(Point centerPoint, double radius) {
        super(centerPoint, radius*2, radius*2);
    }

    public Circle(Point startPoint, Point endPoint){
        this(startPoint,Math.abs(endPoint.getX() - startPoint.getX()));
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }

    public double getRadius() {
        return getsMayorAxis()/2;
    }

    public double getDiameter(){
        return getRadius() * 2;
    }

}
