package network;

import data.Dataset;
import function.ErrorFunc;
import function.loss.LossFunc;
import network.model.NetworkModel;
import optimizer.Optimizer;

public interface TrainableNetwork<T, D> {

    void setOptimizer(Optimizer optimizer);

    void setLossFunction(LossFunc<D> loss);

    void setErrorFunction(ErrorFunc<D> errorFunc);

    void train(int epoch, Dataset<T, D> dataset, boolean validate);

    void train(int epoch, double learnRate, Dataset<T, D> dataset, boolean validate);

    void test(Dataset<T, D> dataset);

    NetworkModel getNetworkModel();

}
