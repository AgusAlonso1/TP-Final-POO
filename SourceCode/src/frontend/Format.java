package frontend;

import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import java.util.Deque;
import java.util.LinkedList;

public class Format {
    private Color colorFill, borderColor;
    private Deque<Format> operations = new LinkedList<>();
    private BorderWidths borderWidths;
    private BorderStroke borderStroke;

    public void setColorFill(Color color){
        colorFill = color; //change color
    }

    public void setBorderColor(Color color){
        borderColor = color;
    }

    public void setBorderStroke(BorderStroke borderStroke){
        this.borderStroke = borderStroke;
    }

    public void setBorderWidths(BorderWidths borderWidths){
        this.borderWidths = borderWidths;
    }

}
