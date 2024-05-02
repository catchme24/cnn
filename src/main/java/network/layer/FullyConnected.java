package network.layer;

import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

@Slf4j
public class FullyConnected implements Layer2D {
    private RealMatrix offset;
    private RealMatrix offsetGradient;
    private RealMatrix weights;
    private RealMatrix weightsGradient;

    private RealMatrix preActivation;

    private RealMatrix postActivation;

    private Layer previousLayer;

    private Dimension dimension;

    private int inputsCount = 0;

    public FullyConnected(int neuronsCount) {
        this.dimension = new Dimension(0, neuronsCount, 0);
        this.offset = MatrixUtils.createInstance(neuronsCount, 1);
        MatrixUtils.fillHeNormal(offset);
    }

    public FullyConnected(int neuronsCount, int inputsCount) {
        this(neuronsCount);
        this.inputsCount = inputsCount;
        weights = MatrixUtils.createInstance(inputsCount, neuronsCount);
        MatrixUtils.fillHeNormal(weights);
    }

    @Override
    public void setPrevious(Layer previous) {
        if (inputsCount <= 0) {
            if (previous == null) {
                throw new NetworkConfigException("Prev layer for fully connected cannot be null!");
            }
            if (!(previous instanceof Layer2D || previous instanceof Layer3Dto2D)) {
                throw new NetworkConfigException("Prev layer for FullyConnected must be child of Layer2D");
            }
            this.previousLayer = previous;
            weights = MatrixUtils.createInstance(previous.getSize().getHeightTens(), dimension.getHeightTens());
            log.debug("FullyConnected layer: {} prev size", previous.getSize());
            log.debug("FullyConnected layer: {} size", dimension.getHeightTens());
        } else {
            log.debug("FullyConnected layer: {} prev size", inputsCount);
            log.debug("FullyConnected layer: {} size", dimension.getHeightTens());
        }
        MatrixUtils.fillHeNormal(weights);
    }

    @Override
    public RealMatrix propogateForward(RealMatrix inputVector) {
        log.debug("FullyConneted: Start propogateForward with:");
        MatrixUtils.printMatrix(inputVector);
        if (inputVector.getColumnDimension() != 1) {
            throw new NetworkConfigException(   "Input vector has size: " + inputVector.getRowDimension() +
                    "x" + inputVector.getColumnDimension() +
                    ". Count of columns must be 1!"
            );
        }

        if (weights == null) {
            throw new NetworkConfigException("First FullyConnected layer must have inputs count!");
        }

//        log.debug("МАТРИЦА ВЕСОВ");
//        MatrixUtils.printMatrix(weights);
//        log.debug("ТРАНСПОНИРОВАННАЯ МАТРИЦА ВЕСОВ");
//        MatrixUtils.printMatrix(weights.transpose());
//        log.debug("МАТРИЦА СМЕЩЕНИЯ");
//        MatrixUtils.printMatrix(offset);

        preActivation = inputVector.copy();
        //Высчитывает сигнал с оффсетом
        RealMatrix output = weights.transpose().multiply(inputVector).add(offset);
        postActivation = output.copy();
        log.debug("FullyConneted: End propogateForward with:");
        MatrixUtils.printMatrix(output);
        return output;
    }


    @Override
    public RealMatrix propogateBackward(RealMatrix localGradients) {
        log.debug("FullyConneted: Start propogateBackward with local gradient activation layer:");
        MatrixUtils.printMatrix(localGradients);

        log.debug("FullyConneted: preActivation:");
        MatrixUtils.printMatrix(preActivation.transpose());

        offsetGradient = localGradients;
        log.debug("FullyConneted: offsetGradient:");
        MatrixUtils.printMatrix(offsetGradient);

        weightsGradient = localGradients.multiply(preActivation.transpose());
        log.debug("FullyConneted: weightsGradient:");
        MatrixUtils.printMatrix(weightsGradient);

        RealMatrix errorVector = weights.multiply(localGradients);
        log.debug("FullyConneted: End propogateBackward with error vector:");
        MatrixUtils.printMatrix(errorVector);
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
    public void correctWeights(double learnRate) {
        weights = weights.subtract(weightsGradient.transpose().scalarMultiply(learnRate));
        offset = offset.subtract(offsetGradient.scalarMultiply(learnRate));
    }

    @Override
    public Dimension getSize() {
        return dimension;
    }
}
