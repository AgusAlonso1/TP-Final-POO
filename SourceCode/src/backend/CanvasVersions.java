package backend;

import backend.model.FormatFigure;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class CanvasVersions {
    private Deque<CanvasSnapshot> undoVersions = new LinkedList<>();
    private Deque<CanvasSnapshot> redoVersions = new LinkedList<>();

    public Iterable<FormatFigure> getCurrentVersion() {
        if(!canUndo()){//canvas is clear
            return new ArrayList<>();
        }
        return undoVersions.peek().getCanvasSnapshot();
    }

    public void saveVersion(CanvasAction action, String figureName, Iterable<FormatFigure> snapshot) {
        undoVersions.push(new CanvasSnapshot(action,figureName,snapshot));
    }

    public String lastActionUndo(){
        if(undoVersions.isEmpty()){
            return "Nothing to undo. %d.".formatted(undoVersions.size());
        }
        return "%s. %d.".formatted(undoVersions.peek().toString(),undoVersions.size());
    }

    public String lastActionRedo(){
        if(redoVersions.isEmpty()){
            return "%d. Nothing to undo".formatted(redoVersions.size());
        }
        return "%d. %s.".formatted(redoVersions.size(), redoVersions.peek().toString());
    }

    public void undo() {
        if(!canUndo()){
            throw new NothingToDoException("Nothing to Undo.");
        }
        redoVersions.push(undoVersions.pop());
    }

    public void redo() {
        if(!canRedo()){
            throw new NothingToDoException("Nothing to Redo.");
        }
        undoVersions.push(redoVersions.pop());

    }

    public void clearRedo(){
        redoVersions.clear();
    }

    public boolean canUndo(){
        return !undoVersions.isEmpty();
    }

    public boolean canRedo(){
        return !redoVersions.isEmpty();
    }
}
