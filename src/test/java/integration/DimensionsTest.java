package integration;

import data.Dataset;
import data.DatasetHelperImpl;
import data.parser.MyDatasetParser;
import function.ReLu;
import function.Softmax;
import network.TrainableNetwork;
import network.builder.NetworkBuilder;
import network.layer.*;
import network.model.NetworkModel;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Test;
import util.Matrix3DUtils;
import util.MatrixUtils;
import util.model.Matrix3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DimensionsTest {

    @Test
    public void wantToCheckSizeOfResults() throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));
        //Вход размера 32х32
        Matrix3D wrapping3D = Matrix3DUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        TrainableNetwork trainableNetwork = NetworkBuilder.builder()
                .append(new ConvolutionLayer(32, 3, 1, imageDimension))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(64, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new PoolingLayer(2, 2))
                .append(new ConvolutionLayer(128, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(256, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new PoolingLayer(2, 2))
                //5 x 5 x 256
                .append(new Flatten())
                .append(new FullyConnected(1024))
                .append(new ActivationLayer(new ReLu()))
                .append(new DropoutLayer(0.3))
                .append(new FullyConnected(128))
                .append(new ActivationLayer(new ReLu()))
                .append(new FullyConnected(10))
                .append(new ActivationLayer(new ReLu()))
                .append(new ActivationLayer(new Softmax()))
                .build();

        NetworkModel networkModel = trainableNetwork.getNetworkModel();
        RealMatrix result = (RealMatrix) networkModel.propogateForward(wrapping3D);
        networkModel.propogateBackward(result);
    }


}
