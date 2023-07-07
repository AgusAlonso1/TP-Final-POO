package backend.actions;

import backend.CanvasState;
import backend.customExceptions.NothingToDoException;

import java.util.Deque;
import java.util.LinkedList;

public class ActionManager {
    private Deque<LastAction> undoVersions = new LinkedList<>();
    private Deque<LastAction> redoVersions = new LinkedList<>();

    private CanvasState canvasState;

    public ActionManager(CanvasState canvasState){
        this.canvasState = canvasState;
    }

    public void saveVersion(LastAction lastAction) {
        undoVersions.push(lastAction);
    }

    public String lastActionUndo(){
        if(!canUndo()){
            return "Nothing to undo. %d.".formatted(undoVersions.size());
        }
        return "%s. %d.".formatted(undoVersions.peek().toString(),undoVersions.size());
    }

    public String lastActionRedo(){
        if(!canRedo()){
            return "%d. Nothing to undo".formatted(redoVersions.size());
        }
        return "%d. %s.".formatted(redoVersions.size(), redoVersions.peek().toString());
    }

    //Undo action deletes the last action done by the canvas.
    public void undo() {
        if(!canUndo()){
            throw new NothingToDoException("Nothing to Undo.");
        }
        LastAction lastAction = undoVersions.peek();
        lastAction.getUndoAction().applyAction(canvasState, lastAction.getOldFigure(), lastAction.getNewFigure(), lastAction.getOldLayer(),lastAction.getNewLayer());
        redoVersions.push(undoVersions.pop());
    }

    //Redo action readds the last action done by the canvas.
    public void redo() {
        if(!canRedo()){
            throw new NothingToDoException("Nothing to Redo.");
        }
        LastAction lastAction = redoVersions.peek();
        lastAction.getRedoAction().applyAction(canvasState, lastAction.getOldFigure(), lastAction.getNewFigure(), lastAction.getOldLayer(),lastAction.getNewLayer());
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
