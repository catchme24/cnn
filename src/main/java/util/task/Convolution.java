package util.task;

import util.ConvolutionUtils;
import util.Matrix3D;

import java.util.concurrent.Callable;

public class Convolution implements Callable<Matrix3D> {

    private Matrix3D result;
    private int indexStart;
    private int indexEnd;
    private Matrix3D input;
    private Matrix3D[] kernels;
    private double[] biases;
    private int stride;

    public Convolution(Matrix3D result,
                       int indexStart,
                       int indexEnd,
                       Matrix3D input,
                       Matrix3D[] kernels,
                       double[] biases,
                       int stride) {

        this.result = result;
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
        this.input = input;
        this.kernels = kernels;
        this.biases = biases;
        this.stride = stride;
    }

    @Override
    public Matrix3D call() throws Exception {
        for(int i = indexStart; i <= indexEnd; i++){
            double[][] current = ConvolutionUtils.convolution3D(input, kernels[i], biases[i], stride);
            result.setMatrix2d(current, i);
        }

        return result;
    }
}
