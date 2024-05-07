package util.task;

import org.junit.jupiter.api.Test;
import util.ConvolutionParallelUtils;
import util.ConvolutionUtils;
import util.Matrix3DUtils;
import util.model.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConvolutionTest {

    @Test
    public void convolutionParallelResultCheck() throws IOException {
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
        //Вход размера 32х32
        Matrix3D wrapping3D = Matrix3DUtils.getDataFrame(image);
        //Вход размера 5х7
//        Matrix3D wrapping3D = new Matrix3D(wrapping);

        int kernelsCount = 1;

        Matrix3D[] kernels = new Matrix3D[kernelsCount];
        double[] biases = new double[kernelsCount];
        int stride = 2;

        //Инициализация фильтров и баесов
        for (int i = 0; i < kernelsCount; i++) {
            kernels[i] = new Matrix3D(kernel1);
//            kernels[i] = wrapping3D;
            biases[i] = bias1;
        }

        Matrix3D resultParallel = null;
        Matrix3D result = null;

        int countOfConv = 1;
        for (int i = 0; i < countOfConv; i++) {
            resultParallel = ConvolutionParallelUtils.convolutionParallel(wrapping3D, kernels, biases, stride, 6);
            result = ConvolutionUtils.convolution(wrapping3D, kernels, biases, stride);
        }
        System.out.println("-------------------Результат обычного------------");
        MatrixUtils.printMatrix3D(result);
        System.out.println("-------------------Результат параллельного------------");
        MatrixUtils.printMatrix3D(resultParallel);
    }

    @Test
    public void testConvolutionParallel() throws IOException {
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
        //Вход размера 32х32
        Matrix3D wrapping3D = Matrix3DUtils.getDataFrame(image);
        //Вход размера 5х7
//        Matrix3D wrapping3D = new Matrix3D(wrapping);

        int kernelsCount = 1;

        Matrix3D[] kernels = new Matrix3D[kernelsCount];
        double[] biases = new double[kernelsCount];
        int stride = 2;

        //Инициализация фильтров и баесов
        for (int i = 0; i < kernelsCount; i++) {
            kernels[i] = new Matrix3D(kernel1);
//            kernels[i] = wrapping3D;
            biases[i] = bias1;
        }

        Matrix3D result = null;

        int countOfConv = 1;
        for (int i = 0; i < countOfConv; i++) {
            result = ConvolutionParallelUtils.convolutionParallel(wrapping3D, kernels, biases, stride, 6);
//            result = ConvolutionUtils.convolution(wrapping3D, kernels, biases, stride);
        }
        MatrixUtils.printMatrix3D(result);
    }

    @Test
    public void testConvolutionParallelForStolenArch() throws IOException {
        //32x30x30
        //32x32x3x3
//        Matrix3D input = new Matrix3D(32,30, 30);
//        int kernelChannel = 32;
//        int kernelSize = 3;
//        Matrix3DUtils.fillRandom(input);
//        double bias1 = 1;
//        int kernelsCount = 32;

        //32x30x30
        //32x32x3x3
        Matrix3D input = new Matrix3D(3,32, 32);
        int kernelChannel = 3;
        int kernelSize = 3;
        Matrix3DUtils.fillRandom(input);
        double bias1 = 1;
        int kernelsCount = 32;

        Matrix3D[] kernels = new Matrix3D[kernelsCount];
        double[] biases = new double[kernelsCount];
        int stride = 1;

        //Инициализация фильтров и баесов
        for (int i = 0; i < kernelsCount; i++) {
            kernels[i] = new Matrix3D(kernelChannel, kernelSize, kernelSize);
            Matrix3DUtils.fillRandom(kernels[i]);
            biases[i] = bias1;
        }

        Matrix3D result = null;

        long start = System.currentTimeMillis();

        int countOfConv = 5000;
        for (int i = 0; i < countOfConv; i++) {
            result = ConvolutionParallelUtils.convolutionParallel(input, kernels, biases, stride, 16);
//            result = ConvolutionUtils.convolution(input, kernels, biases, stride);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(Double.valueOf(end - start) / 1000);

        MatrixUtils.printMatrix3D(result);
    }
}
