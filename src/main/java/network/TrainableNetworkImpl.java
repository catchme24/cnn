package network;

import data.Dataset;
import network.model.NetworkModel;

public class TrainableNetworkImpl extends AbstractTrainableNetwork {


    public TrainableNetworkImpl(NetworkModel networkModel) {
        super(networkModel);
    }


    @Override
    public void learn(int epoch, int batchSize, Dataset dataset) {
        // TO-DO
    }

    @Override
    public void learn(int epoch, double learnRate, int batchSize, Dataset dataset) {

    }
}
