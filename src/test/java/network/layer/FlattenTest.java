package network.layer;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Test;
import util.model.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlattenTest {
    @Test
    public void testIsolatedFlatten() throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(2, 3, 1, imageDimension);

        PoolingLayer layer2 = new PoolingLayer(2, 2);

        Flatten layer3 = new Flatten();

        layer2.setPrevious(layer1);
        layer3.setPrevious(layer2);

        Matrix3D result1 = layer1.propogateForward(dataFrame);
        Matrix3D result2 = layer2.propogateForward(result1);
        RealMatrix result3 = layer3.propogateForward(result2);
        Matrix3D result4 = layer3.propogateBackward(result3);

        MatrixUtils.printMatrix3D(result2);
        MatrixUtils.printMatrixTest(result3);
        System.out.println("---------AFTER BACK FLATTEN--------");
//        MatrixUtils.printMatrix3D(result4);
    }
    @Test
    public void testIsolatedFlattePlusFullyConnected() throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(3, 8, 8, imageDimension);
        PoolingLayer layer2 = new PoolingLayer(2, 2);
        Flatten layer3 = new Flatten();
        FullyConnected layer4 = new FullyConnected(5);

        layer2.setPrevious(layer1);
        layer3.setPrevious(layer2);
        layer4.setPrevious(layer3);

        Matrix3D result1 = layer1.propogateForward(dataFrame);
        Matrix3D result2 = layer2.propogateForward(result1);
        RealMatrix result3 = layer3.propogateForward(result2);
        RealMatrix result4 = layer4.propogateForward(result3);

        MatrixUtils.printMatrixTest(result4);
    }
}
