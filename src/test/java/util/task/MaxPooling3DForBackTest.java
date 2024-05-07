package util.task;

import org.junit.jupiter.api.Test;
import util.ConvolutionParallelUtils;
import util.ConvolutionUtils;
import util.Matrix3DUtils;
import util.MatrixUtils;
import util.model.Matrix3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MaxPooling3DForBackTest {

    @Test
    public void maxPooling3DForBackParallelResultCheck() throws IOException {
        Matrix3D preActivation = new Matrix3D(10,8, 8);
        Matrix3D pooling = new Matrix3D(10,4, 4);

        Matrix3DUtils.fillRandom(preActivation);
        Matrix3DUtils.fillRandom(pooling);

        int stride = 2;
        int kernelSize = 2;

        Matrix3D result = null;
        Matrix3D resultParallel = null;

        long start = System.currentTimeMillis();

        int countOfConv = 1;
        for (int i = 0; i < countOfConv; i++) {
            resultParallel = ConvolutionParallelUtils.maxPooling3DForBackParallel(preActivation, pooling ,kernelSize , stride, 2);
            result = ConvolutionUtils.maxPooling3DForBack(preActivation, pooling, kernelSize, stride);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(Double.valueOf(end - start) / 1000);

        System.out.println("-------------------Результат обычного------------");
        MatrixUtils.printMatrix3D(result);
        System.out.println("-------------------Результат параллельного------------");
        MatrixUtils.printMatrix3D(resultParallel);
    }

    @Test
    public void testMaxPooling3DForBackParallel() throws IOException {
        Matrix3D preActivation = new Matrix3D(64,28, 28);
        Matrix3D pooling = new Matrix3D(64,14, 14);

        Matrix3DUtils.fillRandom(preActivation);
        Matrix3DUtils.fillRandom(pooling);

        int stride = 2;
        int kernelSize = 2;

        Matrix3D result = null;

        long start = System.currentTimeMillis();

        int countOfConv = 50000;
        for (int i = 0; i < countOfConv; i++) {
            result = ConvolutionParallelUtils.maxPooling3DForBackParallel(preActivation,pooling , kernelSize, stride, 4);
//            result = ConvolutionUtils.maxPooling3DForBack(preActivation, pooling, kernelSize, stride);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(Double.valueOf(end - start) / 1000);

        MatrixUtils.printMatrix3D(result);
    }

    @Test
    public void testMaxPooling3DForBackParallelForStolenArch() throws IOException {
        Matrix3D preActivation = new Matrix3D(32,28, 28);
        Matrix3D pooling = new Matrix3D(32,14, 14);

        Matrix3DUtils.fillRandom(preActivation);
        Matrix3DUtils.fillRandom(pooling);

        int stride = 2;
        int kernelSize = 2;

        Matrix3D result = null;

        long start = System.currentTimeMillis();

        int countOfConv = 50000;
        for (int i = 0; i < countOfConv; i++) {
            result = ConvolutionParallelUtils.maxPooling3DForBackParallel(preActivation,pooling , kernelSize, stride, 4);
//            result = ConvolutionUtils.maxPooling3DForBack(preActivation, pooling, kernelSize, stride);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(Double.valueOf(end - start) / 1000);

        MatrixUtils.printMatrix3D(result);
    }
}
