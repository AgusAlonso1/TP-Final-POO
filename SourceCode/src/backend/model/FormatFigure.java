package backend.model;

public abstract class FormatFigure {

    private Format format;
    private final FigureDrawer fg;
    public FormatFigure(FigureDrawer fg,Format format){
        this.fg = fg;
        this.format = format;
    }
    public abstract boolean pointIsIn(Point point);
    public abstract void moveFigure(double diffX, double diffY);

    public abstract String getShapeName();
    public void drawFigure(){
        drawFigure(format.getLineColor());
    }
    public void drawFigure(String color){
        Format aux = new Format(color, getFormat().getFillColor(), getFormat().getLineWidth());
        drawFigureWithFormat(aux);
    }
    protected abstract void drawFigureWithFormat(Format format);
    protected FigureDrawer getFigureDrawer() {
        return fg;
    }
    public Format getFormat() {
        return format;
    }
    public void setFormat(Format format) {
        this.format = format;
    }


}
