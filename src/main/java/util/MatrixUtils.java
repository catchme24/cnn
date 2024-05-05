package util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import util.model.Matrix3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Slf4j
public class MatrixUtils {

    private static final Array2DRowRealMatrix worker = new Array2DRowRealMatrix();

    private static final Random random = new Random();

    private MatrixUtils() {};

    public static void setSeed(long seed) {
        random.setSeed(seed);
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

    public static Matrix3D hadamard(Matrix3D input, Matrix3D multiplier) {
        double[][][] input3D = input.getMatrix3d();
        double[][][] multiplier3D = multiplier.getMatrix3d();
        for(int i = 0; i < input3D.length; i++) {
            for(int j = 0; j < input3D[0].length; j++) {
                for(int k = 0; k < input3D[0][0].length; k++) {
                    input3D[i][j][k] *= multiplier3D[i][j][k];
                }
            }
        }
        return input;
    }

    public static Matrix3D subtract(Matrix3D input, Matrix3D subtrahend, double learningRate) {
        double[][][] input3D = input.getMatrix3d();
        double[][][] subtrahend3D = subtrahend.getMatrix3d();
        for(int i = 0; i < input3D.length; i++) {
            for(int j = 0; j < input3D[0].length; j++) {
                for(int k = 0; k < input3D[0][0].length; k++) {
                    input3D[i][j][k] -= subtrahend3D[i][j][k] * learningRate;
                }
            }
        }
        return input;
    }

    public static Matrix3D fillHeNormal(Matrix3D matrix, int count) {
        double[][][] array3D = matrix.getMatrix3d();
        double stdDev = Math.sqrt(2.0 / count);
        for(int i = 0; i < array3D.length; i++) {
            for(int j = 0; j < array3D[0].length; j++) {
                for(int k = 0; k < array3D[0][0].length; k++) {
                    array3D[i][j][k] = random.nextGaussian() * stdDev;
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

    public static List<Matrix3D> getDataFrames(String path) throws IOException {
        List<Matrix3D> inputData = new ArrayList<>();
        Iterator<String> iterator = FileSystemUtils.instance().getIterator(path);
        while (iterator.hasNext()) {
            String pathPicture = iterator.next();
            System.out.println("reading: " + pathPicture);
            BufferedImage image = ImageIO.read(new File(pathPicture));
            inputData.add(getDataFrame(image));
        }
        return inputData;
    }

    public static double[] fillHeNormal(double [] input, int count) {
        double stdDev = Math.sqrt(2.0 / count);
        for(int i = 0; i < input.length; i++) {
            input[i] = random.nextGaussian() * stdDev;
        }
        return input;
    }

    public static RealMatrix fillHeNormal(RealMatrix matrix, int count) {
        double stdDev = Math.sqrt(2.0 / count);
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                matrix.setEntry(i, j, random.nextGaussian() * stdDev);
            }
        }
        return matrix;
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

    public static RealMatrix createEmptyVector(int row) {
        return worker.createMatrix(row, 1);
    }

    public static RealMatrix createVectorWithSameValue(int row, double value) {
        return worker.createMatrix(row, 1).scalarAdd(value);
    }

    public static RealMatrix getGroundTruth(double classNumber, int classesCount) {
        RealMatrix matrix = worker.createMatrix(classesCount, 1);
        matrix.setEntry(Double.valueOf(classNumber).intValue() - 1, 0, 1.0);
        return matrix;
    }

    public static RealMatrix createOneHotEncoding(double labelNumber, int labelsCount) {
        RealMatrix matrix = worker.createMatrix(labelsCount, 1);
        matrix.setEntry(Double.valueOf(labelNumber).intValue() - 1, 0, 1.0);
        return matrix;
    }

    public static double[] fillRandom(double [] input) {
        for(int i = 0; i < input.length; i++) {
            input[i] = random.nextDouble() * 2 - 1;
        }
        return input;
    }

    public static RealMatrix fillRandom(RealMatrix matrix) {
        for(int i = 0; i < matrix.getRowDimension(); i++) {
            for(int j = 0; j < matrix.getColumnDimension(); j++) {
                matrix.setEntry(i, j, random.nextDouble() * 2 - 1);
            }
        }
        return matrix;
    }

    public static RealMatrix fillDropout(RealMatrix matrix, double dropout) {
        int quantity = Long.valueOf(Math.round(dropout * matrix.getRowDimension())).intValue();

        int countOfZeroes = 0;
        while (countOfZeroes < quantity) {
            int indexOfZero = getRandomNumber(0, matrix.getRowDimension() - 1);
            if (matrix.getEntry(indexOfZero, 0) != 0) {
                matrix.setEntry(indexOfZero, 0, 0);
                countOfZeroes++;
            }
        }

        return matrix;
    }

    public static int getRandomNumber(int from, int to) {
        return from + random.nextInt(to - from + 1);
    }
}
