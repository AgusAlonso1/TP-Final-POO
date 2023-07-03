package backend.model;

public class Ellipse extends FormatFigure {

    protected final Point centerPoint;
    protected final double sMayorAxis, sMinorAxis;

    //sMayorAxis and sMinorAxis are the diameters.
    public Ellipse(FigureDrawer fg, Format format, Point centerPoint, double sMayorAxis, double sMinorAxis) {
        super(fg, format);
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    public Ellipse(FigureDrawer fg, Format format, Point startPoint,Point endPoint) {
        this(fg, format, new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2)),Math.abs(endPoint.getX() - startPoint.getX()), Math.abs(endPoint.getY() - startPoint.getY()));
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

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
    public void drawFigure() {
        getFigureDrawer().drawEllipse(, );
    }

}
