package backend.model;

public abstract class Figure {
    private final FigureDrawer fg;
    public Figure(FigureDrawer fg) {
        this.fg = fg;
    }
    protected FigureDrawer getFigureDrawer() {
        return fg;
    }


    public abstract boolean pointIsIn(Point point);

    public abstract void moveFigure(double diffX, double diffY);

}
