package backend;

import backend.model.FormatFigure;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class CanvasState {

    //private final SortedMap<String, List<FormatFigure>> layerMap = new TreeMap<>();
    private final List<FormatFigure> list = new ArrayList<>();

    public void addFigure(FormatFigure figure) {
        list.add(figure);
    }
    //public void addFigure(FormatFigure figure, string layer){
    //  layerMap.putIfAbsent(layer, new ArrayList<>());
    //  layerMap.get(layer).add(figure);
    // }

    public void deleteFigure(FormatFigure figure) {
        list.remove(figure);
    }

    // public void deleteFigure(FormatFigure, String layer){
    //      layerMap.get(layer).delete(figure);
    // }
    //

    public Iterable<FormatFigure> figures() {
        return list;
    }

    //public Iterable<FormatFigure> figures() {
    //   List<FormatFigure> toReturn = new ArrayList<>();
    //   for(MapEntry<String,List<FormatFigure>> entry : layersMap.entrySet()){
    //      toReturn.addAll(entry.getValue());
    //   }
    //

    //Returns the list of format figures in a layer
    //public Iterable<FormatFigure> getLayerFigures(int layer) {
    //    return layerMap.get(layer);
    //}

}
