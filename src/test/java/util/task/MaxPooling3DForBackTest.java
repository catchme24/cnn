package util.task;

import org.junit.jupiter.api.Test;
import util.ConvolutionParallelUtils;
import util.ConvolutionUtils;
import util.Matrix3DUtils;
import util.MatrixUtils;
import util.model.Matrix3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MaxPooling3DForBackTest {

    @Test
    public void testMaxPooling3DForBackParallel() throws IOException {
        Matrix3D preActivation = new Matrix3D(64,28, 28);
        Matrix3D pooling = new Matrix3D(64,14, 14);

        Matrix3DUtils.fillRandom(preActivation);
        Matrix3DUtils.fillRandom(pooling);

        int stride = 2;
        int kernelSize = 2;

        Matrix3D result = null;

        long start = System.currentTimeMillis();

        int countOfConv = 50000;
        for (int i = 0; i < countOfConv; i++) {
            result = ConvolutionParallelUtils.maxPooling3DForBackParallel(preActivation,pooling , kernelSize, stride, 4);
//            result = ConvolutionUtils.maxPooling3DForBack(preActivation, pooling, kernelSize, stride);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(Double.valueOf(end - start) / 1000);

        MatrixUtils.printMatrix3D(result);
    }
}
