package backend.model;

public abstract class FormatFigure implements Figure{

    private Format format;
    private boolean selected;
    public FormatFigure(Format format){
        this.format = format;
        this.selected = false;
    }
    @Override
    public abstract boolean pointIsIn(Point point);

    @Override
    public abstract void moveFigure(double diffX, double diffY);

}
