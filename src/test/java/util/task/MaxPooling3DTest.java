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

public class MaxPooling3DTest {

    @Test
    public void testMaxPooling3DParallel() throws IOException {
        //Тензор ошибки: 128x12x12
        //Свапнутые кернелы: 64x128x3x3
        Matrix3D pooling = new Matrix3D(64,28, 28);

        Matrix3DUtils.fillRandom(pooling);

        int stride = 2;
        int kernelSize = 2;

        Matrix3D result = null;

        long start = System.currentTimeMillis();

        int countOfConv = 50000;
        for (int i = 0; i < countOfConv; i++) {
            result = ConvolutionParallelUtils.maxPooling3DParallel(pooling,kernelSize , stride, 24);
//            result = ConvolutionUtils.maxPooling3D(pooling, kernelSize, stride);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(Double.valueOf(end - start) / 1000);

        MatrixUtils.printMatrix3D(result);
    }
}
