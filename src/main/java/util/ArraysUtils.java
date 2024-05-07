package util;

import util.model.Matrix3D;

public class ArraysUtils {

    public static void scalarMultiplyMutable(double[][] input, double scalar){
        for (int i = 0; i < input.length; i++){
            for (int j =0; j < input[0].length; j++){
                input[i][j] += scalar;
            }
        }
    }

    public static double[][] scalarMultiplyImmutable(double[][] input, double scalar){
        double[][] result = new double[input.length][input[0].length];
        for (int i = 0; i < result.length; i++){
            for (int j =0; j < result[0].length; j++){
                result[i][j] += scalar;
            }
        }
        return result;
    }

    public static void scalarAdditionMutable(double[][] input, double scalar){
        for (int i = 0; i < input.length; i++){
            for (int j =0; j < input[0].length; j++){
                input[i][j] += scalar;
            }
        }
    }

    public static double[][] scalarAdditionImmutable(double[][] input, double scalar){
        double[][] result = new double[input.length][input[0].length];
        for (int i = 0; i < result.length; i++){
            for (int j =0; j < result[0].length; j++){
                result[i][j] += scalar;
            }
        }
        return result;
    }

    public static void arrayAdditionMutable(double[][] input, double[][] addend){
        for (int i = 0; i < input.length; i++){
            for (int j =0; j < input[0].length; j++){
                input[i][j] = input[i][j] + addend[i][j];
            }
        }
    }

    public static double[][] arrayAdditionImmutable(double[][] input, double[][] addend){
        double[][] result = new double[input.length][input[0].length];
        for (int i = 0; i < result.length; i++){
            for (int j =0; j < result[0].length; j++){
                result[i][j] = result[i][j] + addend[i][j];
            }
        }
        return result;
    }

    public static double[][] getSubArrayImmutable(double[][] matrix, int rowStart, int rowEnd, int columnStart, int columnEnd) {
        double[][] reuslt = new double[rowEnd - rowStart + 1][columnEnd - columnStart + 1];

        for (int i = rowStart, j = 0; i <= rowEnd; i++, j++) {
            System.arraycopy(matrix[i], columnStart, reuslt[j], 0, columnEnd - columnStart + 1);
        }
        return reuslt;
    }

    public static void hadamardMutable(double[][] input, double[][] multiplier) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                input[i][j] = input[i][j] * multiplier[i][j];
            }
        }
    }

    public static double[][] hadamardImmutable(double[][] input, double[][] multiplier) {
        double[][] reuslt = new double[input.length][input[0].length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                reuslt[i][j] = input[i][j] * multiplier[i][j];
            }
        }
        return reuslt;
    }

    public static double[] hadamardImmutable(double[] input, double[] multiplier) {
        double[] reuslt = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            reuslt[i] = input[i] * multiplier[i];
        }
        return reuslt;
    }

    public static double[][] invertRowsAndColumnsImmutable(double[][] input) {
        double[][] result = new double[input.length][input[0].length];

        for (int i1 = result.length - 1, i2 = 0; i1 >= 0; i1--, i2++) {
            for (int j1 = result[0].length - 1, j2 = 0; j1 >= 0; j1--, j2++) {
                result[i1][j1] = input[i2][j2];
            }
        }

        return result;
    }

    public static double summOfArray(double[][] matrix1) {
        double summ = 0;
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                summ += matrix1[i][j];
            }
        }
        return summ;
    }

    public static double summOfArray(double[] input) {
        double summ = 0;
        for (int i = 0; i < input.length; i++) {
            summ += input[i];
        }
        return summ;
    }
}
