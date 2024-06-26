package network.builder;

import network.TrainableNetwork;
import network.TrainableNetworkImpl;
import network.layer.Layer;
import network.model.FileNetworkModel;
import network.model.NetworkModelImpl;

import java.io.File;
import java.util.*;

public class NetworkBuilder {
    private final LinkedList<Layer> layers;

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

    public NetworkBuilder remove(int layerIndex) {
        layers.remove(layerIndex);
        return this;

    }

    public NetworkBuilder add(int layerIndex, Layer layer) {
        layers.add(layerIndex, layer);
        return this;

    }

    public <T, D> TrainableNetwork<T, D> build(File logsFile, File saveFile, boolean needSave) {
        return new TrainableNetworkImpl<T, D>(new NetworkModelImpl(layers), logsFile, saveFile);
    }

    public <T, D> TrainableNetwork<T, D> build() {
        return new TrainableNetworkImpl<T, D>(new NetworkModelImpl(layers), null, null);
    }

    public <T, D> TrainableNetwork<T, D> build(File loadFile, boolean needLoad) {
        return new TrainableNetworkImpl<T, D>(new FileNetworkModel(layers, loadFile), null, null);
    }

    public <T, D> TrainableNetwork<T, D> build(File logsFile) {
        return new TrainableNetworkImpl<T, D>(new NetworkModelImpl(layers), logsFile, null);
    }

    public <T, D> TrainableNetwork<T, D> build(File logsFile, File saveFile, File loadFile) {
        return new TrainableNetworkImpl<T, D>(new FileNetworkModel(layers, loadFile), logsFile, saveFile);
    }

    public <T, D> TrainableNetwork<T, D> build(File logsFile, File loadFile) {
        return new TrainableNetworkImpl<T, D>(new FileNetworkModel(layers, loadFile), logsFile, null);
    }

}
