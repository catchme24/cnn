package util.task;

import util.ConvolutionUtils;
import util.model.Matrix3D;

import java.util.concurrent.Callable;

public class MaxPooling3D implements Callable<Matrix3D> {

    private Matrix3D result;

    private int indexStart;
    private int indexEnd;
    private Matrix3D input;
    private int size;
    private int stride;

    public MaxPooling3D(Matrix3D result,
                        int indexStart,
                        int indexEnd,
                        Matrix3D input,
                        int size,
                        int stride) {

        this.result = result;
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
        this.input = input;
        this.size = size;
        this.stride = stride;
    }

    @Override
    public Matrix3D call() throws Exception {

        double[][][] result3d = result.getMatrix3d();
        double[][][] input3d = input.getMatrix3d();

        for (int i = indexStart; i <= indexEnd; i++){
            result3d[i] = ConvolutionUtils.maxPooling2D(input3d[i], size, stride);
        }

        return result;
    }
}
