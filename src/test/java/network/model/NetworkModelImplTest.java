package network.model;

import function.ReLu;
import function.Softmax;
import network.TrainableNetwork;
import network.builder.NetworkBuilder;
import network.layer.*;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Test;
import util.Matrix3D;
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
        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));
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
        layers.add(new ActivationLayer(new Softmax()));

        NetworkModel networkModel = new NetworkModelImpl(layers);

        Object object = networkModel.propogateForward(dataFrame);
//        MatrixUtils.printMatrix3D((Matrix3D) object);
        MatrixUtils.printMatrixTest((RealMatrix) object);
    }
}
