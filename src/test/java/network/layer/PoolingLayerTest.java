package network.layer;

import org.junit.jupiter.api.Test;
import util.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PoolingLayerTest {
    @Test
    public void testCreatePollingLayer() {
        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(32, 3, 1, imageDimension);

        ConvolutionLayer layer2 = new ConvolutionLayer(64, 3, 1);
        layer2.setPrevious(layer1);

        PoolingLayer layer3 = new PoolingLayer(2, 2);
        layer3.setPrevious(layer2);



        System.out.println("---------AFTER CONNECT FULL--------");
        System.out.println(layer1.getSize());
        System.out.println(layer2.getSize());
        System.out.println(layer3.getSize());
    }
    @Test
    public void testIsolatedPoolingLayer() throws IOException {
        BufferedImage image = ImageIO.read(new File("C:\\0001.png"));

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(10, 3, 1, imageDimension);

        PoolingLayer layer2 = new PoolingLayer(2, 2);
        layer2.setPrevious(layer1);


        Matrix3D result1 = layer1.propogateForward(dataFrame);
        Matrix3D result2 = layer2.propogateForward(result1);

        System.out.println("---------AFTER CONNECT FULL--------");
        MatrixUtils.printMatrix3D(result1);
        MatrixUtils.printMatrix3D(result2);
    }

    @Test
    public void testBackPropagatePoolingLayer() throws IOException {
        BufferedImage image = ImageIO.read(new File("C:\\0001.png"));

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(2, 3, 1, imageDimension);

        PoolingLayer layer2 = new PoolingLayer(2, 2);
        layer2.setPrevious(layer1);


        Matrix3D result1 = layer1.propogateForward(dataFrame);
        Matrix3D result2 = layer2.propogateForward(result1);
        Matrix3D result3 = layer2.propogateBackward(result2);

        System.out.println("---------AFTER CONNECT FULL--------");
        MatrixUtils.printMatrix3D(result1);
        MatrixUtils.printMatrix3D(result2);
        MatrixUtils.printMatrix3D(result3);
    }
}
