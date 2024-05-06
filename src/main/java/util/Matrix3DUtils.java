package util;

import util.model.Matrix3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Matrix3DUtils {

    private static final Random random = new Random();

    public static Matrix3D fillRandom(Matrix3D matrix) {
        double[][][] array3D = matrix.getMatrix3d();
        for(int i = 0; i < array3D.length; i++) {
            for(int j = 0; j < array3D[0].length; j++) {
                for(int k = 0; k < array3D[0][0].length; k++) {
                    array3D[i][j][k] = random.nextGaussian();
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

    public static double[] sumsOfEachChannelImmutable(Matrix3D input) {
        double[][][] input3D = input.getMatrix3d();
        double[] result = new double[input3D.length];
        for(int i = 0; i < input3D.length; i++) {
            double sum = 0;
            for(int j = 0; j < input3D[0].length; j++) {
                for(int k = 0; k < input3D[0][0].length; k++) {
                    sum += input3D[i][j][k];
                }
            }
            result[i] = sum;
        }
        return result;
    }
}
