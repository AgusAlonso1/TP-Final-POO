package backend;

import backend.model.FormatFigure;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class CanvasVersions {
    private Deque<CanvasSnapshot> undoVersions = new LinkedList<>();
    private Deque<CanvasSnapshot> redoVersions = new LinkedList<>();

    public Iterable<FormatFigure> getCurrentVersion() {
        if(undoVersions.isEmpty()){
            throw new NothingToDoException("There is no current version");
        }
        return undoVersions.peek().getCanvasSnapshot();
    }

    public void saveVersion(CanvasAction action, String figureName, Iterable<FormatFigure> snapshot) {
        undoVersions.push(new CanvasSnapshot(action,figureName,snapshot));
    }

    public void undo() {
        redoVersions.push(undoVersions.pop());
    }

    public void redo() {
        undoVersions.push(redoVersions.pop());
    }

    public void clearRedo(){
        redoVersions.clear();
    }
}
