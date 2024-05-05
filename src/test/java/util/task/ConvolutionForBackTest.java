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

public class ConvolutionForBackTest {

    @Test
    public void convolutionForBackParallelResultCheck() throws IOException {
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
//        Matrix3D wrapping3D = Matrix3DUtils.getDataFrame(image);
        //Вход размера 5х7
        Matrix3D wrapping3D = new Matrix3D(wrapping);

        int kernelsCount = 32;

        Matrix3D kernel = new Matrix3D(kernel1);
        double[] biases = new double[kernelsCount];
        int stride = 2;

        Matrix3D[] result = null;
        Matrix3D[] resultParallel = null;

        int countOfConv = 1;
        for (int i = 0; i < countOfConv; i++) {
            result = ConvolutionParallelUtils.convolutionForBackParallel(wrapping3D, kernel, stride, 6);
            resultParallel = ConvolutionUtils.convolutionForBack(wrapping3D, kernel, stride);
        }
        System.out.println("-------------------Результат обычного------------");
        for (int i = 0; i < result.length; i++) {
            MatrixUtils.printMatrix3D(result[i]);
        }
        System.out.println("-------------------Результат параллельного------------");
        for (int i = 0; i < result.length; i++) {
            MatrixUtils.printMatrix3D(resultParallel[i]);
        }
    }

    @Test
    public void testConvolutionForBackParallel() throws IOException {
        Matrix3D preActivation = new Matrix3D(128,12, 12);
        Matrix3D localGradient = new Matrix3D(256, 10, 10);

        Matrix3DUtils.fillRandom(preActivation);
        Matrix3DUtils.fillRandom(localGradient);

        int stride = 1;

        Matrix3D[] result = null;

        int countOfConv = 500;
        for (int i = 0; i < countOfConv; i++) {
            result = ConvolutionParallelUtils.convolutionForBackParallel(preActivation, localGradient, stride, 28);
//            result = ConvolutionUtils.convolutionForBack(preActivation, localGradient, stride);
        }
        for (int i = 0; i < result.length; i++) {
            MatrixUtils.printMatrix3D(result[i]);
        }
    }
}
