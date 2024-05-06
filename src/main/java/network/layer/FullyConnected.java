package network.layer;

import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import util.ArraysUtils;
import util.Matrix3DUtils;
import util.MatrixUtils;
import util.model.Matrix3D;

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
        MatrixUtils.fillHeNormal(baises, inputDimension.getHeightTens());
        MatrixUtils.fillHeNormal(weights, inputDimension.getHeightTens());
//        System.out.println("Инициализированные веса:");
//        MatrixUtils.printMatrixTest(weights.getSubMatrix(0, 9, 0, 0));

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

//        //Размеры
//        System.out.println("Forward " + this.getClass().getName() + " " + outputDimension);
//        System.out.println("Входной вектор: " + inputVector.getRowDimension() + "x" + inputVector.getColumnDimension());
//        System.out.println("Весов размер: " + weights.getRowDimension() + "x" + weights.getColumnDimension());
//        System.out.println("Транс весов размер: " + transposedWeight.getRowDimension() + "x" + transposedWeight.getColumnDimension());
//        //Значения
//        System.out.println("-----------ЗНАЧЕНИЯ-----------");
//        System.out.println("Входной вектор: ");
//        MatrixUtils.printMatrixTest(inputVector.getSubMatrix(0, 9, 0, 0));
//        System.out.println("Веса:");
//        MatrixUtils.printMatrixTest(weights.getSubMatrix(0, 9, 0, 9));

        postActivation = output.copy();
        return output;
    }


    @Override
    public RealMatrix propogateBackward(RealMatrix localGradients) {
        baisesGradient = localGradients;
        RealMatrix transopedPreActivation = preActivation.transpose();
        weightsGradient = localGradients.multiply(transopedPreActivation);

//        //Размеры
//        System.out.println("Backward " + this.getClass().getName() + " " + outputDimension);
//        System.out.println("Локал градиент размер: " + localGradients.getRowDimension() + "x" + localGradients.getColumnDimension());
//        System.out.println("Пре активация размер: " + preActivation.getRowDimension() + "x" + preActivation.getColumnDimension());
//        System.out.println("Транс пре активация размер: " + transopedPreActivation.getRowDimension() + "x" + transopedPreActivation.getColumnDimension());
//        System.out.println("Градиент весов размер: " + weightsGradient.getRowDimension() + "x" + weightsGradient.getColumnDimension());
//        System.out.println("Весов размер: " + weights.getRowDimension() + "x" + weights.getColumnDimension());
//        //Значения
//        System.out.println("-----------ЗНАЧЕНИЯ-----------");
//        System.out.println("Локал градиент:");
//        MatrixUtils.printMatrixTest(localGradients.getSubMatrix(0, 9, 0, 0));
//        System.out.println("Пре активация:");
//        MatrixUtils.printMatrixTest(preActivation.getSubMatrix(0, 9, 0, 0));
//        System.out.println("Градиент весов:");
//        MatrixUtils.printMatrixTest(weightsGradient.getSubMatrix(0, 9, 0, 9));

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
    public void correctWeights(double learnRate) {
//        System.out.println("Веса:");
//        MatrixUtils.printMatrixTest(weights.getSubMatrix(0, 9, 0, 9));
//        System.out.println("Градиент весов:");
//        MatrixUtils.printMatrixTest(weightsGradient.transpose().getSubMatrix(0, 9, 0, 9));

        weights = weights.subtract(weightsGradient.transpose().scalarMultiply(learnRate));

//        System.out.println("Веса:");
//        MatrixUtils.printMatrixTest(weights.getSubMatrix(0, 9, 0, 9));

        baises = baises.subtract(baisesGradient.scalarMultiply(learnRate));
    }

    @Override
    public Dimension getSize() {
        return outputDimension;
    }


}
