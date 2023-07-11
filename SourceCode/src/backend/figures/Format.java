package backend.figures;

public class Format {
    private String lineColor;
    private String fillColor;
    private double lineWidth;

    public Format(String lineColor, String fillColor, double lineWidth){
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.lineWidth = lineWidth;
    }

    public String getLineColor(){
        return lineColor;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }


    @Override
    public String toString() {
        return "%s, %s, %f".formatted(lineColor,fillColor,lineWidth);
    }

    public Format getFormatCopy() {
        return new Format(lineColor,fillColor,lineWidth);
    }
}
