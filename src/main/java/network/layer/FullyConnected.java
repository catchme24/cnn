package network.layer;

import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3D;
import util.MatrixUtils;

@Slf4j
public class FullyConnected implements Layer2D {
    private RealMatrix baises;
    private RealMatrix baisesGradient;
    private RealMatrix weights;
    private RealMatrix weightsGradient;

    private RealMatrix preActivation;

    private RealMatrix postActivation;

    private Layer previousLayer;

    private Dimension inputDimension;

    private Dimension outputDimension;


    public FullyConnected(int neuronsCount) {
        this.outputDimension = new Dimension(0, neuronsCount, 0);
        this.baises = MatrixUtils.createInstance(neuronsCount, 1);
//        MatrixUtils.fillHeNormal(baises);
    }

    public FullyConnected(int neuronsCount, int inputsCount) {
        this.outputDimension = new Dimension(0, neuronsCount, 0);
        this.inputDimension = new Dimension(0, inputsCount, 0);
        this.baises = (Array2DRowRealMatrix) MatrixUtils.createInstance(neuronsCount, 1);
//        this.weights = MatrixUtils.createInstance(inputsCount, neuronsCount);
//        MatrixUtils.fillHeNormal(weights);
    }

    @Override
    public void unchain() {
        previousLayer = null;
    }

    @Override
    public void initWeight() {
        weights = MatrixUtils.createInstance(inputDimension.getHeightTens(), outputDimension.getHeightTens());
        MatrixUtils.fillHeNormal(baises, outputDimension.getHeightTens());
        MatrixUtils.fillHeNormal(weights, outputDimension.getHeightTens());
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
//            weights = MatrixUtils.createInstance(previous.getSize().getHeightTens(), dimension.getHeightTens());
            log.debug("FullyConnected layer: {} prev size", previous.getSize());
            log.debug("FullyConnected layer: {} size", outputDimension);
        } else {
            this.inputDimension = new Dimension(0, previous.getSize().getHeightTens(), 0);
            log.debug("FullyConnected layer: {} prev size", previous.getSize());
            log.debug("FullyConnected layer: {} size", outputDimension);
        }
//        MatrixUtils.fillHeNormal(weights);
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

//        System.out.println("ВЕСА");
//        System.out.println(weights.getRowDimension() + "x" + weights.getColumnDimension());
//        System.out.println("ИНПУТ");
//        System.out.println(inputVector.getRowDimension() + "x" + inputVector.getColumnDimension());
//        System.out.println("ОФФСЕТ");
//        System.out.println(baises.getRowDimension() + "x" + baises.getColumnDimension());
//        System.out.println("ОФФСЕТ ДАТА");
//        System.out.println(baises.getData().length + "x" + baises.getData()[0].length);

        //Высчитывает сигнал с оффсетом
        RealMatrix output = weights.transpose().multiply(inputVector).add(baises);
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

        baisesGradient = localGradients;
        log.debug("FullyConneted: offsetGradient:");
        MatrixUtils.printMatrix(baisesGradient);

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
        baises =  (Array2DRowRealMatrix) baises.subtract(baisesGradient.scalarMultiply(learnRate));
    }

    @Override
    public Dimension getSize() {
        return outputDimension;
    }
}
