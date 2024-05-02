package network.model;

import network.layer.Layer;

import java.util.Deque;

public class NetworkModelImpl extends AbstractNetworkModel {

    public NetworkModelImpl(Deque<Layer> layers) {
        super(layers);
    }


}
