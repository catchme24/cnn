package network.layer;

import function.ReLu;
import org.junit.jupiter.api.Test;
import util.model.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConvolutionLayerTest {

    @Test
    public void testCreateConvLayer() {
        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(50, 4, 2, imageDimension);

//        System.out.println("---------BEFORE CONNECT LAYER 1--------");
//        System.out.println(layer1.getSize());

        ConvolutionLayer layer2 = new ConvolutionLayer(100, 3, 1);
        layer2.setPrevious(layer1);

        ConvolutionLayer layer3 = new ConvolutionLayer(200, 3, 2);
        layer3.setPrevious(layer2);

        ConvolutionLayer layer4 = new ConvolutionLayer(400, 3, 1);
        layer4.setPrevious(layer3);


        System.out.println("---------AFTER CONNECT FULL--------");
        layer1.getSize();
        layer2.getSize();
        layer3.getSize();
        layer4.getSize();
    }

    @Test
    public void testIsolatedConvLayer() throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));

        int w = image.getWidth();
        int h = image.getHeight();

        System.out.println(w + " " + h);

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(10, 4, 2, imageDimension);

        Matrix3D result1 = layer1.propogateForward(dataFrame);

        System.out.println("---------AFTER CONNECT FULL--------");
        MatrixUtils.printMatrix3D(result1);
    }

    @Test
    public void testBackPropagateConvolutionLayer() throws IOException {
        BufferedImage image = ImageIO.read(new File("C:\\0001.png"));

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(2, 8, 8, imageDimension);

        Activation3DLayer layer2 = new Activation3DLayer(new ReLu());

        ConvolutionLayer layer3 = new ConvolutionLayer(2, 2, 1);

        layer2.setPrevious(layer1);
        layer3.setPrevious(layer2);

        Matrix3D result1 = layer1.propogateForward(dataFrame);
        Matrix3D result2 = layer2.propogateForward(result1);
        Matrix3D result3 = layer3.propogateForward(result2);
        Matrix3D result = layer3.propogateBackward(result3);

        System.out.println("---------Convolution forward--------");
        MatrixUtils.printMatrix3D(result1);
        System.out.println("---------Forward activation--------");
        MatrixUtils.printMatrix3D(result2);
        System.out.println("---------Convolution forward--------");
        MatrixUtils.printMatrix3D(result3);
        System.out.println("---------Convolution backward--------");
        MatrixUtils.printMatrix3D(result);
    }
}
