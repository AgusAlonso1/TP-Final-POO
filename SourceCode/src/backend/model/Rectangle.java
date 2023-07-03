package backend.model;

public class Rectangle implements Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
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

}