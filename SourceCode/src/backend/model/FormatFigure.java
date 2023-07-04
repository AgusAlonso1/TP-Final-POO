package backend.model;

public abstract class FormatFigure extends Figure{

    private Format format;
    private static final String RED_HEX = "#FF0000";
    private boolean selected;
    public FormatFigure(FigureDrawer fg,Format format){
        super(fg);
        this.format = format;
        this.selected = false;
    }
    @Override
    public abstract boolean pointIsIn(Point point);

    @Override
    public abstract void moveFigure(double diffX, double diffY);
    public abstract void drawFigure();
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
