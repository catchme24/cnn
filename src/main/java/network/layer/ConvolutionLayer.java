package network.layer;

import function.initializer.Initializer;
import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import optimizer.Optimizer;
import util.ConvolutionParallelUtils;
import util.ConvolutionUtils;
import util.Matrix3DUtils;
import util.model.Matrix3D;
import util.MatrixUtils;

@Slf4j
public class ConvolutionLayer implements Layer3D, LearningLayer {

    private Matrix3D preActivation;

    private Matrix3D postActivation;

    private Layer previousLayer;

    private Dimension inputDimension;

    private Dimension outputDimension;

    private Matrix3D[] kernels;

    private double[] biases;

    private Matrix3D[] kernelsGradient;

    private double[] biasesGradient;
    private Initializer initializer;

    public ConvolutionLayer(int kernelsCount, int kernelSize, int stride, Initializer initializer) {
        this(kernelsCount, kernelSize, stride);
        this.initializer = initializer;
    }

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


    public ConvolutionLayer(int kernelsCount, int kernelSize, int stride, Dimension inputDimension, Initializer initializer) {
       this(kernelsCount, kernelSize, stride, inputDimension);
       this.initializer = initializer;
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
        initializer = null;
    }

    @Override
    public void initWeightsAndBaises() {
        double countInput = inputDimension.getWidthTens() * inputDimension.getHeightTens() * inputDimension.getChannel();
        double countOutput = outputDimension.getWidthTens() * outputDimension.getHeightTens() * outputDimension.getChannel();

        initializer.setParams(countInput, countOutput);
        for (int i = 0; i < outputDimension.getChannel(); i++) {
            Matrix3D kernel = new Matrix3D(this.inputDimension.getChannel(),
                                                this.outputDimension.getHeightKernel(),
                                                this.outputDimension.getWidthKernel());
            kernels[i] = kernel;
            initializer.initializeMutable(kernels[i]);
        }
        initializer.initializeMutable(biases);
//        for (int i = 0; i < this.outputDimension.getChannel(); i++) {
//            kernels[i] = MatrixUtils.fillHeNormal(new Matrix3D(this.inputDimension.getChannel(),
//                    this.outputDimension.getHeightKernel(),
//                    this.outputDimension.getWidthKernel()),
//                    inputDimension.getWidthTens() * inputDimension.getHeightTens() * inputDimension.getChannel());
//        }
//        MatrixUtils.fillHeNormal(biases, inputDimension.getWidthTens() * inputDimension.getHeightTens() * inputDimension.getChannel());
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
        this.outputDimension.setHeightTens(calcHeight);
        this.outputDimension.setWidthTens(calcWidth);
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
        Matrix3D result = ConvolutionParallelUtils.convolutionParallel(inputTensor, kernels, biases, outputDimension.getStride(), 16);
        postActivation = result.copy();
        return result;
    }

    @Override
    public Matrix3D propogateBackward(Matrix3D localGradient) {
        kernelsGradient = ConvolutionUtils.convolutionForBack(preActivation, localGradient, outputDimension.getStride());
        kernelsGradient = ConvolutionParallelUtils.convolutionForBackParallel(preActivation, localGradient, outputDimension.getStride(), 16);
        biasesGradient = ConvolutionUtils.sumForBiases(localGradient);

        Matrix3D[] swappedKernels = ConvolutionUtils.swapFilters(kernels);
        Matrix3D errorTensor;
        if(outputDimension.getStride() == 1){
            //Формула без Dilate(s-1)
            errorTensor = ConvolutionUtils.zeroPadding(localGradient, outputDimension.getHeightKernel() -1);
//            errorTensor = ConvolutionUtils.convolutionWithoutBiases(errorTensor, swappedKernels, 1);
            errorTensor = ConvolutionParallelUtils.convolutionWithoutBaisesParallel(errorTensor, swappedKernels, 1, 16);
        } else {
            //Формула c Dilate(s-1)
            errorTensor = ConvolutionUtils.dilate(localGradient, outputDimension.getStride() - 1);
            errorTensor = ConvolutionUtils.zeroPadding(errorTensor, outputDimension.getHeightKernel() -1);
//            errorTensor = ConvolutionUtils.convolutionWithoutBiases(errorTensor, swappedKernels, 1);
            errorTensor = ConvolutionParallelUtils.convolutionWithoutBaisesParallel(errorTensor, swappedKernels, 1, 16);
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
    public Object getWeights() {

//        for (int i = 0; i < kernels.length; i++){
//            MatrixUtils.subtract(kernels[i], kernelsGradient[i], optimizer);
//        }
//        for(int i = 0; i < biases.length; i++){
//            biases[i] -= biasesGradient[i] * learnRate;
//            biases[i] -= optimizer.optimize(biasesGradient[i]);
//        };
        return kernels;
    }

    @Override
    public Object getBaises() {
        return biases;
    }

    @Override
    public Object getWeightsGrad() {
        return kernelsGradient;
    }

    @Override
    public Object getBaisesGrad() {
        return biasesGradient;
    }

    @Override
    public Dimension getSize() {
        return outputDimension;
    }
}
