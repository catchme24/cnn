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

public class ConvolutionWithoutBaisesTest {

    @Test
    public void testConvolutionWithOutBaisesParallel() throws IOException {
        //Тензор ошибки: 128x12x12
        //Свапнутые кернелы: 64x128x3x3
        Matrix3D errorTensor = new Matrix3D(256,10, 10);
        Matrix3D[] swappedKernels = new Matrix3D[128];

        Matrix3DUtils.fillRandom(errorTensor);

        for (int i = 0; i < 128; i++) {
            Matrix3D kernel = new Matrix3D(256, 3, 3);
            Matrix3DUtils.fillRandom(kernel);
            swappedKernels[i] = new Matrix3D(256, 3, 3);
        }

        int stride = 1;

        Matrix3D result = null;

        long start = System.currentTimeMillis();

        int countOfConv = 500;
        for (int i = 0; i < countOfConv; i++) {
            result = ConvolutionParallelUtils.convolutionWithoutBaisesParallel(errorTensor, swappedKernels, stride, 30);
//            result = ConvolutionUtils.convolutionWithoutBiases(errorTensor, swappedKernels, stride);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(Double.valueOf(end - start) / 1000);

        MatrixUtils.printMatrix3D(result);
    }
}
