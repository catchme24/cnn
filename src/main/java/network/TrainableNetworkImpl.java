package network;

import data.DataFrame;
import data.Dataset;
import function.DefaultAccuracyFunction;
import function.DefaultLossFunction;
import network.model.NetworkModel;
import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3DUtils;
import util.model.Matrix3D;

import java.io.*;
import java.util.List;

public class TrainableNetworkImpl extends AbstractTrainableNetwork {

    private File logsFile;
    private File saveFile;

    public TrainableNetworkImpl(NetworkModel networkModel, File logsFile, File saveFile) {
        super(networkModel);
        this.logsFile = logsFile;
        this.saveFile = saveFile;
    }

    @Override
    public void test(Dataset dataset) {
        DefaultLossFunction loss = new DefaultLossFunction();
        DefaultAccuracyFunction accuracy = new DefaultAccuracyFunction();
        Dataset<Matrix3D, RealMatrix> dataset1 = (Dataset<Matrix3D, RealMatrix>) dataset;
        List<DataFrame<Matrix3D, RealMatrix>> testData = dataset1.getTestData();

        for (int i = 0; i < testData.size(); i++){
            RealMatrix forward = (RealMatrix) networkModel.propogateForward(testData.get(i).getSample());
            RealMatrix error = forward.subtract(testData.get(i).getGroundTruth());
            loss.calculate(testData.get(i).getGroundTruth(), forward);
            accuracy.calculate(testData.get(i).getLabelNumber(), forward);
        }

        System.out.println();

        String line1 = "test loss: " + loss.getOveralLoss();
        String line2 = "test accuracy: " + accuracy.getOveralAccuracy();
        // ПУСТЬ ПОТОМ ПИШЕТ В ФАЙЛ
        System.out.println(line1);
        System.out.println(line2);

        // ПУСТЬ ПОТОМ ПИШЕТ В ФАЙЛ
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logsFile.getAbsolutePath(), true))) {
            bufferedWriter.write(line1);
            bufferedWriter.newLine();
            bufferedWriter.write(line2);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

            String line1 = "train loss on epoch " + (i+1) + ": " +loss.getOveralLoss();
            String line2 = "train accuracy on epoch " + (i+1) + ": " +accuracy.getOveralAccuracy();
            // ПУСТЬ ПОТОМ ПИШЕТ В ФАЙЛ
            System.out.println(line1);
            System.out.println(line2);

            for (int j = 0; j < validData.size(); j++){
                RealMatrix forward = (RealMatrix) networkModel.propogateForward(validData.get(j).getSample());
                loss.calculate(validData.get(j).getGroundTruth(), forward);
                accuracy.calculate(validData.get(j).getLabelNumber(), forward);

            }

            System.out.println();

            String line3 = "valid loss on epoch " + (i+1) + ": " +loss.getOveralLoss();
            String line4 = "valid accuracy on epoch " + (i+1) + ": " +accuracy.getOveralAccuracy();
            // ПУСТЬ ПОТОМ ПИШЕТ В ФАЙЛ
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logsFile.getAbsolutePath(), true))) {
                bufferedWriter.write(line1);
                bufferedWriter.newLine();
                bufferedWriter.write(line2);
                bufferedWriter.newLine();
                bufferedWriter.write(line3);
                bufferedWriter.newLine();
                bufferedWriter.write(line4);
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(line3);
            System.out.println(line4);

            System.out.println();
        }
        //ТУТА СОХРАНИТЬ СЕТКУ
        networkModel.saveInFile(saveFile);
    }

    @Override
    public void learn(int epoch, double learnRate, int batchSize, Dataset dataset) {

    }

    @Override
    public NetworkModel getNetworkModel() {
        return networkModel;
    }
}
