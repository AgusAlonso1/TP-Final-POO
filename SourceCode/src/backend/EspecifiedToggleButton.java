package backend;

import backend.model.Point;
import backend.model.Figure;
import javafx.scene.control.ToggleButton;

public class EspecifiedToggleButton extends ToggleButton { //Class which adds distiction to the buttons in the app.
    private ButtonType type;

    public EspecifiedToggleButton(String name, ButtonType type) {
        super(name);
        this.type = type;
    }

    public Figure getFigure(Point firstPoint, Point secondPoint) {
        if (!type.isAFigureType()) {
            throw new IllegalArgumentException("Button type cannot create a figure.");
        }
        return type.buildFigure(firstPoint,secondPoint);
    }

    public boolean isAFigureButton() {
        return type.isAFigureType();
    }
}
