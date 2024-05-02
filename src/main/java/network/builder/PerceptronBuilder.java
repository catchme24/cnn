package network.builder;

import network.Perceptron;
import network.TrainableNetwork;
import network.layer.Layer;

import java.util.*;

public class PerceptronBuilder {
    private final Deque<Layer> layers;

    private PerceptronBuilder() {
        this.layers = new LinkedList<>();
    }

    public static PerceptronBuilder builder() {
        return new PerceptronBuilder();
    }

    public PerceptronBuilder append(Layer layer) {
        layers.add(layer);
        return this;
    }

    public TrainableNetwork build() {
        return new Perceptron(layers);
    }
}
