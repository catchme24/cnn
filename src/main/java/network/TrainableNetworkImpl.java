package network;

import network.model.NetworkModel;

import java.io.File;

public class TrainableNetworkImpl<T, D> extends AbstractTrainableNetwork<T, D> {

    public TrainableNetworkImpl(NetworkModel networkModel, File logsFile, File saveFile) {
        super(networkModel);
        this.logsFile = logsFile;
        this.saveFile = saveFile;
    }

}
