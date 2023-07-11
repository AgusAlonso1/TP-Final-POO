package backend.actions;

import backend.CanvasState;
import backend.figures.FormatFigure;

@FunctionalInterface
public interface ApplyAction {
        void applyAction(CanvasState canvasState, FormatFigure oldFigure, FormatFigure newFigure);
}
