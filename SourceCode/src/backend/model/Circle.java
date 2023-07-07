package backend.model;

import backend.FigureDrawer;

public class Circle extends Ellipse {
    public Circle(FigureDrawer fg, Format format, Point centerPoint, double radius) {
        super(fg, format, centerPoint, radius*2, radius*2);
    }

    public Circle(FigureDrawer fg,Format format, Point startPoint, Point endPoint){
        this(fg, format, startPoint,Math.abs(endPoint.getX() - startPoint.getX()));
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
    @Override
    public void drawFigureWithFormat(Format format) {
        getFigureDrawer().drawCircle(format, getTopLeftBound().getX(), getTopLeftBound().getY(), getDiameter());
    }

    @Override
    public String getShapeName(){
        return "Circulo";
    }

}
