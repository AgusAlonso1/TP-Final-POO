package backend.model;

public class Rectangle extends FormatFigure{

    private final Point topLeft, bottomRight;

    public Rectangle(FigureDrawer fg, Format format, Point topLeft, Point bottomRight) {
        super(fg, format);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public boolean pointIsIn(Point point){
        return point.getX() > topLeft.getX() && point.getX() < bottomRight.getX() && point.getY() > topLeft.getY() && point.getY() < bottomRight.getY();
    }

    @Override
    public void moveFigure(double diffX, double diffY){
        topLeft.setX(topLeft.getX() + diffX);
        bottomRight.setX(bottomRight.getX() + diffX);
        topLeft.setY(topLeft.getY() + diffY);
        bottomRight.setY(bottomRight.getY() + diffY);
    }

    public double getHeight(){
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }

    public double getWidth(){
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }
    @Override
    public void drawFigureWithFormat(Format format) {
        getFigureDrawer().drawRectangle(format, topLeft.getX(), topLeft.getY(), getWidth(), getHeight());
    }
    @Override
    public boolean equals(Object other){
        return this == other || (other instanceof Rectangle r && r.bottomRight.equals(bottomRight) && r.topLeft.equals(topLeft) && r.getFormat().equals(this.getFormat()));
    }

}
