package backend;

import backend.model.FormatFigure;

import java.util.Iterator;

public class CanvasSnapshot {
    private CanvasAction lastActionType;
    private String lastActionFigureName;
    private Iterable<FormatFigure> canvasSnapshot;

    public CanvasSnapshot(CanvasAction lastActionType, String lastActionFigureName, Iterable<FormatFigure> canvasSnapshot) {
        this.lastActionType = lastActionType;
        this.lastActionFigureName = lastActionFigureName;
        this.canvasSnapshot = canvasSnapshot;
    }

    public Iterable<FormatFigure> getCanvasSnapshot(){
        return canvasSnapshot;
    }

    @Override
    public String toString() {
        return "%s %s".formatted(lastActionType,lastActionFigureName);
    }
}
