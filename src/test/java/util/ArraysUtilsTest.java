package util;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ArraysUtilsTest {

    @Test
    public void testInnerGetSubMatrix() {
        double[][] matrix = {   {1, 2, 3, 0, -1, 8, 3},
                                {2, 6, 0, 3, 2, 7, 6},
                                {0, 8, 1, 5, 3, 6, 1},
                                {7, 0, 11, 2, 3, -2, 7}
        };


        double[][] subMatrix = ArraysUtils.getSubArrayImmutable(matrix, 1, 3, 2, 6);

        //Visualization
        RealMatrix forConsole = MatrixUtils.createInstance(subMatrix);
        MatrixUtils.printMatrixTest(forConsole);
    }
}
