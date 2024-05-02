package network.layer;

import function.ActivationFunc;
import function.Softmax;
import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

@Slf4j
public class ActivationLayer implements Layer2D {

    private RealMatrix preActivation;

    private RealMatrix postActivation;

    private Layer previousLayer;

    private ActivationFunc activationFunc;

    private Dimension dimension;

    public ActivationLayer(ActivationFunc func) {
        if (func == null) {
            throw new NetworkConfigException("Activation function cannot be null!");
        }
        this.activationFunc = func;
    }

    @Override
    public void setPrevious(Layer previous) {
        if (!(previous instanceof Layer2D || previous instanceof Layer3Dto2D)) {
            throw new NetworkConfigException("Prev layer for FullyConnected must be child of Layer2D");
        }
        if (previous == null) {
            throw new NetworkConfigException("Prev layer for activation layer cannot be null!");
        }
        this.previousLayer = previous;
        dimension = new Dimension(0, previous.getSize().getHeightTens(), 0);
        log.debug("ActivationLayer layer: {} prev size", previous.getSize());
        log.debug("ActivationLayer layer: {} size", dimension.getHeightTens());
    }

    @Override
    public RealMatrix propogateForward(RealMatrix inputVector) {
        log.debug("ActivationLayer: Start propogateForward with:");
        MatrixUtils.printMatrix(inputVector);
        if (inputVector.getColumnDimension() != 1) {
            throw new NetworkConfigException(   "Input vector has size: " + inputVector.getRowDimension() +
                    "x" + inputVector.getColumnDimension() +
                    ". Count of columns must be 1!"
            );
        }

        preActivation = inputVector.copy();
        //Высчитывает сигнал с оффсетом, если он установлен
        RealMatrix output = activationFunc.calculate(inputVector);
        postActivation = output.copy();
        log.debug("ActivationLayer: End propogateForward with:");
        MatrixUtils.printMatrix(output);
        return output;
    }

    @Override
    public Object propogateBackward(Object input) {
        return propogateBackward((RealMatrix) input);
    }

    @Override
    public Object propogateForward(Object input) {
        return propogateForward((RealMatrix) input);
    }

    @Override
    public void correctWeights(double learnRate) {

    }

    @Override
    public RealMatrix propogateBackward(RealMatrix errorVector) {
        log.debug("ActivationLayer: Start propogateBackward with error vector:");
        MatrixUtils.printMatrix(errorVector);

        log.debug("ActivationLayer: preActivation vector:");
        MatrixUtils.printMatrix(preActivation);

        RealMatrix localGradients;

        if (!(activationFunc instanceof Softmax)) {
            RealMatrix derivation = activationFunc.calculateDerivation(preActivation);

            log.debug("ActivationLayer: derivations vector:");
            MatrixUtils.printMatrix(derivation);

            localGradients = derivation.copy();

            for (int i = 0; i < errorVector.getRowDimension(); i++) {
                localGradients.setEntry(i,
                        0,
                        errorVector.getEntry(i, 0) * derivation.getEntry(i, 0));
            }
        } else {
            localGradients = errorVector.copy();
        }

        log.debug("ActivationLayer: End propogateBackward with local gradient:");
        MatrixUtils.printMatrix(localGradients);
        return localGradients;
    }

    @Override
    public Dimension getSize() {
        return dimension;
    }
}
