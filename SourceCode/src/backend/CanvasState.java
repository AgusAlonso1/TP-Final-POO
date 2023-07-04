package backend;

import backend.model.FormatFigure;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class CanvasState {

    //private final SortedMap<Integer, List<FormatFigure>> layerMap = new TreeMap<>();
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
    //Returns the list of format figures in a layer
    //public Iterable<FormatFigure> getLayerFigures(int layer) {
    //    return layerMap.get(layer);
    //}

}
