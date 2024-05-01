package util;

import java.util.Arrays;

import static util.ArraysUtils.*;

public class ConvolutionUtils {

    public static Matrix3D wrap(Matrix3D input, Matrix3D kernel, int stride) {
        double[][][] matrix3d = input.getMatrix3d();
        double[][][] kernelArray = kernel.getMatrix3d();

        Matrix3D result = new Matrix3D(   matrix3d.length,
                                        (matrix3d[0].length - kernelArray[0].length)/stride + 1,
                                        (matrix3d[0][0].length - kernelArray[0][0].length)/stride + 1);

        for (int i = 0; i < matrix3d.length; i++) {
            double[][] wrapped = wrap2d(matrix3d[i], kernelArray[i], stride);
            result.setMatrix2d(wrapped, i);
        }

        return result;
    }

    public static double[][] wrap2d(double[][] wrapping, double[][] kernel2d, int stride) {
        int rowSize = (wrapping.length - kernel2d.length)/stride + 1;
        int columnSize = (wrapping[0].length - kernel2d[0].length)/stride + 1;
        double[][] wrapResult = new double[rowSize][columnSize];

        for (int i = 0, wrapResultRow = 0; i < wrapping.length - kernel2d.length + 1; i += stride, wrapResultRow++) {
            for (int j = 0, wrapResultColumn = 0; j < wrapping[0].length - kernel2d[0].length + 1; j += stride, wrapResultColumn++) {
                double[][] doubles = multiplyByElement(getSubArray(wrapping,
                                i,
                                i + kernel2d.length - 1,
                                j,
                                j + kernel2d.length - 1),
                        kernel2d);

                wrapResult[wrapResultRow][wrapResultColumn] = summOfArray(doubles);
            }
        }

        return wrapResult;
    }

}
