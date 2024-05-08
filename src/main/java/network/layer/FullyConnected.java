package network.layer;

import function.initializer.Initializer;
import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import optimizer.Optimizer;
import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

@Slf4j
public class FullyConnected implements Layer2D, LearningLayer {
    private RealMatrix baises;
    private RealMatrix baisesGradient;
    private RealMatrix weights;
    private RealMatrix weightsGradient;

    private RealMatrix preActivation;

    private RealMatrix postActivation;

    private Layer previousLayer;

    private Dimension inputDimension;

    private Dimension outputDimension;

    private Initializer initializer;


    public FullyConnected(int neuronsCount, Initializer initializer) {
        this(neuronsCount);
        this.initializer = initializer;
    }

    public FullyConnected(int neuronsCount) {
        this.outputDimension = new Dimension(0, neuronsCount, 0);
        this.baises = MatrixUtils.createInstance(neuronsCount, 1);
    }


    public FullyConnected(int neuronsCount, int inputsCount, Initializer initializer) {
        this(neuronsCount, inputsCount);
        this.initializer = initializer;
    }
    public FullyConnected(int neuronsCount, int inputsCount) {
        this.outputDimension = new Dimension(0, neuronsCount, 0);
        this.inputDimension = new Dimension(0, inputsCount, 0);
        this.baises = MatrixUtils.createInstance(neuronsCount, 1);
    }

    @Override
    public void unchain() {
        previousLayer = null;
        initializer = null;
    }

    @Override
    public void initWeightsAndBaises() {
        weights = MatrixUtils.createInstance(inputDimension.getHeightTens(), outputDimension.getHeightTens());
        initializer.setParams(inputDimension.getHeightTens());
        initializer.initializeMutable(baises);
        initializer.initializeMutable(weights);
//        MatrixUtils.fillHeNormal(baises, inputDimension.getHeightTens());
//        MatrixUtils.fillHeNormal(weights, inputDimension.getHeightTens());
    }

    @Override
    public void setPrevious(Layer previous) {
        if (outputDimension.getHeightTens() <= 0) {
            if (previous == null) {
                throw new NetworkConfigException("Prev layer for fully connected cannot be null!");
            }
            if (!(previous instanceof Layer2D || previous instanceof Layer3Dto2D)) {
                throw new NetworkConfigException("Prev layer for FullyConnected must be child of Layer2D");
            }
            this.previousLayer = previous;
            this.inputDimension = new Dimension(0, previous.getSize().getHeightTens(), 0);
            log.debug("FullyConnected layer: {} prev size", previous.getSize());
            log.debug("FullyConnected layer: {} size", outputDimension);
        } else {
            this.inputDimension = new Dimension(0, previous.getSize().getHeightTens(), 0);
            log.debug("FullyConnected layer: {} prev size", previous.getSize());
            log.debug("FullyConnected layer: {} size", outputDimension);
        }

    }

    @Override
    public RealMatrix propogateForward(RealMatrix inputVector) {
        if (inputVector.getColumnDimension() != 1) {
            throw new NetworkConfigException(   "Input vector has size: " + inputVector.getRowDimension() +
                    "x" + inputVector.getColumnDimension() +
                    ". Count of columns must be 1!"
            );
        }
        if (weights == null) {
            throw new NetworkConfigException("First FullyConnected layer must have inputs count!");
        }

        preActivation = inputVector.copy();
        RealMatrix transposedWeight = weights.transpose();
        RealMatrix multiplied = transposedWeight.multiply(inputVector);
        RealMatrix output = multiplied.add(baises);
        return output;
    }


    @Override
    public RealMatrix propogateBackward(RealMatrix localGradients) {
        baisesGradient = localGradients;
        RealMatrix transopedPreActivation = preActivation.transpose();
        weightsGradient = localGradients.multiply(transopedPreActivation);
        RealMatrix errorVector = weights.multiply(localGradients);
        return errorVector;
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
    public Object getWeights() {
//        optimizer.optimize(weights);
//        weights = weights.subtract(weightsGradient.transpose().scalarMultiply(optimizer.getLearingRate()));
//        baises = baises.subtract(baisesGradient.scalarMultiply(optimizer.getLearingRate()));
        return weights;
    }

    @Override
    public RealMatrix getBaises() {
        return baises;
    }

    @Override
    public Object getWeightsGrad() {
        return weightsGradient.transpose();
    }

    @Override
    public Object getBaisesGrad() {
        return baisesGradient;
    }

    @Override
    public Dimension getSize() {
        return outputDimension;
    }
}
