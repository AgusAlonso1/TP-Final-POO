package backend.figures;

import backend.FigureDrawer;

public class Square extends Rectangle {

    public Square(FigureDrawer fg, Format format, String layer, Point topLeft, double size) {
        super(fg, format,layer,topLeft,new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    public Square(FigureDrawer fg, Format format, String layer, Point startPoint, Point endPoint){
        this(fg,format, layer,startPoint, Math.abs(endPoint.getX() - startPoint.getX()));
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }
    @Override
    public void drawFigureWithFormat(Format format) {
        getFigureDrawer().drawSquare(format, getTopLeft().getX(), getTopLeft().getY(), getHeight());
    }
    @Override
    public String getShapeName(){
        return "Cuadrado";
    }

}
