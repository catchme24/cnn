package network.layer;


import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import optimizer.Optimizer;
import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

@Slf4j
public class DropoutLayer implements Layer2D {

    private RealMatrix dropoutVector;

    private Layer previousLayer;

    private double dropout;

    private Dimension dimension;


    public DropoutLayer(double dropout) {
        this.dropout = dropout;
    }

    @Override
    public void unchain() {
        previousLayer = null;
    }

    @Override
    public void setPrevious(Layer previous) {
        if (!(previous instanceof Layer2D || previous instanceof Layer3Dto2D)) {
            throw new NetworkConfigException("Prev layer for FullyConnected must be child of Layer2D");
        }

        if (previous == null) {
            throw new NetworkConfigException("Prev layer for activation layer cannot be null!");
        }
        previousLayer = previous;

        dimension = new Dimension(0, previous.getSize().getHeightTens(), 0);

        this.dropoutVector = MatrixUtils.createVectorWithSameValue(dimension.getHeightTens(), 1);
        MatrixUtils.fillDropout(dropoutVector, dropout);

        log.debug("ActivationLayer layer: {} prev size", previous.getSize());
        log.debug("ActivationLayer layer: {} size", dimension);
    }




    @Override
    public RealMatrix propogateForward(RealMatrix inputVector) {
        //CHECK
        this.dropoutVector = MatrixUtils.createVectorWithSameValue(dimension.getHeightTens(), 1);
        MatrixUtils.fillDropout(dropoutVector, dropout);

        RealMatrix outputVector = inputVector.copy();

        for (int i = 0; i < inputVector.getRowDimension(); i++) {
            outputVector.setEntry(i, 0, inputVector.getEntry(i, 0) * dropoutVector.getEntry(i, 0));
        }

        return outputVector;
    }

    @Override
    public RealMatrix propogateBackward(RealMatrix errorVector) {

        //CHECK
        this.dropoutVector = MatrixUtils.createVectorWithSameValue(dimension.getHeightTens(), 1);
        MatrixUtils.fillDropout(dropoutVector, dropout);

        RealMatrix localGradients = errorVector.copy();

        for (int i = 0; i < localGradients.getRowDimension(); i++) {
            localGradients.setEntry(i, 0, errorVector.getEntry(i, 0) * dropoutVector.getEntry(i, 0));
        }
        return localGradients;
    }

    @Override
    public Object propogateForward(Object input) {
        return propogateForward((RealMatrix) input);
    }

    @Override
    public Object propogateBackward(Object input) {
        return propogateBackward((RealMatrix) input);
    }

    @Override
    public Dimension getSize() {
        return dimension;
    }
}

