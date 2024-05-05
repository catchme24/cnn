package util;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.model.Matrix3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MatrixUtilsTest {

    @Test
    public void testGetGroundTruth() {
        double classNumber = 2.0;
        int classesCount = 10;

        RealMatrix result = MatrixUtils.getGroundTruth(classNumber, classesCount);
        MatrixUtils.printMatrixTest(result);
    }

    @Test
    public void testFillDropout() {
        double dropout = 0.5;
        int rowsCount = 500;

        RealMatrix dropoutVector = MatrixUtils.createVectorWithSameValue(rowsCount, 1);

//        MatrixUtils.printMatrixTest(dropoutVector);

        RealMatrix result = MatrixUtils.fillDropout(dropoutVector, dropout);
        MatrixUtils.printMatrixTest(result);
    }

    @Test
    public void testGetDataFrame() throws IOException {

        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));

        int w = image.getWidth();
        int h = image.getHeight();

        System.out.println(w + " " + h);

        int[] dataBuffInt = image.getRGB(0, 0, w, h, null, 0, w);
        Color c = new Color(dataBuffInt[512 + 15]);

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        double[][][] dataFrameArray = dataFrame.getMatrix3d();

        for (int i = 0; i < dataFrameArray.length; i++) {
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            RealMatrix matrix = MatrixUtils.createInstance(dataFrameArray[i]);
            MatrixUtils.printMatrixTest(matrix);
        }
    }

    @Test
    public void testGetDataFrames() throws IOException {
        String path = "D:\\kek";

        List<Matrix3D> dataFrames = MatrixUtils.getDataFrames(path);

        Assertions.assertEquals(dataFrames.size(), 12);

        for (Matrix3D dataFrame: dataFrames) {
            double[][][] dataFrameArray = dataFrame.getMatrix3d();
            for (int i = 0; i < dataFrameArray.length; i++) {
                System.out.println("-------------------------");
                System.out.println("-------------------------");
                System.out.println("-------------------------");
                RealMatrix matrix = MatrixUtils.createInstance(dataFrameArray[i]);
                MatrixUtils.printMatrixTest(matrix);
            }
        }
    }

    @Test
    public void getRandomNumber() {
        System.out.println(MatrixUtils.getRandomNumber(10, 100));
        System.out.println(MatrixUtils.getRandomNumber(10, 100));
        System.out.println(MatrixUtils.getRandomNumber(10, 100));
        System.out.println(MatrixUtils.getRandomNumber(10, 100));
    }

}
