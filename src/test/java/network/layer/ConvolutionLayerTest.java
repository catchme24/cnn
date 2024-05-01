package network.layer;

import com.sun.tools.javac.Main;
import function.ReLu;
import org.junit.jupiter.api.Test;
import util.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

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
}
