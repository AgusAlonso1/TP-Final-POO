package backend.actions;

import backend.CanvasState;
import backend.model.FormatFigure;

@FunctionalInterface
public interface ApplyAction {
        void applyAction(CanvasState canvasState, FormatFigure oldFigure, FormatFigure newFigure, String oldLayer, String newLayer);
}
