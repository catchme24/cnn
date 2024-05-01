package util;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static util.ArraysUtils.*;

public class ConvolutionUtilsTest {

//    @Test
//    public void testResultSizeOfInnerWrap2D_case1() {
//        //wrapping - 3x5
//        //kernel - 3x3
//        double[][] wrapping = {{1, 2, 3, 0, -1}, {7, 1, 4, 0, 3}, {5, 6, 7, 8, 11}};
//        double[][] kernel = {{1, 0, 1}, {-1, 1, 0}, {1, 1, 0}};
//
//        int stride = 1;
//
//        double[][] wrapped = ConvolutionUtils.wrap2d(wrapping, kernel, stride);
//
//        //reuslt must be - 1x3
//        //Visualization
//        RealMatrix forConsole = MatrixUtils.createInstance(wrapped);
//        MatrixUtils.printMatrixTest(forConsole);
//
//        Assertions.assertEquals(wrapped.length, 1);
//        Assertions.assertEquals(wrapped[0].length, 3);
//    }
//
//    @Test
//    public void testResultSizeOfInnerWrap2D_case2() {
//        //wrapping - 4x7
//        //kernel - 3x3
//        double[][] wrapping = { {1, 2, 3, 0, -1, 8, 3},
//                {2, 6, 0, 3, 2, 7, 6},
//                {0, 8, 1, 5, 3, 6, 1},
//                {7, 0, 11, 2, 3, -2, 7}
//        };
//        double[][] kernel = {{1, 0, 1}, {-1, 1, 0}, {1, 1, 0}};
//
//        int stride = 1;
//
//        double[][] wrapped = ConvolutionUtils.wrap2d(wrapping, kernel, stride);
//
//        //reuslt must be - 2x5
//        //Visualization
//        RealMatrix forConsole = MatrixUtils.createInstance(wrapped);
//        MatrixUtils.printMatrixTest(forConsole);
//
//        Assertions.assertEquals(wrapped.length, 2);
//        Assertions.assertEquals(wrapped[0].length, 5);
//    }
//    @Test
//    public void testInnerWrap2D() {
//        //wrapping - 4x7
//        //kernel - 3x3
//        double[][] wrapping = { {1, 2, 3, 0, -1, 8, 3},
//                {2, 6, 0, 3, 2, 7, 6},
//                {0, 8, 1, 5, 3, 6, 1},
//                {7, 0, 11, 2, 3, -2, 7}
//        };
//        double[][] kernel = {   {1, 0, 1},
//                {-1, 1, 0},
//                {1, 1, 0}
//        };
//
//        double[][] result = {   {16.0, 5.0, 11.0, 15.0, 16.0},
//                {17.0, 13.0, 19.0, 13.0, 12.0}
//        };
//
//        int stride = 1;
//
//        double[][] wrapped = ConvolutionUtils.wrap2d(wrapping, kernel, stride);
//
//        /*
//            reuslt must be
//            [16.0, 5.0, 11.0, 15.0, 16.0]
//            [17.0, 13.0, 19.0, 13.0, 12.0]
//        */
//        RealMatrix wrappedMatrix = MatrixUtils.createInstance(wrapped);
//        RealMatrix resultMatrix = MatrixUtils.createInstance(result);
//
//        //Visualization
//        MatrixUtils.printMatrixTest(wrappedMatrix);
//        MatrixUtils.printMatrixTest(resultMatrix);
//
//        Assertions.assertTrue(Arrays.deepEquals(wrappedMatrix.getData(), resultMatrix.getData()));
//    }
//
//    @Test
//    public void testWrap2D() {
//        //wrapping - 4x7
//        //kernel - 3x3
//        double[][] wrapping = { {1, 2, 3, 0, -1, 8, 3},
//                                {2, 6, 0, 3, 2, 7, 6},
//                                {0, 8, 1, 5, 3, 6, 1},
//                                {7, 0, 11, 2, 3, -2, 7}
//        };
//        double[][] kernel = {   {1, 0, 1},
//                {-1, 1, 0},
//                {1, 1, 0}
//        };
//
//        double[][] result = {   {16.0, 5.0, 11.0, 15.0, 16.0},
//                {17.0, 13.0, 19.0, 13.0, 12.0}
//        };
//
//        Matrix3D wrapping2D = new Matrix3D(wrapping);
//        Matrix3D kernel2D = new Matrix3D(kernel);
//
//        int stride = 1;
//
//        Matrix3D wrapped2D = ConvolutionUtils.wrap(wrapping2D, kernel2D, stride);
//
//        Assertions.assertEquals(1, wrapped2D.getMatrix3d().length);
//        Assertions.assertEquals(2, wrapped2D.getDimension());
//        Assertions.assertEquals(2, wrapped2D.getMatrix3d()[0].length);
//        Assertions.assertEquals(5, wrapped2D.getMatrix3d()[0][0].length);
//
//        double[][][] wrappedResult = wrapped2D.getMatrix3d();
//
//        //Visualization
//        /*
//            reuslt must be - 2x5
//            [16.0, 5.0, 11.0, 15.0, 16.0]
//            [17.0, 13.0, 19.0, 13.0, 12.0]
//        */
//        for (int i = 0; i < wrappedResult.length; i++) {
//            RealMatrix matrix = MatrixUtils.createInstance(wrappedResult[i]);
//            MatrixUtils.printMatrixTest(matrix);
//
//        }
//
//        Assertions.assertTrue(Arrays.deepEquals(wrappedResult[0], result));
//
//    }
    @Test
    public void testConvolution2D() {
        //wrapping - 4x7
        //kernel - 3x3
        double[][] wrapping = { {1, 2, 3, 0, -1, 8, 3},
                                {2, 6, 0, 3, 2, 7, 6},
                                {0, 8, 1, 5, 3, 6, 1},
                                {7, 0, 11, 2, 3, -2, 7}
        };
        double[][] wrapping1 = { {1, 2, 3, 0, -1},
                                {2, 6, 0, 3, 2},
                                {0, 8, 1, 5, 3},
                                {7, 0, 11, 2, 3},
                                {7, 0, 11, 2, 3}
        };
        double[][] wrapping2 = { {1, 2, 3, 0},
                {2, 6, 0, 3},
                {0, 8, 1, 5},
                {7, 0, 11, 2},
                {7, 0, 11, 2},
                {7, 0, 11, 2},
                {7, 0, 11, 2}
        };
        double[][] kernel = {   {1, 0, 1},
                                {-1, 1, 0},
                                {1, 1, 0}
        };

        double[][] result = {   {16.0, 5.0, 11.0, 15.0, 16.0},
                                {17.0, 13.0, 19.0, 13.0, 12.0}
        };


        int stride = 1;

        double[][] wrapped2D = ConvolutionUtils.convolution2D(wrapping2, kernel, stride);

//        Assertions.assertEquals(1, wrapped2D.getMatrix3d().length);
//        Assertions.assertEquals(2, wrapped2D.getDimension());
//        Assertions.assertEquals(2, wrapped2D.getMatrix3d()[0].length);
//        Assertions.assertEquals(5, wrapped2D.getMatrix3d()[0][0].length);

//        float[][][] wrappedResult = wrapped2D.getMatrix3d();

        //Visualization
        /*
            reuslt must be - 2x5
            [16.0, 5.0, 11.0, 15.0, 16.0]
            [17.0, 13.0, 19.0, 13.0, 12.0]
        */
        RealMatrix matrix = MatrixUtils.createInstance(wrapped2D);
        MatrixUtils.printMatrixTest(matrix);

//        Assertions.assertTrue(Arrays.deepEquals(wrappedResult[0], result));
    }

