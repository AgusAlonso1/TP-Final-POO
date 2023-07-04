package backend.model;

public abstract class FormatFigure {

    private Format format;
    private final FigureDrawer fg;
    private static final String RED_HEX = "#FF0000";
    private boolean selected;
    public FormatFigure(FigureDrawer fg,Format format){
        this.fg = fg;
        this.format = format;
        this.selected = false;
    }
    public abstract boolean pointIsIn(Point point);
    public abstract void moveFigure(double diffX, double diffY);
    public abstract void drawFigure();
    protected FigureDrawer getFigureDrawer() {
        return fg;
    }
    protected Format getFormat() {
        if (selected) {
            //deselect();
            return new Format(RED_HEX, format.getFillColor(), format.getLineWidth());
        }
        return format;
    }

    public void select() {
        this.selected = true;
    }

    public void deselect() {
        this.selected = false;
    }

}
