package network.model;

import network.layer.Layer;

import java.io.File;
import java.util.Deque;

public class NetworkModelImpl extends AbstractNetworkModel {

    public NetworkModelImpl(Deque<Layer> layers) {
        super(layers);
        Layer prev = null;
        layers.getFirst().initWeight();

        for (Layer layer: this.layers) {
            layer.setPrevious(prev);
            layer.initWeight();
            prev = layer;
        }
    }

}
