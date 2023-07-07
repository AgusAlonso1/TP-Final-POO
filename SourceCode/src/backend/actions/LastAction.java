package backend.actions;

import backend.model.FormatFigure;

public class LastAction {
    private ActionType actionType;
    private FormatFigure newFigure;
    private FormatFigure oldFigure;
    private String newLayer;
    private String oldLayer;
    private ApplyAction undoAction;
    private ApplyAction redoAction;

    public LastAction(ActionType actionType, FormatFigure oldFigure, FormatFigure newFigure, String oldLayer, String newLayer, ApplyAction undoAction, ApplyAction redoAction) {
        this.actionType = actionType;
        this.newFigure = newFigure;
        this.oldFigure = oldFigure;
        this.newLayer = newLayer;
        this.oldLayer = oldLayer;
        this.undoAction = undoAction;
        this.redoAction = redoAction;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public FormatFigure getNewFigure() {
        return newFigure;
    }

    public FormatFigure getOldFigure() {
        return oldFigure;
    }

    public String getNewLayer() {
        return newLayer;
    }

    public String getOldLayer() {
        return oldLayer;
    }

    public ApplyAction getUndoAction() {
        return undoAction;
    }

    public ApplyAction getRedoAction() {
        return redoAction;
    }

    @Override
    public String toString() {
        return "%s %s".formatted(actionType,newFigure.getShapeName());
    }
}
