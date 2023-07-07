package backend.Actions;

import backend.CanvasState;
import backend.NothingToDoException;
import backend.model.FormatFigure;

import java.util.Deque;
import java.util.LinkedList;

public class Actions {
    private Deque<LastAction> undoVersions = new LinkedList<>();
    private Deque<LastAction> redoVersions = new LinkedList<>();

    private CanvasState canvasState;

    public Actions(CanvasState canvasState){
        this.canvasState = canvasState;
    }

    public void saveVersion(CanvasAction action, FormatFigure figure, String layer) {
        undoVersions.push(new LastAction(action,figure,layer));
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

    //Undo action deletes the last action done by the canvas.
    public void undo() {
        if(!canUndo()){
            throw new NothingToDoException("Nothing to Undo.");
        }else if(!undoVersions.peek().isModifierActionType()){ //Action type is no a format change
            canvasState.deleteFigure(undoVersions.peek().getLastActionFigure(),undoVersions.peek().getLastActionLayer());
        }
        redoVersions.push(undoVersions.pop());
    }

    //Redo action readds the last action done by the canvas.
    public void redo() {
        if(!canRedo()){
            throw new NothingToDoException("Nothing to Redo.");
        }else if(!redoVersions.peek().isModifierActionType()) { //Action type is no a format change
            canvasState.addFigure(redoVersions.peek().getLastActionFigure(), redoVersions.peek().getLastActionLayer());
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
