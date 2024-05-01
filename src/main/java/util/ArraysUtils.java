package util;

public class ArraysUtils {

    public static double[][] getSubArray(double[][] matrix, int rowStart, int rowEnd, int columnStart, int columnEnd) {
        double[][] reuslt = new double[rowEnd - rowStart + 1][columnEnd - columnStart + 1];

        for (int i = rowStart, j = 0; i <= rowEnd; i++, j++) {
            System.arraycopy(matrix[i], columnStart, reuslt[j], 0, columnEnd - columnStart + 1);
        }
        return reuslt;
    }

    public static double[][] multiplyByElement(double[][] matrix1, double[][] matrix2) {
        double[][] reuslt = new double[matrix1.length][matrix1[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                reuslt[i][j] = matrix1[i][j] * matrix2[i][j];
            }
        }
        return reuslt;
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
}
