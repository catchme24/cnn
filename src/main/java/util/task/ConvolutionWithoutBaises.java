package util.task;

import util.ConvolutionUtils;
import util.model.Matrix3D;

import java.util.concurrent.Callable;

public class ConvolutionWithoutBaises implements Callable<Matrix3D> {

    private Matrix3D result;
    private int indexStart;
    private int indexEnd;
    private Matrix3D input;
    private Matrix3D[] kernels;
    private int stride;

    public ConvolutionWithoutBaises(Matrix3D result,
                                    int indexStart,
                                    int indexEnd,
                                    Matrix3D input,
                                    Matrix3D[] kernels,
                                    int stride) {

        this.result = result;
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
        this.input = input;
        this.kernels = kernels;
        this.stride = stride;
    }

    @Override
    public Matrix3D call() throws Exception {
        for(int i = indexStart; i <= indexEnd; i++){
            double[][] current = ConvolutionUtils.convolution3DWithoutBiases(input, kernels[i], stride);
            result.setMatrix2d(current, i);
        }

        return result;
    }
}
