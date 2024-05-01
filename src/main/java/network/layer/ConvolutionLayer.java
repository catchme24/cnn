package network.layer;

import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import util.ConvolutionUtils;
import util.Matrix3D;
import util.MatrixUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConvolutionLayer implements Layer3D {

    private Matrix3D preActivation;

    private Matrix3D postActivation;

    private Layer3D prev;

    private Dimension inputDimension;

    private Dimension outputDimension;

    private Matrix3D[] kernels;

    private double[] biases;

    public ConvolutionLayer(int kernelsCount, int kernelSize, int stride) {
        if (kernelSize <= 0) {
            throw new NetworkConfigException("Kernel size cannot be 0 or less!");
        }
        if (kernelsCount <= 0) {
            throw new NetworkConfigException("Kernels count cannot be 0 or less!");
        }
        this.outputDimension = new Dimension(kernelsCount, 0, 0, stride, kernelSize, kernelSize);
        this.kernels = new Matrix3D[kernelsCount];
        this.biases = new double[kernelsCount];
    }

    public ConvolutionLayer(int kernelsCount, int kernelSize, int stride, Dimension inputDimension) {
        if (kernelSize <= 0) {
            throw new NetworkConfigException("Kernel size cannot be 0 or less!");
        }
        if (kernelsCount <= 0) {
            throw new NetworkConfigException("Kernels count cannot be 0 or less!");
        }
        this.inputDimension = inputDimension;
        this.inputDimension.setHeightKernel(kernelSize);
        this.inputDimension.setWidthKernel(kernelSize);
        this.inputDimension.setStride(stride);
        int calcHeight = (inputDimension.getHeightTens() - kernelSize)/stride + 1;
        int calcWidth = (inputDimension.getWidthTens() - kernelSize)/stride + 1;
        this.outputDimension = new Dimension(kernelsCount, calcHeight, calcWidth);
        this.outputDimension.setHeightKernel(kernelSize);
        this.outputDimension.setWidthKernel(kernelSize);
        this.outputDimension.setStride(stride);
        this.kernels = new Matrix3D[kernelsCount];
        this.biases = new double[kernelsCount];
//        for (int i = 0; i < kernelsCount; i++) {
//            kernels.set(i, MatrixUtils.fillRandom(new Matrix3D(this.outputDimension.getChannel(),
//                                                                    kernelSize,
//                                                                    kernelSize)));
//        }
        for (int i = 0; i < kernelsCount; i++) {
            kernels[i] = MatrixUtils.fillRandom(new Matrix3D(this.outputDimension.getChannel(),
                                                                    kernelSize,
                                                                    kernelSize));
            biases[i] = 2 * Math.random() - 1;
        }
    }

    @Override
    public void setPrevious(Layer3D prev) {
        if (prev == null) return;

        Dimension outputDimensionOfPrevLayer = prev.getSize();
        inputDimension = new Dimension( outputDimensionOfPrevLayer.getChannel(),
                                        outputDimensionOfPrevLayer.getHeightTens(),
                                        outputDimensionOfPrevLayer.getWidthTens(),
                                        outputDimensionOfPrevLayer.getStride(),
                                        outputDimensionOfPrevLayer.getHeightKernel(),
                                        outputDimensionOfPrevLayer.getWidthKernel());

        int calcHeight = (inputDimension.getHeightTens() - outputDimension.getHeightKernel())/outputDimension.getStride() + 1;
        int calcWidth = (inputDimension.getWidthTens() - outputDimension.getWidthKernel())/outputDimension.getStride() + 1;

//        this.outputDimension.setChannel(this.kernels.size());
        this.outputDimension.setHeightTens(calcHeight);
        this.outputDimension.setWidthTens(calcWidth);

        log.debug("Convolution layer: {} prev size", inputDimension);
        log.debug("Convolution layer: {} size", outputDimension);

//        for (int i = 0; i < this.kernels.size(); i++) {
//            kernels.set(i, MatrixUtils.fillRandom(new Matrix3D(this.inputDimension.getChannel(),
//                                                                            this.outputDimension.getHeightKernel(),
//                                                                            this.outputDimension.getWidthKernel())));
//        }
        for (int i = 0; i < this.outputDimension.getChannel(); i++) {
            kernels[i] = MatrixUtils.fillRandom(new Matrix3D(this.inputDimension.getChannel(),
                                                                        this.outputDimension.getHeightKernel(),
                                                                        this.outputDimension.getWidthKernel()));
            biases[i] = 2 * Math.random() - 1;
        }

    }

    @Override
    public Matrix3D propogateForward(Matrix3D inputTensor) {
        preActivation = inputTensor.copy();
        Matrix3D result = ConvolutionUtils.convolution(inputTensor, kernels, biases, outputDimension.getStride());
        postActivation = result.copy();
        return result;
    }


    @Override
    public void correctWeights(double learnRate) {

    }

    @Override
    public Matrix3D propogateBackward(Matrix3D some) {
        return some;
    }

    @Override
    public Dimension getSize() {
        System.out.println("INPUT" + inputDimension);
        System.out.println("OUTPUT" + outputDimension);
        return outputDimension;
    }
}
