package util;

import data.DatasetItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
public class MatrixUtils {

    private static final Array2DRowRealMatrix worker = new Array2DRowRealMatrix();

    private static final Random random = new Random();

    private MatrixUtils() {};

    public static void setSeed(long seed) {
        random.setSeed(seed);
    }

    /*
    Заполняет матрицу рандомными значениями в диапазоне [0, 1]
     */
    public static RealMatrix fillRandom(RealMatrix matrix) {
        for(int i = 0; i < matrix.getRowDimension(); i++) {
            for(int j = 0; j < matrix.getColumnDimension(); j++) {
//                matrix.setEntry(i, j, Math.random() * 2 - 1);
                matrix.setEntry(i, j, random.nextDouble() * 2 - 1);
            }
        }
        return matrix;
    }

    public static RealMatrix fillDropout(RealMatrix matrix, double dropout) {

        int quantity0 = Long.valueOf(Math.round(dropout * matrix.getRowDimension())).intValue();
        int quantity1 = matrix.getRowDimension() - quantity0;

        int i = 0;

        while (i < matrix.getRowDimension()) {
            int count0 = 0;
            int count1 = 0;
            if (count0 != quantity0 || count1 != quantity1) {
                int value = binary();
                if (value == 0) {
                    count0++;
                    matrix.setEntry(i, 0, value);
                } else {
                    count1++;
                    matrix.setEntry(i, 0, value);
                }
            } else {
                if (count0 == quantity0)
                    matrix.setEntry(i, 0, 1);
                if (count1 == quantity1)
                    matrix.setEntry(i, 0, 0);
            }
            i++;
        }
        return matrix;
    }

    public static int binary() {
        double v = random.nextDouble() * 2 - 1;
        return v > 0 ? 1 : 0;
    }


    public static Matrix3D fillHeNormal(Matrix3D matrix) {
        double[][][] array3D = matrix.getMatrix3d();
        for(int i = 0; i < array3D.length; i++) {
            for(int j = 0; j < array3D[0].length; j++) {
                for(int k = 0; k < array3D[0][0].length; k++) {
                    // TO-DO HE NORMAL
                }
            }
        }
        return matrix;
    }

    public static Matrix3D fillRandom(Matrix3D matrix) {
        double[][][] array3D = matrix.getMatrix3d();
        for(int i = 0; i < array3D.length; i++) {
            for(int j = 0; j < array3D[0].length; j++) {
                for(int k = 0; k < array3D[0][0].length; k++) {
                    array3D[i][j][k] = random.nextDouble() * 2 - 1;
                }
            }
        }
        return matrix;
    }
    public static void printMatrix3D(Matrix3D matrix) {
        double[][][] array3D = matrix.getMatrix3d();
        for(int i = 0; i < array3D.length; i++) {
            for(int j = 0; j < array3D[0].length; j++) {
                System.out.println(Arrays.toString(array3D[i][j]));
            }
            System.out.println("----------------------------------------");
        }

    }

    public static RealMatrix createInstance(int row, int column) {
        return worker.createMatrix(row, column);
    }

    public static RealMatrix createInstance(double[][] array2d) {
        RealMatrix matrix  = worker.createMatrix(array2d.length, array2d[0].length);
        matrix.setSubMatrix(array2d, 0, 0);
        return matrix;
    }

    public static RealMatrix createRowFromArray(double[] row) {
        RealMatrix matrix = worker.createMatrix(1, row.length);
        matrix.setRow(0, row);
        return matrix;
    }

    public static RealMatrix createInstanceFromRow(double[] row) {
        RealMatrix matrix = worker.createMatrix(1, row.length);
        matrix.setRow(0, row);
        return matrix;
    }

    public static void printMatrix(RealMatrix matrix) {
        for (int g = 0; g < matrix.getRowDimension(); g++) {
            log.debug(Arrays.toString(matrix.getRow(g)));
        }
    }

    public static void printMatrixTest(RealMatrix matrix) {
        for (int g = 0; g < matrix.getRowDimension(); g++) {
            log.info(Arrays.toString(matrix.getRow(g)));
        }
    }

    public static void printDatasetItemsTest(List<DatasetItem> items) {
        for (DatasetItem item : items) {
            log.info(Arrays.toString(item.getAttributes()));
        }
    }

    public static RealMatrix createEmptyVector(int row) {
        return worker.createMatrix(row, 1);
    }

    public static RealMatrix getGroundTruth(double classNumber, int classesCount) {
        RealMatrix matrix = worker.createMatrix(classesCount, 1);
        matrix.setEntry(Double.valueOf(classNumber).intValue() - 1, 0, 1.0);
        return matrix;
    }

    public static Matrix3D getDataFrame(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        double[][] red = new double[h][w];
        double[][] green = new double[h][w];
        double[][] blue = new double[h][w];

        Matrix3D result = new Matrix3D(3, h, w);

        int[] dataBuffInt = image.getRGB(0, 0, w, h, null, 0, w);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color c = new Color(dataBuffInt[i * w + j]);
                red[i][j] =  c.getRed() / 255.0;
                green[i][j] = c.getGreen() / 255.0;
                blue[i][j] = c.getBlue() / 255.0;
            }
        }
        result.setMatrix2d(red, 0);
        result.setMatrix2d(green, 1);
        result.setMatrix2d(blue, 2);

        return result;
    }
}
