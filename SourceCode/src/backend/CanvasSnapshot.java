package backend;

import backend.model.FormatFigure;

public class CanvasSnapshot {
    private CanvasAction lastActionType;
    private String lastActionFigureName;
    private CanvasState canvasSnapshot;

    public CanvasSnapshot(CanvasAction lastActionType, String lastActionFigureName, CanvasState canvasSnapshot) {
        this.lastActionType = lastActionType;
        this.lastActionFigureName = lastActionFigureName;
        this.canvasSnapshot = canvasSnapshot;
    }

    public CanvasState getCanvasSnapshot(){
        return canvasSnapshot;
    }

    @Override
    public String toString() {
        return "%s %s".formatted(lastActionType,lastActionFigureName);
    }
}
