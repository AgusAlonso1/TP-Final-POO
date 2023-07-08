package backend;

import backend.model.Format;
import backend.model.FormatFigure;
import java.util.*;

public class CanvasState {
    private final SortedMap<String, List<FormatFigure>> layersMap = new TreeMap<>();

    public void addFigure(FormatFigure figure){
      String currentLayer = figure.getLayer();
      layersMap.putIfAbsent(currentLayer, new ArrayList<>());
      layersMap.get(figure.getLayer()).add(figure);
     }

     public void deleteFigure(FormatFigure figure){
         layersMap.get(figure.getLayer()).remove(figure);
     }

     // Move figure form layers and add to a new one.
     public void moveFigure(FormatFigure figure, String newLayer){
        deleteFigure(figure);
        figure.changeLayer(newLayer);
        addFigure(figure);
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

    public void changeFormat(FormatFigure toChange, Format newFormat) {
        FormatFigure figureCopy = toChange.getFigureCopy(); //Copy to avoid losing the reference to the current format.
        deleteFigure(toChange);
        figureCopy.setFormat(newFormat);
        addFigure(figureCopy);
    }
}
