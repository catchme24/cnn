package util.task;

import org.junit.jupiter.api.Test;
import util.ConvolutionUtils;
import util.Matrix3D;
import util.MatrixUtils;

public class ConvolutionTaskTest {

    @Test
    public void testConvolutionParallel() {
        //wrapping - 3x5x7
        //kernel - 3x3x3
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
        double[][][] kernel2 = {
                {       {1, 0, 1},
                        {1, 0, 1},
                        {1, 0, 1}},
                {       {1, 0, 1},
                        {0, -1, 0},
                        {1, 0, 1}},
                {       {1, 0, 0},
                        {-1, 1, 0},
                        {1, 1, 0}},
        };
        double[][][] kernel3 = {
                {       {1, 0, 1},
                        {0, 0, 0},
                        {1, 0, 1}},
                {       {-1, 0, -1},
                        {-1, 0, -1},
                        {-1, 0, -1}},
                {       {0, 0, 0},
                        {1, -1, 1},
                        {0, 0, 0}},
        };
        double[][][] kernel4 = {
                {       {1, 0, 1},
                        {0, 0, 0},
                        {1, 0, 1}},
                {       {-1, 0, -1},
                        {-1, 0, -1},
                        {-1, 0, -1}},
                {       {0, 0, 0},
                        {1, -1, 1},
                        {0, 0, 0}},
        };
        double[][][] kernel5 = {
                {       {1, 0, 1},
                        {0, 0, 0},
                        {1, 0, 1}},
                {       {-1, 0, -1},
                        {-1, 0, -1},
                        {-1, 0, -1}},
                {       {0, 0, 0},
                        {1, -1, 1},
                        {0, 0, 0}},
        };
        double[][][] kernel6 = {
                {       {1, 0, 1},
                        {0, 0, 0},
                        {1, 0, 1}},
                {       {-1, 0, -1},
                        {-1, 0, -1},
                        {-1, 0, -1}},
                {       {0, 0, 0},
                        {1, -1, 1},
                        {0, 0, 0}},
        };
        double[][][] kernel7 = {
                {       {1, 0, 1},
                        {0, 0, 0},
                        {1, 0, 1}},
                {       {-1, 0, -1},
                        {-1, 0, -1},
                        {-1, 0, -1}},
                {       {0, 0, 0},
                        {1, -1, 1},
                        {0, 0, 0}},
        };
        double[][][] kernel8 = {
                {       {1, 0, 1},
                        {0, 0, 0},
                        {1, 0, 1}},
                {       {-1, 0, -1},
                        {-1, 0, -1},
                        {-1, 0, -1}},
                {       {0, 0, 0},
                        {1, -1, 1},
                        {0, 0, 0}},
        };
        double[][][] kernel9 = {
                {       {1, 0, 1},
                        {0, 0, 0},
                        {1, 0, 1}},
                {       {-1, 0, -1},
                        {-1, 0, -1},
                        {-1, 0, -1}},
                {       {0, 0, 0},
                        {1, -1, 1},
                        {0, 0, 0}},
        };
        double[][][] kernel10 = {
                {       {1, 0, 1},
                        {0, 0, 0},
                        {1, 0, 1}},
                {       {-1, 0, -1},
                        {-1, 0, -1},
                        {-1, 0, -1}},
                {       {0, 0, 0},
                        {1, -1, 1},
                        {0, 0, 0}},
        };

        double bias1 = 1;

        Matrix3D wrapping3D = new Matrix3D(wrapping);
        Matrix3D kernel3D1 = new Matrix3D(kernel1);

        Matrix3D[] kernels = new Matrix3D[100];
        double[] biases = new double[100];
        int stride = 2;


        for (int i = 0; i < 100; i++) {
            kernels[i] = new Matrix3D(kernel1);
            biases[i] = bias1;
        }

        Matrix3D result = null;

        int countOfConv = 300000;
        for (int i = 0; i < countOfConv; i++) {
            result = ConvolutionUtils.convolutionParallel(wrapping3D, kernels, biases, stride);
//            result = ConvolutionUtils.convolution(wrapping3D, kernels, biases, stride);
//            System.out.println("Успех " + i);
        }
        MatrixUtils.printMatrix3D(result);
    }
}
