package network;

import data.Dataset;
import data.dataset.DataSet;
import function.LossFunc;
import optimizer.Optimizer;

public interface TrainableNetwork {

    void configure(Optimizer optimizer, LossFunc loss);

    void learn(int epoch, DataSet dataset);

    void learn(int epoch, double learnRate, DataSet dataset);

    void learn(int epoch, int batchSize, DataSet dataset);

    void learn(int epoch, double learnRate, int batchSize, DataSet dataset);

    void test(DataSet dataset);

}
