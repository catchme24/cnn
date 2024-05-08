package network.model;

import network.layer.Layer;
import network.layer.LearningLayer;

import java.io.File;
import java.util.Deque;

public class NetworkModelImpl extends AbstractNetworkModel {

    public NetworkModelImpl(Deque<Layer> layers) {
        super(layers);
        Layer prev = null;

        if (layers.getFirst() instanceof LearningLayer) {
            LearningLayer learningLayer = (LearningLayer) layers.getFirst();
            learningLayer.initWeightsAndBaises();
        }

        for (Layer layer: this.layers) {
            layer.setPrevious(prev);
            if (layer instanceof LearningLayer) {
                LearningLayer learningLayer = (LearningLayer) layer;
                learningLayer.initWeightsAndBaises();
            }
            prev = layer;
        }
    }

}
