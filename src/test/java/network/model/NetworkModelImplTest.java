package network.model;

import function.ReLu;
import function.Softmax;
import network.layer.*;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Test;
import util.model.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public class NetworkModelImplTest {

    @Test
    public void test() throws IOException {
        BufferedImage image = ImageIO.read(new File("C:\\0001.png"));
        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Deque<Layer> layers = new LinkedList<>();

        Dimension imageDimension = new Dimension(3, 32, 32);

        layers.add(new ConvolutionLayer(32, 5, 1, imageDimension));
        layers.add(new Activation3DLayer(new ReLu()));
        layers.add(new ConvolutionLayer(64, 4, 2));
        layers.add(new Activation3DLayer(new ReLu()));
        layers.add(new ConvolutionLayer(128, 3, 2));
        layers.add(new Activation3DLayer(new ReLu()));
        layers.add(new ConvolutionLayer(256, 3, 1));
        layers.add(new Activation3DLayer(new ReLu()));
        layers.add(new Flatten());
        layers.add(new FullyConnected(40));
        layers.add(new ActivationLayer(new ReLu()));
        layers.add(new FullyConnected(10));
//        layers.add(new ActivationLayer(new ReLu()));
//        layers.add(new ActivationLayer(new Softmax()));

        NetworkModel networkModel = new NetworkModelImpl(layers);

        Object object = networkModel.propogateForward(dataFrame);
//        MatrixUtils.printMatrix3D((Matrix3D) object);
        MatrixUtils.printMatrixTest((RealMatrix) object);
    }

    @Test
    public void testFirstArchitecture() throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));
        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Deque<Layer> layers = new LinkedList<>();

        Dimension imageDimension = new Dimension(3, 32, 32);

        layers.add(new ConvolutionLayer(32, 3, 1, imageDimension));
        layers.add(new Activation3DLayer(new ReLu()));
        layers.add(new ConvolutionLayer(64, 3, 1));
        layers.add(new Activation3DLayer(new ReLu()));
        layers.add(new PoolingLayer(2, 2));
        layers.add(new ConvolutionLayer(128, 3, 1));
        layers.add(new Activation3DLayer(new ReLu()));
        layers.add(new ConvolutionLayer(256, 3, 1));
        layers.add(new Activation3DLayer(new ReLu()));
        layers.add(new PoolingLayer(2, 2));
        //5 x 5 x 256
        layers.add(new Flatten());
        layers.add(new FullyConnected(1024));
        layers.add(new ActivationLayer(new ReLu()));
        layers.add(new DropoutLayer(0.3));
        layers.add(new FullyConnected(128));
        layers.add(new ActivationLayer(new ReLu()));
//        layers.add(new DropoutLayer(0.3));
        layers.add(new FullyConnected(10));
        layers.add(new ActivationLayer(new ReLu()));
        layers.add(new ActivationLayer(new Softmax()));

        NetworkModel networkModel = new NetworkModelImpl(layers);

        RealMatrix oneHotEncodingVector = MatrixUtils.createEmptyVector(10);
        oneHotEncodingVector.setEntry(0, 0, 1.0);
        for (int i = 0; i < 20; i++){
            RealMatrix object = (RealMatrix) networkModel.propogateForward(dataFrame);
            RealMatrix error = object.subtract(oneHotEncodingVector);
            networkModel.propogateBackward(error);
            System.out.println(i);
        }


//        MatrixUtils.printMatrixTest((RealMatrix) object);
//        double newSum = 0;
//        for (int i = 0; i < object.getRowDimension(); i++) {
//            newSum += object.getEntry(i, 0);
//        }
//
//        System.out.println("СУММА: " + newSum);
    }
}
