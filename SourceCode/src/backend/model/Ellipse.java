package backend.model;

import backend.FigureDrawer;

public class Ellipse extends FormatFigure {

    protected final Point centerPoint;
    protected final double sMayorAxis, sMinorAxis;

    //sMayorAxis and sMinorAxis are the diameters.
    public Ellipse(FigureDrawer fg, Format format, String layer, Point centerPoint, double sMayorAxis, double sMinorAxis) {
        super(fg, format, layer);
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    public Ellipse(FigureDrawer fg, Format format, String layer, Point startPoint,Point endPoint) {
        this(fg, format,layer, new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2)),Math.abs(endPoint.getX() - startPoint.getX()), Math.abs(endPoint.getY() - startPoint.getY()));
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }
    //X coordinate
    public double getsMayorAxis() {
        return sMayorAxis;
    }
//Y coordinate
    public double getsMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public boolean pointIsIn(Point point){
        return ((Math.pow(point.getX() - centerPoint.getX(), 2) / Math.pow(sMayorAxis, 2)) + (Math.pow(point.getY() - centerPoint.getY(), 2) / Math.pow(sMinorAxis, 2))) <= 0.30;
    }

    @Override
    public void moveFigure(double diffX, double diffY){
        centerPoint.setX(centerPoint.getX() + diffX);
        centerPoint.setY(centerPoint.getY() + diffY);
    }

    public Point getTopLeftBound(){
        return new Point(centerPoint.getX() - (sMayorAxis / 2),centerPoint.getY() - (sMinorAxis / 2));
    }
    @Override
    public void drawFigureWithFormat(Format format) {
        getFigureDrawer().drawEllipse(format, getTopLeftBound().getX(), getTopLeftBound().getY(), getsMayorAxis(), getsMinorAxis());
    }
    @Override
    public boolean equals(Object other){
        return this == other || (other instanceof Ellipse e && e.centerPoint.equals(centerPoint) && e.sMayorAxis == sMayorAxis && e.sMinorAxis == sMinorAxis && e.getFormat().equals(getFormat()));
    }

    @Override
    public String getShapeName(){
        return "Elipse";
    }

    @Override
    public FormatFigure getFigureCopy() {
        return new Ellipse(getFigureDrawer(),getFormat().getFormatCopy(),getLayer(),centerPoint,sMayorAxis,sMinorAxis);
    }
}
