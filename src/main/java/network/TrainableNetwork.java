package network;

import data.Dataset;
import function.loss.LossFunc;
import network.model.NetworkModel;
import optimizer.Optimizer;

public interface TrainableNetwork<T, D> {

    void configure(Optimizer optimizer, LossFunc<D> loss);

    void train(int epoch, Dataset<T, D> dataset, boolean validate);

    void train(int epoch, double learnRate, Dataset<T, D> dataset, boolean validate);

    void test(Dataset<T, D> dataset);

    NetworkModel getNetworkModel();

}
