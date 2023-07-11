package backend.figures;

import backend.FigureDrawer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FormatFigure {

    private Format format;
    private final FigureDrawer fg;
    private String layer;
    private Set<String> tags = new HashSet<>();

    public FormatFigure(FigureDrawer fg,Format format, String layer){
        this.fg = fg;
        this.format = format;
        this.layer = layer;
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
    public String getLayer() {
        return layer;
    }
    public void changeLayer(String newLayer) {
        this.layer = newLayer;
    }
    public Format getFormat() {
        return format;
    }
    public void setFormat(Format format) {
        this.format = format;
    }
    public void addTags(String[] tags) {
        this.tags.clear();
        List<String> stringList = Arrays.asList(tags);
        this.tags.addAll(stringList);
    }
    public String getTags() {
        StringBuilder builder = new StringBuilder();
        for(String tag : tags) {
            builder.append(tag + "\n");
        }
        return builder.toString();
    }
    public abstract FormatFigure getFigureCopy();
}
