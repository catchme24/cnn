package network.layer;

import function.activation.ActivationFunc;
import function.activation.Softmax;
import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import optimizer.Optimizer;
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
    public void unchain() {
        previousLayer = null;
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
        RealMatrix output = activationFunc.calculate(inputVector);
        postActivation = output.copy();

//        //Размеры
//        System.out.println("Forward " + this.getClass().getName() + " " + dimension);
        //Значения
//        System.out.println("-----------ЗНАЧЕНИЯ-----------");
//        System.out.println("Входной вектор: ");
//        MatrixUtils.printMatrixTest(inputVector.getSubMatrix(0, 9, 0, 0));
//        System.out.println("Активированный:");
//        MatrixUtils.printMatrixTest(postActivation.getSubMatrix(0, 9, 0, 0));

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
    public RealMatrix propogateBackward(RealMatrix errorVector) {

        RealMatrix localGradients;
        RealMatrix derivation = null;

        if (!(activationFunc instanceof Softmax)) {
            derivation = activationFunc.calculateDerivation(preActivation);
            localGradients = derivation.copy();
            for (int i = 0; i < errorVector.getRowDimension(); i++) {
                localGradients.setEntry(i,
                        0,
                        errorVector.getEntry(i, 0) * derivation.getEntry(i, 0));
            }
        } else {
            localGradients = errorVector.copy();
        }

//        //Размеры
//        System.out.println("Backward " + this.getClass().getName() + " " + dimension);
//        //Значения
//        System.out.println("-----------ЗНАЧЕНИЯ-----------");
//        if (!(activationFunc instanceof Softmax)) {
//            System.out.println("Производные");
//            MatrixUtils.printMatrixTest(derivation.getSubMatrix(0, 9, 0, 0));
//        }
//        System.out.println("Пре активация");
//        MatrixUtils.printMatrixTest(preActivation.getSubMatrix(0, 9, 0, 0));
//        System.out.println("Еррор вектор");
//        MatrixUtils.printMatrixTest(errorVector.getSubMatrix(0, 9, 0, 0));

        return localGradients;
    }

    @Override
    public Dimension getSize() {
        return dimension;
    }
}
