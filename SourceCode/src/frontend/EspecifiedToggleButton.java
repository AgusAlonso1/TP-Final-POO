package frontend;

import backend.ButtonType;
import backend.model.FigureDrawer;
import backend.model.Format;
import backend.model.FormatFigure;
import backend.model.Point;
import javafx.scene.control.ToggleButton;

public class EspecifiedToggleButton extends ToggleButton { //Class which adds distinction to the buttons in the app.
    private final ButtonType type;

    public EspecifiedToggleButton(String name, ButtonType type) {
        super(name);
        this.type = type;
    }

    public FormatFigure getFigure(FigureDrawer fg, Format format, Point firstPoint, Point secondPoint) {
        if (!type.isAFigureType()) {
            throw new IllegalArgumentException("Button type cannot create a figure.");
        }
        return type.buildFigure(fg, format, firstPoint,secondPoint);
    }

    public boolean isAFigureButton() {
        return type.isAFigureType();
    }
}
