package util;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Test;

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
        int rowsCount = 10;

        RealMatrix dropoutVector = MatrixUtils.createEmptyVector(rowsCount);

        RealMatrix result = MatrixUtils.fillDropout(dropoutVector, dropout);
        MatrixUtils.printMatrixTest(result);
    }
}
