package util.task;

import util.ConvolutionUtils;
import util.model.Matrix3D;

import java.util.concurrent.Callable;

public class MaxPooling3DForBack implements Callable<Matrix3D> {

    private Matrix3D result;

    private int indexStart;
    private int indexEnd;
    private Matrix3D input;
    private Matrix3D pool;
    private int size;
    private int stride;

    public MaxPooling3DForBack(Matrix3D result,
                        int indexStart,
                        int indexEnd,
                        Matrix3D input,
                        Matrix3D pool,
                        int size,
                        int stride) {

        this.result = result;
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
        this.input = input;
        this.pool = pool;
        this.size = size;
        this.stride = stride;
    }

    @Override
    public Matrix3D call() throws Exception {

        double[][][] result3d = result.getMatrix3d();
        double[][][] input3d = input.getMatrix3d();
        double[][][] pool3d = input.getMatrix3d();

        for (int i = indexStart; i <= indexEnd;i++){
            result3d[i] =  ConvolutionUtils.maxPooling2DForBack(input3d[i], pool3d[i], 2, 2);
        }

        return result;
    }
}
