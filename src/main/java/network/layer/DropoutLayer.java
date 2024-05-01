package network.layer;


import function.ActivationFunc;
import function.Softmax;
import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

@Slf4j
public class DropoutLayer implements Layer  {

    private RealMatrix dropoutVector;

    private Layer prev;

    private double dropout;

    private int neuronsCount;


    public DropoutLayer(double dropout) {
        this.dropout = dropout;
    }

    public void setPrevious(Layer prev) {
        if (prev == null) {
            throw new NetworkConfigException("Prev layer for activation layer cannot be null!");
        }
        this.prev = prev;
        this.neuronsCount = prev.getSize();

        this.dropoutVector = MatrixUtils.createInstance(neuronsCount, 1);
        MatrixUtils.fillDropout(dropoutVector, dropout);

        log.debug("ActivationLayer layer: {} prev size", prev.getSize());
        log.debug("ActivationLayer layer: {} size", neuronsCount);
    }

    @Override
    public RealMatrix propogateForward(RealMatrix inputVector) {
        log.debug("DropoutLayer: Start propogateForward with:");
        MatrixUtils.printMatrix(inputVector);

        RealMatrix outputVector = inputVector.copy();

        for (int i = 0; i < inputVector.getRowDimension(); i++) {
            outputVector.setEntry(i, 0, inputVector.getEntry(i, 0) * dropoutVector.getEntry(i, 0));
        }

        log.debug("DropoutLayer: End propogateForward with:");
        MatrixUtils.printMatrix(outputVector);
        return outputVector;
    }

    @Override
    public void correctWeights(double learnRate) {

    }

    @Override
    public RealMatrix propogateBackward(RealMatrix errorVector) {
        log.debug("DropoutLayer: Start propogateForward with:");
        MatrixUtils.printMatrix(errorVector);

        RealMatrix localGradients = errorVector.copy();

        for (int i = 0; i < localGradients.getRowDimension(); i++) {
            localGradients.setEntry(i, 0, errorVector.getEntry(i, 0) * dropoutVector.getEntry(i, 0));
        }

        log.debug("DropoutLayer: End propogateForward with:");
        MatrixUtils.printMatrix(localGradients);
        return localGradients;
    }

    @Override
    public int getSize() {
        return neuronsCount;
    }
}

