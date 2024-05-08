package network.layer;

import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import optimizer.Optimizer;
import util.ConvolutionParallelUtils;
import util.ConvolutionUtils;
import util.Matrix3DUtils;
import util.model.Matrix3D;

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
    public void unchain() {
        previousLayer = null;
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
        int countTasks = 1;
        if (inputDimension.getHeightTens() >= 20) {
            countTasks *= Double.valueOf(getSize().getHeightTens()).intValue() / 10;
        }
        if (inputDimension.getChannel() > 32) {
            countTasks *= 2;
        }

        preActivation = inputTensor.copy();
        Matrix3D result;
        if (countTasks == 1) {
            result = ConvolutionUtils.maxPooling3D(inputTensor, outputDimension.getHeightKernel(), outputDimension.getStride());
        } else {
            result = ConvolutionParallelUtils.maxPooling3DParallel(inputTensor, outputDimension.getHeightKernel(), outputDimension.getStride(), countTasks);
        }
        postActivation = result.copy();
        return result;
    }

    @Override
    public Matrix3D propogateBackward(Matrix3D errorTensor) {
        int countTasks = 1;
        if (outputDimension.getHeightTens() >= 10) {
            countTasks *= 4;
        }

        Matrix3D error;
        if (countTasks == 1) {
            error = ConvolutionUtils.maxPooling3DForBack(preActivation, errorTensor, outputDimension.getWidthKernel(), outputDimension.getStride());
        } else {
            error = ConvolutionParallelUtils.maxPooling3DForBackParallel(preActivation, errorTensor, outputDimension.getWidthKernel(), outputDimension.getStride(), countTasks);
        }

        return error;
    }

    @Override
    public Dimension getSize() {
        return outputDimension;
    }
}
