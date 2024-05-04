package network;

import data.DataFrame;
import data.Dataset;
import function.DefaultLossFunction;
import function.LossFunc;
import network.model.NetworkModel;
import optimizer.Optimizer;
import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

import java.util.List;

public abstract class AbstractTrainableNetwork implements TrainableNetwork {

    protected NetworkModel networkModel;

    protected Optimizer optimizer;

    protected LossFunc lossFunc;

    public AbstractTrainableNetwork(NetworkModel networkModel) {
        this.networkModel = networkModel;
        this.optimizer = null;
        this.lossFunc = new DefaultLossFunction();
    }

    @Override
    public void configure(Optimizer optimizer, LossFunc loss) {
        this.optimizer = optimizer;
        this.lossFunc = loss;
    }

    @Override
    public void learn(int epoch, Dataset dataset) {
        learn(epoch, 0.01, dataset);
    }

    @Override
    public void learn(int epoch, double learnRate, Dataset dataset) {
//        List<DataFrame> learn = dataset.getTrainData();
//        int selectionCount = learn.size();
//
//        for (int epochIndex = 0; epochIndex < epoch; epochIndex++) {
//            for (int selectionIndex = 0; selectionIndex < selectionCount; selectionIndex++) {
//                RealMatrix networkInput = MatrixUtils.createInstanceFromRow(learn.get(selectionIndex).getAttributes()).transpose();
//                RealMatrix networkOutput = (RealMatrix) networkModel.propogateForward(networkInput);
//                RealMatrix groundTruth  = MatrixUtils.getGroundTruth(learn.get(selectionIndex).getClassNumber(), dataset.getLabelsCount());
//                RealMatrix errorVector = networkOutput.subtract(groundTruth);
//                lossFunc.calculate(errorVector);
//                networkModel.propogateBackward(errorVector);
//                networkModel.correctWeights(learnRate);
//            }
//            System.out.println("RMSE " + epochIndex + "-ой эпохи равна " + lossFunc.getOveralLoss());
//        }
    }

    @Override
    public void test(Dataset dataset) {

    }
}
