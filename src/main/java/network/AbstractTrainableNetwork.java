package network;

import data.DataFrame;
import data.Dataset;
import function.DefaultErrorFunction;
import function.ErrorFunc;
import function.accuracy.AccuracyFunc;
import function.accuracy.DefaultAccuracyFunction;
import function.loss.DefaultLossFunction;
import function.loss.LossFunc;
import network.model.NetworkModel;
import optimizer.Optimizer;
import optimizer.SGD;
import org.apache.commons.math3.linear.RealMatrix;
import util.model.Matrix3D;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class AbstractTrainableNetwork<T, D> implements TrainableNetwork<T, D> {

    protected NetworkModel<T, D> networkModel;

    protected Optimizer optimizer;

    protected LossFunc<D> lossFunc;

    protected AccuracyFunc<D> accuracyFunc;

    protected ErrorFunc<D> errorFunction;

    protected File logsFile;
    protected File saveFile;

    public AbstractTrainableNetwork(NetworkModel<T, D> networkModel) {
        this.networkModel = networkModel;
        this.optimizer = new SGD(0.001);
        this.lossFunc = (LossFunc<D>) new DefaultLossFunction();
        this.accuracyFunc = (AccuracyFunc<D>) new DefaultAccuracyFunction();
        this.errorFunction = (ErrorFunc<D>) new DefaultErrorFunction();
    }

    public AbstractTrainableNetwork(NetworkModel<T, D> networkModel, File logs, File save) {
        this(networkModel);
        this.logsFile = logs;
        this.saveFile = save;
    }

    public AbstractTrainableNetwork(NetworkModel<T, D> networkModel, File logs) {
        this(networkModel);
        this.logsFile = logs;
    }

    @Override
    public void configure(Optimizer optimizer, LossFunc<D> loss) {
        this.optimizer = optimizer;
        this.lossFunc = loss;
    }

    @Override
    public void train(int epoch, Dataset<T, D> dataset, boolean validate) {
        train(epoch, 0.001, dataset, validate);
    }

    @Override
    public void train(int epoch, double learnRate, Dataset<T, D> dataset, boolean validate) {
        List<DataFrame<T, D>> trainData = dataset.getTrainData();
        List<DataFrame<T, D>> validData = dataset.getValidData();

        for (int i = 0; i < epoch; i++) {
            dataset.shuffleDataset();
            train(trainData, i);
            if (validate) {
                valid(validData, i);
            }
        }
        //ТУТА СОХРАНИТЬ СЕТКУ
        if (saveFile != null) {
            networkModel.saveInFile(saveFile);
        }
    }

    protected void train(List<DataFrame<T, D>> trainData, int epochNumber) {
        for (int i = 0; i < trainData.size(); i++){
            D forward = networkModel.propogateForward(trainData.get(i).getSample());
//            D error = (D) forward.subtract(trainData.get(i).getGroundTruth());
            D error = errorFunction.calculate(trainData.get(i).getGroundTruth(), forward);

            lossFunc.calculate(trainData.get(i).getGroundTruth(), forward);
            accuracyFunc.calculate(trainData.get(i).getLabelNumber(), forward);

            networkModel.propogateBackward(error);
            networkModel.correctWeights(optimizer);
            System.out.println("Эпоха " + (epochNumber + 1) + ": " + (i + 1) + " обучающий пример из " + trainData.size());
        }

        System.out.println();
        String trainLoss = "Epoch " + (epochNumber + 1) + ", train loss: " + lossFunc.getOveralLoss();
        String trainAccuracy = "Epoch " + (epochNumber + 1) + ", train accuracy: " + accuracyFunc.getOveralAccuracy();
        System.out.println(trainLoss);
        System.out.println(trainAccuracy);
        // ПУСТЬ ПОТОМ ПИШЕТ В ФАЙЛ
        if (logsFile != null) {
            logToFile(trainLoss, trainAccuracy);
        }
        System.out.println();
    }

    protected void valid(List<DataFrame<T, D>> validData, int epochNumber) {
        for (int i = 0; i < validData.size(); i++){
            D forward = (D) networkModel.propogateForward(validData.get(i).getSample());
            lossFunc.calculate(validData.get(i).getGroundTruth(), forward);
            accuracyFunc.calculate(validData.get(i).getLabelNumber(), forward);
        }

        System.out.println();
        String validLoss = "Epoch " + (epochNumber + 1) + ", valid loss: " + lossFunc.getOveralLoss();
        String validAccuracy = "Epoch " + (epochNumber + 1) + ", valid accuracy: " + accuracyFunc.getOveralAccuracy();
        System.out.println(validLoss);
        System.out.println(validAccuracy);
        // ПУСТЬ ПОТОМ ПИШЕТ В ФАЙЛ
        if (logsFile != null) {
            logToFile(validLoss, validAccuracy);
        }
    }

    @Override
    public void test(Dataset<T, D> dataset) {
        List<DataFrame<T, D>> testData = dataset.getTestData();

        for (int i = 0; i < testData.size(); i++){
            D forward = networkModel.propogateForward(testData.get(i).getSample());
//            D error = forward.subtract(testData.get(i).getGroundTruth());
            lossFunc.calculate(testData.get(i).getGroundTruth(), forward);
            accuracyFunc.calculate(testData.get(i).getLabelNumber(), forward);
        }

        System.out.println();
        String testLoss = "Test loss: " + lossFunc.getOveralLoss();
        String testAccuracy = "Test accuracy: " + accuracyFunc.getOveralAccuracy();
        System.out.println(testLoss);
        System.out.println(testAccuracy);
        // ПУСТЬ ПОТОМ ПИШЕТ В ФАЙЛ
        if (logsFile != null) {
            logToFile(testLoss, testAccuracy);
        }
    }

    protected void logToFile(String... lines) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logsFile.getAbsolutePath(), true))) {
            for (String line: lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NetworkModel getNetworkModel() {
        return networkModel;
    }
}
