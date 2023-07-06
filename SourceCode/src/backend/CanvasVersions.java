package backend;

import backend.model.FormatFigure;

import java.util.Deque;
import java.util.LinkedList;

public class CanvasVersions {
    private Deque<CanvasSnapshot> undoVersions = new LinkedList<>();
    private Deque<CanvasSnapshot> redoVersions = new LinkedList<>();

    public CanvasSnapshot getCurrentVersion() {
        return undoVersions.peek();
    }

    public void saveVersion(CanvasAction action, String figureName, CanvasState snapshot) {
        undoVersions.push(new CanvasSnapshot(action,figureName,snapshot));
    }

    public void undo() {
        redoVersions.push(undoVersions.pop());
    }

    public void redo() {
        undoVersions.push(redoVersions.pop());
    }
}
