package network.layer;

import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import org.apache.commons.math3.linear.RealMatrix;
import util.ConvolutionUtils;
import util.Matrix3D;
import util.MatrixUtils;

@Slf4j
public class PoolingLayer implements Layer3D {

    private Matrix3D preActivation;

    private Matrix3D postActivation;

    private Layer previousLayer;

    private Dimension inputDimension;

    private Dimension outputDimension;


    public PoolingLayer(int kernelSize, int stride) {
        if (kernelSize <= 0) {
            throw new NetworkConfigException("Kernel size cannot be 0 or less!");
        }
        this.outputDimension = new Dimension(0, 0, 0, stride, kernelSize, kernelSize);
    }

    @Override
    public void setPrevious(Layer previous) {
        if (previous == null) return;

        if (!(previous instanceof Layer3D)) {
            throw new NetworkConfigException("Prev layer for Pooling Layer must be child of Layer3D");
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

        this.outputDimension.setChannel(inputDimension.getChannel());
        this.outputDimension.setHeightTens(calcHeight);
        this.outputDimension.setWidthTens(calcWidth);


        log.debug("Convolution layer: {} prev size", inputDimension);
        log.debug("Convolution layer: {} size", outputDimension);
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
    public Matrix3D propogateForward(Matrix3D inputTensor) {
//        System.out.println(outputDimension);
        preActivation = inputTensor.copy();
        Matrix3D result = ConvolutionUtils.maxPooling3D(inputTensor, outputDimension.getHeightKernel(), outputDimension.getStride());
        postActivation = result.copy();
        return result;
    }

    @Override
    public Matrix3D propogateBackward(Matrix3D errorTensor) {
//        Matrix3D error = new Matrix3D(inputDimension.getChannel(),
//                                    inputDimension.getWidthTens(),
//                                    inputDimension.getHeightTens());
        Matrix3D error = ConvolutionUtils.backMaxPooling3D(preActivation, errorTensor, outputDimension.getWidthKernel(), outputDimension.getStride());
        return error;
    }

    @Override
    public void correctWeights(double learnRate) {

    }

    @Override
    public Dimension getSize() {
        return outputDimension;
    }
}
