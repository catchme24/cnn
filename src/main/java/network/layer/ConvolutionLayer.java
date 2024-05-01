package network.layer;

import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import util.ConvolutionUtils;
import util.Matrix3D;
import util.MatrixUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConvolutionLayer {

    private Matrix3D preActivation;

    private Matrix3D postActivation;

    private ConvolutionLayer prev;

    private Dimension inputDimension;

    private Dimension outputDimension;

    private List<Matrix3D> kernels;

    public ConvolutionLayer(int kernelsCount, int kernelSize, int stride) {
        if (kernelSize <= 0) {
            throw new NetworkConfigException("Kernel size cannot be 0 or less!");
        }
        if (kernelsCount <= 0) {
            throw new NetworkConfigException("Kernels count cannot be 0 or less!");
        }
        this.outputDimension = new Dimension(kernelsCount, 0, 0, stride, kernelSize, kernelSize);
        this.kernels = new ArrayList<>(kernelsCount);
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
        this.kernels = new ArrayList<>(kernelsCount);
//        for (int i = 0; i < kernelsCount; i++) {
//            kernels.set(i, MatrixUtils.fillRandom(new Matrix3D(this.outputDimension.getChannel(),
//                                                                    kernelSize,
//                                                                    kernelSize)));
//        }
        for (int i = 0; i < kernelsCount; i++) {
            kernels.add(MatrixUtils.fillRandom(new Matrix3D(this.outputDimension.getChannel(),
                                                                    kernelSize,
                                                                    kernelSize)));
        }
    }

    public void setPrevious(ConvolutionLayer prev) {
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
            kernels.add(MatrixUtils.fillRandom(new Matrix3D(this.inputDimension.getChannel(),
                                                                        this.outputDimension.getHeightKernel(),
                                                                        this.outputDimension.getWidthKernel())));
        }

    }

    public Matrix3D propogateForward(Matrix3D some) {
        Matrix3D result = new Matrix3D(outputDimension.getChannel(), outputDimension.getHeightTens(), outputDimension.getWidthTens());

        for (int i = 0; i < kernels.size(); i++) {
            Matrix3D wrapped = ConvolutionUtils.wrap(some, kernels.get(0), outputDimension.getStride());
            result.setMatrix2d(array, i);
        }
        return some;
    }


    public void correctWeights(double learnRate) {

    }

    public Matrix3D propogateBackward(Matrix3D some) {
        return some;
    }

    public Dimension getSize() {
        System.out.println("INPUT" + inputDimension);
        System.out.println("OUTPUT" + outputDimension);
        return outputDimension;
    }
}
