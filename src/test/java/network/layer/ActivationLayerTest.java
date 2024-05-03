package network.layer;

import function.ActivationFunc;
import function.ReLu;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
class ActivationLayerTest {

    private double[] array;

    @BeforeEach
    public void setup() {
        //Зафиксировать сид, чтобы тесты выдавали однотипные данные!
        MatrixUtils.setSeed(10);
        array = new double[]{0.12315, 0.65323, 1.42235, -2.4324234};
    }

    @Test
    void testIsolatedLayerWithReluFunction() {
        ActivationFunc func = new ReLu();
        ActivationLayer layer = new ActivationLayer(func);

        RealMatrix input = MatrixUtils.createRowFromArray(array).transpose();
        log.info("ВХОДНАЯ МАТРИЦА");
        MatrixUtils.printMatrixTest(input);

        RealMatrix output = layer.propogateForward(input);
        log.info("ВЫХОДНАЯ МАТРИЦА");
        MatrixUtils.printMatrixTest(output);

        Assertions.assertEquals(input.getEntry(0, 0), output.getEntry(0, 0));
        Assertions.assertEquals(input.getEntry(1, 0), output.getEntry(1, 0));
        Assertions.assertEquals(input.getEntry(2, 0), output.getEntry(2, 0));
        Assertions.assertEquals(0, output.getEntry(3, 0));
    }

    @Test
    public void testBackPropagateActivationLayer() throws IOException {
        BufferedImage image = ImageIO.read(new File("C:\\0001.png"));

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(2, 8, 8, imageDimension);

        Activation3DLayer layer2 = new Activation3DLayer(new ReLu());

        layer2.setPrevious(layer1);

        Matrix3D result1 = layer1.propogateForward(dataFrame);
        Matrix3D result2 = layer2.propogateForward(result1);
        Matrix3D result3 = layer2.propogateBackward(result2);

        System.out.println("---------Convolution--------");
        MatrixUtils.printMatrix3D(result1);
        System.out.println("---------Forward activation--------");
        MatrixUtils.printMatrix3D(result2);
        System.out.println("---------Backward activation--------");
        MatrixUtils.printMatrix3D(result3);
    }

}