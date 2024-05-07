package util.task;

import function.activation.ActivationFunc;
import function.activation.ReLu;
import org.junit.jupiter.api.Test;
import util.ActivationParallelUtils;
import util.ConvolutionParallelUtils;
import util.Matrix3DUtils;
import util.MatrixUtils;
import util.model.Matrix3D;

import java.io.IOException;

public class ActivationTest {

    @Test
    public void testActivationParallelResultCheck() throws IOException {
        Matrix3D inputParallel = new Matrix3D(3,3, 3);
        Matrix3DUtils.fillRandom(inputParallel);

        Matrix3D input = inputParallel.copy();

        ReLu relu = new ReLu();

        long start = System.currentTimeMillis();

        ActivationParallelUtils.activationParallelMutable(relu, inputParallel, 2);
        relu.calculate(input);

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(Double.valueOf(end - start) / 1000);

        System.out.println("Норм");
        MatrixUtils.printMatrix3D(input);
        System.out.println("Параллельно");
        MatrixUtils.printMatrix3D(inputParallel);
    }

    @Test
    public void testActivationParallel() throws IOException {
        Matrix3D input = new Matrix3D(32,30, 30);
        Matrix3DUtils.fillRandom(input);

        ActivationFunc relu = new ReLu();

        long start = System.currentTimeMillis();

        int countOfConv = 50000;
        for (int i = 0; i < countOfConv; i++) {
            ActivationParallelUtils.activationParallelMutable(relu, input, 16);
//            relu.calculate(input);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(Double.valueOf(end - start) / 1000);
    }
}
