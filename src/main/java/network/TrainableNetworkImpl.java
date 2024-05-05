package network;

import data.DataFrame;
import data.Dataset;
import function.DefaultAccuracyFunction;
import function.DefaultLossFunction;
import network.model.NetworkModel;
import org.apache.commons.math3.linear.RealMatrix;
import util.model.Matrix3D;

import java.util.List;

public class TrainableNetworkImpl extends AbstractTrainableNetwork {


    public TrainableNetworkImpl(NetworkModel networkModel) {
        super(networkModel);
    }


    @Override
    public void learn(int epoch, int batchSize, Dataset dataset) {
        DefaultLossFunction loss = new DefaultLossFunction();
        DefaultAccuracyFunction accuracy = new DefaultAccuracyFunction();
        Dataset<Matrix3D, RealMatrix> dataset1 = (Dataset<Matrix3D, RealMatrix>) dataset;
        List<DataFrame<Matrix3D, RealMatrix>> trainData = dataset1.getTrainData();
        List<DataFrame<Matrix3D, RealMatrix>> validData = dataset1.getValidData();
        for (int i = 0; i < epoch; i++){
//            MatrixUtils.printMatrix3D(trainData.get(0).getSample());
            dataset1.shuffleDataset();
            for (int j = 0; j < trainData.size(); j++){
                RealMatrix forward = (RealMatrix) networkModel.propogateForward(trainData.get(j).getSample());
                RealMatrix error = forward.subtract(trainData.get(j).getGroundTruth());
                loss.calculate(trainData.get(j).getGroundTruth(), forward);
                accuracy.calculate(trainData.get(j).getLabelNumber(), forward);
                Matrix3D backward = (Matrix3D) networkModel.propogateBackward(error);
                networkModel.correctWeights(0.001);
                System.out.println("Эпоха " + (i+1) + ": " + (j+1)+" обучающий пример из " + trainData.size());
            }

            System.out.println();

            // ПУСТЬ ПОТОМ ПИШЕТ В ФАЙЛ
            System.out.println("train loss on epoch " + (i+1) + ": " +loss.getOveralLoss());
            System.out.println("train accuracy on epoch " + (i+1) + ": " +accuracy.getOveralAccuracy());

            for (int j = 0; j < validData.size(); j++){
                RealMatrix forward = (RealMatrix) networkModel.propogateForward(validData.get(j).getSample());
                loss.calculate(validData.get(j).getGroundTruth(), forward);
                accuracy.calculate(validData.get(j).getLabelNumber(), forward);

            }

            System.out.println();

            // ПУСТЬ ПОТОМ ПИШЕТ В ФАЙЛ
            System.out.println("valid loss on epoch " + (i+1) + ": " +loss.getOveralLoss());
            System.out.println("valid accuracy on epoch " + (i+1) + ": " +accuracy.getOveralAccuracy());

            System.out.println();

//            ТУТА СОХРАНИТЬ СЕТКУ
//            networkModel.save();
        }
    }

    @Override
    public void learn(int epoch, double learnRate, int batchSize, Dataset dataset) {

    }

    @Override
    public NetworkModel getNetworkModel() {
        return networkModel;
    }
}