    @Test
    public void testConvolution3D() {
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
        double[][][] kernel = {
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

        double[][] result = {

        };

        Matrix3D wrapping3D = new Matrix3D(wrapping);
        Matrix3D kernel3D = new Matrix3D(kernel);

        double bias = 1;
        int stride = 1;

        double[][] wrapped3D = ConvolutionUtils.convolution3D(wrapping3D, kernel3D, bias, stride);


        //Visualization

        RealMatrix matrix = MatrixUtils.createInstance(wrapped3D);
        MatrixUtils.printMatrixTest(matrix);


    }
    @Test
    public void testConvolution() {
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




        Matrix3D wrapping3D = new Matrix3D(wrapping);

        Matrix3D kernel3D1 = new Matrix3D(kernel1);
        Matrix3D kernel3D2 = new Matrix3D(kernel2);
        Matrix3D kernel3D3 = new Matrix3D(kernel3);
        Matrix3D[] kernels = new Matrix3D[]{kernel3D1, kernel3D2, kernel3D3};

        double bias1 = 1;
        double bias2 = 2;
        double bias3 = 3;

        double[] biases = new double[]{bias1, bias2, bias3};

        int stride = 2;

        Matrix3D result = ConvolutionUtils.convolution(wrapping3D, kernels, biases, stride);

        MatrixUtils.printMatrix3D(result);
    }

    @Test
    public void zeroPaddingTest() {
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

        Matrix3D wrapping3D = new Matrix3D(wrapping);

        Matrix3D result = ConvolutionUtils.zeroPadding(wrapping3D, 2);

        MatrixUtils.printMatrix3D(result);
    }
    @Test
    public void dilateTest() {
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

        Matrix3D wrapping3D = new Matrix3D(wrapping);

        Matrix3D result = ConvolutionUtils.dilate(wrapping3D, 0);

        MatrixUtils.printMatrix3D(result);
    }

    @Test
    public void dilateAndZeroPaddingTest() {
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

        Matrix3D wrapping3D = new Matrix3D(wrapping);

        Matrix3D result = ConvolutionUtils.dilate(wrapping3D, 2);
        result = ConvolutionUtils.zeroPadding(result, 2);

        MatrixUtils.printMatrix3D(result);
    }

//    @Test
//    public void testWrap3D() {
//        //wrapping - 3x5x7
//        //kernel - 3x3x3
//        double[][][] wrapping = {
//                {       {1, 2, 3, 0, -1, 8, 3},
//                        {2, 6, 0, 3, 2, 7, 6},
//                        {2, 6, 0, 3, 2, 7, 6},
//                        {0, 8, 1, 5, 3, 6, 1},
//                        {7, 0, 11, 2, 3, -2, 7}},
//
//                {       {1, 11, 3, 5, -1, 2, 3},
//                        {4, 6, 0, 6, 1, 7, 7},
//                        {2, 6, 0, 3, 2, 7, 6},
//                        {0, 0, 1, 5, 3, 26, 1},
//                        {7, 0, 11, 2, 3, -2, 13}},
//
//                {       {2, 2, 5, 0, -2, 4, 3},
//                        {2, 5, 0, -3, 4, 6, 6},
//                        {2, 6, 0, 3, 2, 7, 6},
//                        {3, -8, 1, 5, 3, 2, 1},
//                        {7, 0, 6, 2, 5, -2, 11}},
//        };
//        double[][][] kernel = {
//                {       {1, 0, 1},
//                        {1, 0, 1},
//                        {1, 0, 1}},
//                {       {1, 0, 1},
//                        {0, -1, 0},
//                        {1, 0, 1}},
//                {       {1, 0, 0},
//                        {-1, 1, 0},
//                        {1, 1, 0}},
//        };
//
//        double[][][] result = {
//                {       {7.0, 24.0, 8.0, 29.0, 14.0},
//                        {21.0, 24.0, 20.0, 21.0, 22.0}},
//                {       {-1.0, 21.0, 0.0, 37.0, -1.0},
//                        {22.0, 13.0, 10.0, 10.0, -2.0}},
//                {       {0.0, -10.0, 8.0, 15.0, 5.0},
//                        {-2.0, 20.0, 12.0, 2.0, 6.0}},
//        };
//
//        Matrix3D wrapping3D = new Matrix3D(wrapping);
//        Matrix3D kernel3D = new Matrix3D(kernel);
//
//        int stride = 2;
//
//        Matrix3D wrapped3D = ConvolutionUtils.wrap(wrapping3D, kernel3D, stride);
//
//        Assertions.assertEquals(3, wrapped3D.getMatrix3d().length);
//        Assertions.assertEquals(3, wrapped3D.getDimension());
//        Assertions.assertEquals(2, wrapped3D.getMatrix3d()[0].length);
//        Assertions.assertEquals(3, wrapped3D.getMatrix3d()[0][0].length);
//
//        double[][][] wrappedResult = wrapped3D.getMatrix3d();
//
//        //Visualization
//        /*
//            reuslt must be - 3x2x5
//            {
//                {   {7.0, 24.0, 8.0, 29.0, 14.0},
//                    {21.0, 24.0, 20.0, 21.0, 22.0}},
//                {   {-1.0, 21.0, 0.0, 37.0, -1.0},
//                    {22.0, 13.0, 10.0, 10.0, -2.0}},
//                {   {0.0, -10.0, 8.0, 15.0, 5.0},
//                    {-2.0, 20.0, 12.0, 2.0, 6.0}},
//            }
//        */
//        for (int i = 0; i < wrappedResult.length; i++) {
//            RealMatrix matrix = MatrixUtils.createInstance(wrappedResult[i]);
//            MatrixUtils.printMatrixTest(matrix);
//        }
////        Assertions.assertTrue(Arrays.deepEquals(wrappedResult, result));
//    }
//
//    @Test
//    public void testWrap3DWithRandomNumbers() {
//        //wrapping - 3x7x12
//        //kernel - 3x3x3
//        double[][][] wrapping = {
//                {       {1, 2, 3, 0, -1, 8, 3, 1, 5, 6, 1, 5},
//                        {2, 6, 0, 3, 2, 7, 6, 1, 5, 6, 1, 5},
//                        {0, 8, 1, 5, 3, 6, 1, 1, 5, 6, 1, 5},
//                        {2, 6, 0, 3, 2, 7, 6, 1, 5, 6, 1, 5},
//                        {2, 6, 0, 3, 2, 7, 6, 1, 5, 6, 1, 5},
//                        {2, 6, 0, 3, 2, 7, 6, 1, 5, 6, 1, 5},
//                        {7, 0, 11, 2, 3, -2, 7, 1, 5, 6, 1, 5}},
//
//                {       {1, 11, 3, 5, -1, 2, 3, 1, 5, 6, 1, 5},
//                        {4, 6, 0, 6, 1, 7, 7, 1, 5, 6, 1, 5},
//                        {0, 0, 1, 5, 3, 26, 1, 1, 5, 6, 1, 5},
//                        {2, 6, 0, 3, 2, 7, 6, 1, 5, 6, 1, 5},
//                        {2, 6, 0, 3, 2, 7, 6, 1, 5, 6, 1, 5},
//                        {2, 6, 0, 3, 2, 7, 6, 1, 5, 6, 1, 5},
//                        {7, 0, 11, 2, 3, -2, 13, 1, 5, 6, 1, 5}},
//
//                {       {2, 2, 5, 0, -2, 4, 3, 1, 5, 6, 1, 5},
//                        {2, 5, 0, -3, 4, 6, 6, 1, 5, 6, 1, 5},
//                        {2, 5, 0, -3, 4, 6, 6, 1, 5, 6, 1, 5},
//                        {2, 5, 0, -3, 4, 6, 6, 1, 5, 6, 1, 5},
//                        {2, 5, 0, -3, 4, 6, 6, 1, 5, 6, 1, 5},
//                        {3, -8, 1, 5, 3, 2, 1, 1, 5, 6, 1, 5},
//                        {7, 0, 6, 2, 5, -2, 11, 1, 5, 6, 1, 5}},
//        };
//        double[][][] kernel = {
//                {       {1, 0, 1},
//                        {1, 0, 1},
//                        {1, 0, 1}},
//                {       {1, 0, 1},
//                        {0, -1, 0},
//                        {1, 0, 1}},
//                {       {1, 0, 0},
//                        {-1, 1, 0},
//                        {1, 1, 0}},
//        };
//
//        Matrix3D wrapping3D = new Matrix3D(wrapping);
//        Matrix3D kernel3D = new Matrix3D(kernel);
//
//        int stride = 2;
//
//        Matrix3D wrapped3D = ConvolutionUtils.wrap(wrapping3D, kernel3D, stride);
//
//        double[][][] wrappedResult = wrapped3D.getMatrix3d();
//
//        //Visualization
//        for (int i = 0; i < wrappedResult.length; i++) {
//            RealMatrix matrix = MatrixUtils.createInstance(wrappedResult[i]);
//            System.out.println(i + "-АЯ ГЛУБИНА");
//            MatrixUtils.printMatrixTest(matrix);
//        }
//    }

}
