package network.layer;

import function.ActivationFunc;
import function.Softmax;
import jdk.jfr.Percentage;
import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3D;
import util.MatrixUtils;

@Slf4j
public class Activation3DLayer implements Layer3D {

    private Matrix3D preActivation;

    private Matrix3D postActivation;

    private Layer3D prev;

    private ActivationFunc activationFunc;

    private Dimension dimension;

    public Activation3DLayer(ActivationFunc func) {
        if (func == null) {
            throw new NetworkConfigException("Activation function cannot be null!");
        }
        this.activationFunc = func;
    }

    @Override
    public void setPrevious(Layer3D prev) {
        if (prev == null) {
            throw new NetworkConfigException("Prev layer for activation layer cannot be null!");
        }
        this.prev = prev;


        dimension = new Dimension( prev.getSize().getChannel(),
                prev.getSize().getHeightTens(),
                prev.getSize().getWidthTens(),
                prev.getSize().getStride(),
                prev.getSize().getHeightKernel(),
                prev.getSize().getWidthKernel());

        log.debug("Activation3DLayer layer: {} prev size", prev.getSize());

    }

    @Override
    public Matrix3D propogateForward(Matrix3D inputTensor) {
//        log.debug("Activation3DLayer layer: Start propogateForward with:");
//        MatrixUtils.printMatrix3D(inputTensor);

        preActivation = inputTensor.copy();
        //Высчитывает сигнал с оффсетом, если он установлен
        Matrix3D outputTensor = activationFunc.calculate(inputTensor);
//        postActivation = outputTensor;
//        log.debug("Activation3DLayer layer: End propogateForward with:");
//        MatrixUtils.printMatrix3D(outputTensor);
        return outputTensor;
    }

    @Override
    public void correctWeights(double learnRate) {

    }

    @Override
    public Matrix3D propogateBackward(Matrix3D errorVector) {

//        log.debug("Activation3DLayer layer: Start propogateBackward with error vector:");
//        MatrixUtils.printMatrix(errorVector);
//
//        log.debug("Activation3DLayer layer: preActivation vector:");
//        MatrixUtils.printMatrix(preActivation);
//
        Matrix3D localGradients = null;
//
//        if (!(activationFunc instanceof Softmax)) {
//            RealMatrix derivation = activationFunc.calculateDerivation(preActivation);
//
//            log.debug("Activation3DLayer layer: derivations vector:");
//            MatrixUtils.printMatrix(derivation);
//
//            localGradients = derivation.copy();
//
//            for (int i = 0; i < errorVector.getRowDimension(); i++) {
//                localGradients.setEntry(i,
//                        0,
//                        errorVector.getEntry(i, 0) * derivation.getEntry(i, 0));
//            }
//        } else {
//            localGradients = errorVector.copy();
//        }
//
//        log.debug("Activation3DLayer layer: End propogateBackward with local gradient:");
//        MatrixUtils.printMatrix(localGradients);
        return localGradients;
    }

    @Override
    public Dimension getSize() {
        return dimension;
    }
}
