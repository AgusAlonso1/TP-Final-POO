package backend;

import backend.model.FormatFigure;

import java.util.*;

public class CanvasState {
    private final SortedMap<String, List<FormatFigure>> layersMap = new TreeMap<>();

    public void addFigure(FormatFigure figure, String layer){
      layersMap.putIfAbsent(layer, new ArrayList<>());
      layersMap.get(layer).add(figure);
     }

     public void deleteFigure(FormatFigure figure, String layer){
          layersMap.get(layer).remove(figure);
     }

     //Returns the list of FormatFigures acording to the selected layers in the checkboxes.
     public Iterable<FormatFigure> figures(List<String> selectedLayers) {
         List<FormatFigure> toReturn = new ArrayList<>();
         for (Map.Entry<String, List<FormatFigure>> entry : layersMap.entrySet()) {
             if (selectedLayers.contains(entry.getKey())) {
                 toReturn.addAll(entry.getValue());
             }
         }
         return toReturn;
     }

    //Returns the list of FormatFigures in a specific layer.
    public Iterable<FormatFigure> figures(String layer) {
        return layersMap.getOrDefault(layer, new ArrayList<>());
    }

}
