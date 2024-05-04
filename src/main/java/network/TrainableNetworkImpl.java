package network;

import data.Dataset;
import network.model.NetworkModel;
import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TrainableNetworkImpl extends AbstractTrainableNetwork {


    public TrainableNetworkImpl(NetworkModel networkModel) {
        super(networkModel);
    }


    @Override
    public void learn(int epoch, int batchSize, Dataset dataset) {
        // TO-DO

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("D:\\0001.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);
        RealMatrix forwardResult = (RealMatrix) networkModel.propogateForward(dataFrame);
//        Matrix3D forwardResult = (Matrix3D) networkModel.propogateForward(dataFrame);
        Matrix3D backwardResult = (Matrix3D) networkModel.propogateBackward(forwardResult);

        System.out.println("РЕЗУЛЬТАТ ПРЯМОГО");
        MatrixUtils.printMatrixTest(forwardResult);
//        MatrixUtils.printMatrix3D(forwardResult);
        System.out.println("РЕЗУЛЬТАТ ОБРАТНОГО");
        MatrixUtils.printMatrix3D(backwardResult);


    }

    @Override
    public void learn(int epoch, double learnRate, int batchSize, Dataset dataset) {

    }
}
