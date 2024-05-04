package network.builder;

import network.TrainableNetwork;
import network.TrainableNetworkImpl;
import network.layer.Layer;
import network.model.FileNetworkModel;
import network.model.NetworkModelImpl;

import java.io.File;
import java.util.Deque;
import java.util.LinkedList;

public class NetworkBuilder {
    private final Deque<Layer> layers;

    private NetworkBuilder() {
        this.layers = new LinkedList<>();
    }

    public static NetworkBuilder builder() {
        return new NetworkBuilder();
    }

    public NetworkBuilder append(Layer layer) {
        layers.add(layer);
        return this;
    }

    public TrainableNetwork build() {
        return new TrainableNetworkImpl(new NetworkModelImpl(layers));
    }

    public TrainableNetwork build(File file) {
        return new TrainableNetworkImpl(new FileNetworkModel(layers, file));
    }
}
