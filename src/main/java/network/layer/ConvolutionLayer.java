package network.layer;

import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import util.ConvolutionParallelUtils;
import util.ConvolutionUtils;
import util.model.Matrix3D;
import util.MatrixUtils;

@Slf4j
public class ConvolutionLayer implements Layer3D {

    private Matrix3D preActivation;

    private Matrix3D postActivation;

    private Layer previousLayer;

    private Dimension inputDimension;

    private Dimension outputDimension;

    private Matrix3D[] kernels;

    private double[] biases;

    private Matrix3D[] kernelsGradient;

    private double[] biasesGradient;

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
//        for (int i = 0; i < kernelsCount; i++) {
//            kernels[i] = MatrixUtils.fillHeNormal(new Matrix3D(this.inputDimension.getChannel(),
//                                                                    kernelSize,
//                                                                    kernelSize));
//        }
//        MatrixUtils.fillHeNormal(biases);
    }

    @Override
    public void unchain() {
        previousLayer = null;
    }

    @Override
    public void initWeight() {
        //        for (int i = 0; i < this.kernels.size(); i++) {
//            kernels.set(i, MatrixUtils.fillRandom(new Matrix3D(this.inputDimension.getChannel(),
//                                                                            this.outputDimension.getHeightKernel(),
//                                                                            this.outputDimension.getWidthKernel())));
//        }
        for (int i = 0; i < this.outputDimension.getChannel(); i++) {
            kernels[i] = MatrixUtils.fillHeNormal(new Matrix3D(this.inputDimension.getChannel(),
                    this.outputDimension.getHeightKernel(),
                    this.outputDimension.getWidthKernel()),
                    outputDimension.getHeightTens() * outputDimension.getWidthTens() * outputDimension.getChannel());
        }
        MatrixUtils.fillHeNormal(biases,
                outputDimension.getHeightTens() * outputDimension.getWidthTens() * outputDimension.getChannel());
    }

    @Override
    public void setPrevious(Layer previous) {
        if (previous == null) return;

        if (!(previous instanceof Layer3D)) {
            throw new NetworkConfigException("Prev layer for Convolution Layer must be child of Layer3D");
        }

        Dimension outputDimensionOfPrevLayer = previous.getSize();
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
    }

    @Override
    public Matrix3D propogateForward(Matrix3D inputTensor) {
//        System.out.println(outputDimension);
//        System.out.println("Кернелов");
//        System.out.println(kernels.length);
//        System.out.println("Размером");
//        System.out.println(kernels[0].getMatrix3d().length + "x" + kernels[0].getMatrix3d()[0].length + "x" + kernels[0].getMatrix3d()[0][0].length);

        preActivation = inputTensor.copy();
//        System.out.println("Forward: " + outputDimension);
//        System.out.println("Входной тензон: " + inputTensor.getMatrix3d().length + "x" + inputTensor.getMatrix3d()[0].length + "x" + inputTensor.getMatrix3d()[0][0].length);
//        System.out.println("Кернелы: " + kernels.length + "x" + kernels[0].getMatrix3d().length + "x" + kernels[0].getMatrix3d()[0].length + "x" + kernels[0].getMatrix3d()[0][0].length);
//        Matrix3D result = ConvolutionUtils.convolution(inputTensor, kernels, biases, outputDimension.getStride());


        Matrix3D result = ConvolutionParallelUtils.convolutionParallel(inputTensor, kernels, biases, outputDimension.getStride(), 12);


        postActivation = result.copy();
        return result;
    }

    @Override
    public Matrix3D propogateBackward(Matrix3D localGradient) {

//        System.out.println("Backward: " + outputDimension);
//        System.out.println("convolutionForBack: ");
//        System.out.println("Преактивация тензон: " + preActivation.getMatrix3d().length + "x" + preActivation.getMatrix3d()[0].length + "x" + preActivation.getMatrix3d()[0][0].length);
//        System.out.println("Локальный градиент: " + localGradient.getMatrix3d().length + "x" + localGradient.getMatrix3d()[0].length + "x" + localGradient.getMatrix3d()[0][0].length);
//        kernelsGradient = ConvolutionUtils.convolutionForBack(preActivation, localGradient, outputDimension.getStride());

        kernelsGradient = ConvolutionParallelUtils.convolutionForBackParallel(preActivation, localGradient, outputDimension.getStride(), 16);


        biasesGradient = ConvolutionUtils.sumForBiases(localGradient);

        Matrix3D[] swappedKernels = ConvolutionUtils.swapFilters(kernels);
        Matrix3D errorTensor;
        if(outputDimension.getStride() == 1){
            //Формула без Dilate(s-1)
            errorTensor = ConvolutionUtils.zeroPadding(localGradient, outputDimension.getHeightKernel() -1);
//            System.out.println("convolutionWithOutBaises: ");
//            System.out.println("Тензор ошибки: " + localGradient.getMatrix3d().length + "x" + localGradient.getMatrix3d()[0].length + "x" + localGradient.getMatrix3d()[0][0].length);
//            System.out.println("Свапнутые кернелы: " + swappedKernels.length + "x" + swappedKernels[0].getMatrix3d().length + "x" + swappedKernels[0].getMatrix3d()[0].length + "x" + swappedKernels[0].getMatrix3d()[0][0].length);
//            errorTensor = ConvolutionUtils.convolutionWithoutBiases(errorTensor, swappedKernels, 1);

            errorTensor = ConvolutionParallelUtils.convolutionWithoutBaisesParallel(errorTensor, swappedKernels, 1, 12);
        } else {
            //Формула c Dilate(s-1)
            errorTensor = ConvolutionUtils.dilate(localGradient, outputDimension.getStride() - 1);
            errorTensor = ConvolutionUtils.zeroPadding(errorTensor, outputDimension.getHeightKernel() -1);
//            errorTensor = ConvolutionUtils.convolutionWithoutBiases(errorTensor, swappedKernels, 1);

            errorTensor = ConvolutionParallelUtils.convolutionWithoutBaisesParallel(errorTensor, swappedKernels, 1, 12);
        }
        return errorTensor;
    }

    @Override
    public Object propogateForward(Object input) {
        return propogateForward((Matrix3D) input);
    }

    @Override
    public Object propogateBackward(Object input) {
        return propogateBackward((Matrix3D) input);
    }

    @Override
    public void correctWeights(double learnRate) {
        for (int i = 0; i < kernels.length; i++){
            MatrixUtils.subtract(kernels[i], kernelsGradient[i], learnRate);
        }
        for(int i = 0; i < biases.length; i++){
            biases[i] -= biasesGradient[i] * learnRate;
        };
    }

    @Override
    public Dimension getSize() {
        return outputDimension;
    }
}
