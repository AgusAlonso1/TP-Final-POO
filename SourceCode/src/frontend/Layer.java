package frontend;

import backend.model.FormatFigure;

import java.util.ArrayList;

public class Layer extends ArrayList<FormatFigure> {
    private final Integer layerNum;

    public Layer(Integer layerNum) {
        this.layerNum = layerNum;
    }
    public Integer getLayerNum() {
        return layerNum;
    }
    @Override
    public String toString() {
        return "Layer %d".formatted(layerNum);
    }
}
