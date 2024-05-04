package network;

import data.Dataset;
import function.LossFunc;
import optimizer.Optimizer;

public interface TrainableNetwork {

    void configure(Optimizer optimizer, LossFunc loss);

    void learn(int epoch, Dataset dataset);

    void learn(int epoch, double learnRate, Dataset dataset);

    void learn(int epoch, int batchSize, Dataset dataset);

    void learn(int epoch, double learnRate, int batchSize, Dataset dataset);

    void test(Dataset dataset);

}
