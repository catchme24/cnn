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
    public void maxPooling3DParallelResultCheck() throws IOException {
        double[][][] wrapping = {
                {       {1, 2, 3, 0, -1, 8, 3},
                        {2, 6, 0, 3, 2, 7, 6},
                        {2, 6, 0, 3, 2, 7, 6},
                        {0, 8, 1, 5, 3, 6, 1},
                        {7, 0, 11, 2, 3, -2, 7}},

                {       {1, 11, 3, 5, -1, 2, 3},
                        {4, 6, 0, 6, 1, 7, 7},
                        {2, 6, 0, 3, 2, 7, 6},
                        {0, 0, 1, 5, 3, 26, 1},
                        {7, 0, 11, 2, 3, -2, 13}},

                {       {2, 2, 5, 0, -2, 4, 3},
                        {2, 5, 0, -3, 4, 6, 6},
                        {2, 6, 0, 3, 2, 7, 6},
                        {3, -8, 1, 5, 3, 2, 1},
                        {7, 0, 6, 2, 5, -2, 11}},
        };
        double[][][] kernel1 = {
                {       {1, 0, 0},
                        {-1, 1, 0},
                        {1, 1, 0}},
                {       {1, 0, 1},
                        {1, 0, 1},
                        {1, 0, 1}},
                {       {1, 0, 1},
                        {0, -1, 0},
                        {1, 0, 1}},
        };
        double bias1 = 1;
        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));
        //Вход размера 32х32x3
        Matrix3D wrapping3D = Matrix3DUtils.getDataFrame(image);
        //Вход размера 5х7
//        Matrix3D wrapping3D = new Matrix3D(wrapping);

        Matrix3DUtils.fillRandom(wrapping3D);

        int stride = 2;
        int kernelSize = 2;

        Matrix3D result = null;
        Matrix3D resultParallel = null;

        long start = System.currentTimeMillis();

        int countOfConv = 1;
        for (int i = 0; i < countOfConv; i++) {
            resultParallel = ConvolutionParallelUtils.maxPooling3DParallel(wrapping3D,kernelSize , stride, 2);
            result = ConvolutionUtils.maxPooling3D(wrapping3D, kernelSize, stride);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(Double.valueOf(end - start) / 1000);

        System.out.println("-------------------Результат обычного------------");
        MatrixUtils.printMatrix3D(result);
        System.out.println("-------------------Результат параллельного------------");
        MatrixUtils.printMatrix3D(resultParallel);
    }
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
