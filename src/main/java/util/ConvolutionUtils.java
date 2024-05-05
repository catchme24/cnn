package util;

import util.model.Matrix3D;

public class ConvolutionUtils {

    public static Matrix3D convolution(Matrix3D input, Matrix3D[] kernels, double[] biases, int stride) {
        int rowSize = (input.getMatrix3d()[0].length - kernels[0].getMatrix3d()[0].length) / stride + 1;
        int columnSize = (input.getMatrix3d()[0][0].length - kernels[0].getMatrix3d()[0][0].length) / stride + 1;
        Matrix3D result = new Matrix3D(kernels.length, rowSize, columnSize);

        for(int i = 0; i < kernels.length; i++){
            double[][] current = convolution3D(input, kernels[i], biases[i], stride);
            result.setMatrix2d(current, i);
        }

        return result;
    }
    public static double[][] convolution3D(Matrix3D input, Matrix3D kernel, double bias, int stride) {
        double[][][] matrix3d = input.getMatrix3d();
        double[][][] kernel3d = kernel.getMatrix3d();

        int rowSize = (matrix3d[0].length - kernel3d[0].length) / stride + 1;
        int columnSize = (matrix3d[0][0].length - kernel3d[0][0].length) / stride + 1;

        double[][] result = new double[rowSize][columnSize];

        for (int i = 0; i < matrix3d.length; i++) {
//            result = matrixAddition(result, convolution2D(matrix3d[i], kernel3d[i], stride));
            ArraysUtils.arrayAdditionMutable(result, convolution2DImmutable(matrix3d[i], kernel3d[i], stride));
        }
//        result = addBias(result, bias);
        ArraysUtils.scalarAdditionMutable(result, bias);
        return result;
    }
    public static double[][] convolution2DImmutable(double[][] input, double[][] kernel2d, int stride) {
        int rowSize = (input.length - kernel2d.length) / stride + 1;
        int columnSize = (input[0].length - kernel2d[0].length) / stride + 1;
        double[][] result = new double[rowSize][columnSize];
        int ii = 0;
        for (int i = 0; i < input.length - kernel2d.length + 1; i += stride) {
            int jj = 0;
            for (int j = 0; j < input[0].length - kernel2d.length + 1; j += stride) {
                double sum = 0;
                for (int k = 0; k < kernel2d.length; k++) {
                    for (int l = 0; l < kernel2d[k].length; l++) {
                        sum += input[i + k][j + l] * kernel2d[k][l];
                    }
                }
                result[ii][jj] = sum;
                jj++;
            }
            ii++;
        }
        return result;
    }
    public static Matrix3D convolutionWithoutBiases(Matrix3D input, Matrix3D[] kernels, int stride) {
        int rowSize = (input.getMatrix3d()[0].length - kernels[0].getMatrix3d()[0].length) / stride + 1;
        int columnSize = (input.getMatrix3d()[0][0].length - kernels[0].getMatrix3d()[0][0].length) / stride + 1;
        Matrix3D result = new Matrix3D(kernels.length, rowSize, columnSize);

        for(int i = 0; i < kernels.length; i++){
            double[][] current = convolution3DWithoutBiases(input, kernels[i], stride);
            result.setMatrix2d(current, i);
        }

        return result;
    }
    public static double[][] convolution3DWithoutBiases(Matrix3D input, Matrix3D kernel, int stride) {
        double[][][] matrix3d = input.getMatrix3d();
        double[][][] kernel3d = kernel.getMatrix3d();

        int rowSize = (matrix3d[0].length - kernel3d[0].length) / stride + 1;
        int columnSize = (matrix3d[0][0].length - kernel3d[0][0].length) / stride + 1;

        double[][] result = new double[rowSize][columnSize];

        for (int i = 0; i < matrix3d.length; i++) {
//            result = matrixAddition(result, convolution2D(matrix3d[i], kernel3d[i], stride));
            ArraysUtils.arrayAdditionMutable(result, convolution2DImmutable(matrix3d[i], kernel3d[i], stride));
        }
        return result;
    }
    public static Matrix3D[] convolutionForBack(Matrix3D input, Matrix3D kernel, int stride) {
        double[][][] kernel3d = kernel.getMatrix3d();
        Matrix3D[] result = new Matrix3D[kernel3d.length];
        for (int i = 0; i < kernel3d.length; i++) {
            result[i] = convolution3DForBack(input, kernel3d[i], stride);
        }
        return result;
    }
    public static Matrix3D convolution3DForBack(Matrix3D input, double[][] kernel, int stride) {
        double[][][] input3d = input.getMatrix3d();

        int rowSize = (input3d[0].length - kernel.length) / stride + 1;
        int columnSize = (input3d[0][0].length - kernel[0].length) / stride + 1;

        double[][][] result = new double[input3d.length][rowSize][columnSize];

        for (int i = 0; i < input3d.length; i++) {
            result[i] = convolution2DImmutable(input3d[i], kernel, stride);
        }

        return new Matrix3D(result);
    }
    public static Matrix3D maxPooling3D(Matrix3D input, int size, int stride){
        double[][][] input3d = input.getMatrix3d();
        double[][][] result = new double[input3d.length]
                                        [(input3d[0].length - size) / stride + 1]
                                        [(input3d[0][0].length - size) / stride + 1];

        for (int i = 0; i < input3d.length; i++){
            result[i] = maxPooling2D(input3d[i], size, stride);
        }
        return new Matrix3D(result);
    }
    public static double[][] maxPooling2D(double[][] input, int size, int stride){
        double [][] result = new double[(input.length - size) / stride + 1][(input[0].length - size) / stride + 1];
        for (int i = 0; i < result.length; i++){
            for (int j = 0; j < result[0].length; j++){
                double max = Double.NEGATIVE_INFINITY;
                for (int row = i * stride; row < i * stride + size; row++){
                    for(int column = j * stride; column < j * stride + size; column++){
                        max = Math.max(max, input[row][column]);
                    }
                }
                result[i][j] = max;
            }
        }
        return result;
    }
    public static Matrix3D maxPooling3DForBack(Matrix3D input, Matrix3D pool, int size, int stride){
        double[][][] input3d = input.getMatrix3d();
        double[][][] pool3d = pool.getMatrix3d();
        double[][][] result = new double[input3d.length][input3d[0].length][input3d[0][0].length];
        for (int i = 0; i < result.length;i++){
            result[i] =  maxPooling2DForBack(input3d[i], pool3d[i], 2, 2);
        }
        return new Matrix3D(result);
    }
    public static double[][] maxPooling2DForBack(double[][] input, double[][] pool, int size, int stride){
        double [][] result = new double[input.length][input[0].length];
        for (int i = 0; i < pool.length; i++){
            for (int j = 0; j < pool[0].length; j++){
                double max = Double.NEGATIVE_INFINITY;
                int iMax = 0;
                int jMax = 0;
                for (int row = i * stride; row < i * stride + size; row++){
                    for(int column = j * stride; column < j * stride + size; column++){
                        if (input[row][column] > max){
                            iMax = row;
                            jMax = column;
                            max = input[row][column];
                        }
                    }
                }
                result[iMax][jMax] = pool[i][j];
            }
        }
        return result;
    }
    public static Matrix3D zeroPadding(Matrix3D input, int count){
        double[][][] input3d = input.getMatrix3d();
        double[][][] result = new double[input3d.length][input3d[0].length + 2 * count][input3d[0][0].length + 2 * count];
        for (int i = 0; i < input3d.length; i++){
            for (int j = 0; j < input3d[0].length; j++){
                for (int k = 0; k < input3d[0][0].length; k++){
                    result[i][j + count][k + count] = input3d[i][j][k];
                }
            }
        }
        return new Matrix3D(result);
    }
    public static Matrix3D dilate(Matrix3D input, int count){
        double[][][] input3d = input.getMatrix3d();
        double[][][] result = new double[input3d.length]
                                        [input3d[0].length * (count + 1) - count]
                                        [input3d[0][0].length * (count + 1) - count];
        for (int i = 0; i < input3d.length; i++){
            for (int j = 0; j < input3d[0].length; j++){
                for (int k = 0; k < input3d[0][0].length; k++){
                    result[i][j * (count + 1)][k * (count + 1)] = input3d[i][j][k];
                }
            }
        }
        return new Matrix3D(result);
    }
    public static Matrix3D[] swapFilters(Matrix3D[] filters) {
        double[][][] filter3d = filters[0].getMatrix3d();
        Matrix3D[] swappedFilters = new Matrix3D[filters[0].getMatrix3d().length];

        for (int i = 0; i < swappedFilters.length; i++) {
            swappedFilters[i] = new Matrix3D(filters.length, filter3d[0].length, filter3d[0][0].length);
            for (int j = 0; j < filters.length; j++) {
                //равно вызову функции, которая развернет фильтр
//                swappedFilters[i].getMatrix3d()[j] = invertRowsAndColumns(filters[j].getMatrix3d()[i]);
                swappedFilters[i].getMatrix3d()[j] = ArraysUtils.invertRowsAndColumnsImmutable(filters[j].getMatrix3d()[i]);
            }
        }

        return swappedFilters;
    }
    public static double[] sumForBiases(Matrix3D input) {
        return Matrix3DUtils.sumsOfEachChannelImmutable(input);
    }

}
