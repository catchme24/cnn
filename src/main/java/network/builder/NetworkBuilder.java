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

    public <T, D> TrainableNetwork<T, D> build(File logsFile, File saveFile, boolean needSave) {
        return new TrainableNetworkImpl<T, D>(new NetworkModelImpl(layers), logsFile, saveFile);
    }

    public <T, D> TrainableNetwork<T, D> build() {
        return new TrainableNetworkImpl<T, D>(new NetworkModelImpl(layers), null, null);
    }

    public <T, D> TrainableNetwork<T, D> build(File logsFile, File saveFile, File loadFile) {
        return new TrainableNetworkImpl<T, D>(new FileNetworkModel(layers, loadFile), logsFile, saveFile);
    }

    public <T, D> TrainableNetwork<T, D> build(File logsFile, File loadFile) {
        return new TrainableNetworkImpl<T, D>(new FileNetworkModel(layers, loadFile), logsFile, null);
    }
}
