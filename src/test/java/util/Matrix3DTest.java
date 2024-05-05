package util;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.model.Matrix3D;

import java.util.Arrays;

public class Matrix3DTest {

    @Test
    public void testCreate2D() {
        //wrapping - 4x7
        //kernel - 3x3
        double[][] wrapping = { {1, 2, 3, 0, -1, 8, 3},
                {2, 6, 0, 3, 2, 7, 6},
                {0, 8, 1, 5, 3, 6, 1},
                {7, 0, 11, 2, 3, -2, 7}
        };
        double[][] kernel = {   {1, 0, 1},
                {-1, 1, 0},
                {1, 1, 0}
        };

        Matrix3D wrapping2D = new Matrix3D(wrapping);
        Matrix3D kernel2D = new Matrix3D(kernel);

        Assertions.assertEquals(wrapping2D.getMatrix3d().length, 1);
        Assertions.assertEquals(wrapping2D.getDimension(), 2);
        Assertions.assertEquals(kernel2D.getMatrix3d().length, 1);
        Assertions.assertEquals(kernel2D.getDimension(), 2);

        RealMatrix wrappingMatrix = MatrixUtils.createInstance(wrapping2D.getMatrix3d()[0]);
        RealMatrix kernelMatrix = MatrixUtils.createInstance(kernel2D.getMatrix3d()[0]);

        //Visualization
        MatrixUtils.printMatrixTest(wrappingMatrix);
        MatrixUtils.printMatrixTest(kernelMatrix);

        Assertions.assertTrue(Arrays.deepEquals(wrappingMatrix.getData(), wrapping));
        Assertions.assertTrue(Arrays.deepEquals(kernelMatrix.getData(), kernel));
    }

    @Test
    public void testCreate3D() {
        //wrapping - 3x4x7
        //kernel - 3x3x3
        double[][][] wrapping = {
                                    {   {1, 2, 3, 0, -1, 8, 3},
                                        {2, 6, 0, 3, 2, 7, 6},
                                        {0, 8, 1, 5, 3, 6, 1},
                                        {7, 0, 11, 2, 3, -2, 7}},

                                    {   {1, 2, 3, 0, -1, 8, 3},
                                        {2, 6, 0, 3, 2, 7, 6},
                                        {0, 8, 1, 5, 3, 6, 1},
                                        {7, 0, 11, 2, 3, -2, 7}},

                                    {   {1, 2, 3, 0, -1, 8, 3},
                                        {2, 6, 0, 3, 2, 7, 6},
                                        {0, 8, 1, 5, 3, 6, 1},
                                        {7, 0, 11, 2, 3, -2, 7}},
        };
        double[][][] kernel = {
                                    {   {1, 0, 1},
                                        {-1, 1, 0},
                                        {1, 1, 0}},
                                    {   {1, 0, 1},
                                        {-1, 1, 0},
                                        {1, 1, 0}},
                                    {   {1, 0, 1},
                                        {-1, 1, 0},
                                        {1, 1, 0}},
        };

        Matrix3D wrapping3D = new Matrix3D(wrapping);
        Matrix3D kernel3D = new Matrix3D(kernel);

        Assertions.assertEquals(wrapping3D.getMatrix3d().length, 3);
        Assertions.assertEquals(wrapping3D.getDimension(), 3);
        Assertions.assertEquals(kernel3D.getMatrix3d().length, 3);
        Assertions.assertEquals(kernel3D.getDimension(), 3);

        //Visualization
        for (int i = 0; i < wrapping3D.getMatrix3d().length; i++) {
            RealMatrix wrappingMatrix = MatrixUtils.createInstance(wrapping3D.getMatrix3d()[i]);
            MatrixUtils.printMatrixTest(wrappingMatrix);
        }
        for (int i = 0; i < wrapping3D.getMatrix3d().length; i++) {
            RealMatrix kernelMatrix = MatrixUtils.createInstance(kernel3D.getMatrix3d()[i]);
            MatrixUtils.printMatrixTest(kernelMatrix);
        }

        Assertions.assertTrue(Arrays.deepEquals(wrapping3D.getMatrix3d(), wrapping));
        Assertions.assertTrue(Arrays.deepEquals(kernel3D.getMatrix3d(), kernel));
    }

    @Test
    public void testArray() {

        double[] array = new double[10];
        array[0] = 1;
        array[1] = 2;
        array[2] = 3;

        System.out.println(Arrays.toString(array));
    }


}
