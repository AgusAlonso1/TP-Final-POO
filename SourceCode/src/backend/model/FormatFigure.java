package backend.model;

public abstract class FormatFigure extends Figure{

    private Format format;
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
        return format;
    }

}
