package network;

import function.ReLu;
import network.layer.Activation3DLayer;
import network.layer.ConvolutionLayer;
import network.layer.Dimension;
import org.junit.jupiter.api.Test;
import util.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ArchTest {

    @Test
    public void tes_Conv_ReLu_Conv_ReLu_Conv_ReLu() throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));

        int w = image.getWidth();
        int h = image.getHeight();

        System.out.println(w + " " + h);

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(32, 5, 1, imageDimension);
        Activation3DLayer layer2 = new Activation3DLayer(new ReLu());
        ConvolutionLayer layer3 = new ConvolutionLayer(64, 4, 2);
        Activation3DLayer layer4 = new Activation3DLayer(new ReLu());
        ConvolutionLayer layer5 = new ConvolutionLayer(128, 3, 2);
        Activation3DLayer layer6 = new Activation3DLayer(new ReLu());
        ConvolutionLayer layer7 = new ConvolutionLayer(256, 3, 1);
        Activation3DLayer layer8 = new Activation3DLayer(new ReLu());

        layer2.setPrevious(layer1);
        layer3.setPrevious(layer2);
        layer4.setPrevious(layer3);
        layer5.setPrevious(layer4);
        layer6.setPrevious(layer5);
        layer7.setPrevious(layer6);
        layer8.setPrevious(layer7);

        Matrix3D result8 = null;
        for (int i = 0; i < 500; i++) {
            Matrix3D result1 = layer1.propogateForward(dataFrame);
            Matrix3D result2 = layer2.propogateForward(result1);
            Matrix3D result3 = layer3.propogateForward(result2);
            Matrix3D result4 = layer4.propogateForward(result3);
            Matrix3D result5 = layer5.propogateForward(result4);
            Matrix3D result6 = layer6.propogateForward(result5);
            Matrix3D result7 = layer7.propogateForward(result6);
            result8 = layer8.propogateForward(result7);
        }

        System.out.println("---------AFTER CONNECT FULL--------");
        MatrixUtils.printMatrix3D(result8);
    }
}