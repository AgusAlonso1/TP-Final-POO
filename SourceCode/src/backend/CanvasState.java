package backend;

import backend.model.FormatFigure;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

    private final List<FormatFigure> list = new ArrayList<>();

    public void addFigure(FormatFigure figure) {
        list.add(figure);
    }

    public void deleteFigure(FormatFigure figure) {
        list.remove(figure);
    }

    public Iterable<FormatFigure> figures() {
        return list;
    }


}
