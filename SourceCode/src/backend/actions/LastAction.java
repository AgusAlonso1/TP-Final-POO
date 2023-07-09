package backend.actions;

import backend.model.FormatFigure;

public class LastAction {
    private final ActionType actionType;
    private final FormatFigure newFigure;
    private final FormatFigure oldFigure;
    private final ApplyAction undoAction;
    private final ApplyAction redoAction;

    public LastAction(ActionType actionType, FormatFigure oldFigure, FormatFigure newFigure, ApplyAction undoAction, ApplyAction redoAction) {
        this.actionType = actionType;
        this.newFigure = newFigure;
        this.oldFigure = oldFigure;
        this.undoAction = undoAction;
        this.redoAction = redoAction;
    }

    public FormatFigure getNewFigure() {
        return newFigure;
    }

    public FormatFigure getOldFigure() {
        return oldFigure;
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
