package backend.Actions;

import backend.Actions.CanvasAction;
import backend.model.FormatFigure;

public class LastAction {
    private final CanvasAction lastActionType;
    private final FormatFigure lastActionFigure;
    private final String lastActionLayer;

    public LastAction(CanvasAction lastActionType, FormatFigure lastActionFigure, String lastActionLayer) {
        this.lastActionType = lastActionType;
        this.lastActionFigure = lastActionFigure;
        this.lastActionLayer = lastActionLayer;
    }

    public FormatFigure getLastActionFigure(){
        return lastActionFigure;
    }

    public String getLastActionLayer(){
        return lastActionLayer;
    }

    public boolean isModifierActionType(){
        return lastActionType.isAFormatModifier();
    }

    @Override
    public String toString() {
        return "%s %s".formatted(lastActionType,lastActionFigure.getShapeName());
    }
}
