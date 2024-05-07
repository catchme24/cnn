package network.layer;

import function.activation.ActivationFunc;
import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import optimizer.Optimizer;
import util.ActivationParallelUtils;
import util.Matrix3DUtils;
import util.model.Matrix3D;
import util.MatrixUtils;

@Slf4j
public class Activation3DLayer implements Layer3D {

    private Matrix3D preActivation;

    private Matrix3D postActivation;

    private Layer previousLayer;

    private ActivationFunc activationFunc;

    private Dimension dimension;

    public Activation3DLayer() {

    }

    public Activation3DLayer(ActivationFunc func) {
        if (func == null) {
            throw new NetworkConfigException("Activation function cannot be null!");
        }
        this.activationFunc = func;
    }

    @Override
    public void initWeight() {

    }

    @Override
    public void setPrevious(Layer previous) {
        if (!(previous instanceof Layer3D)) {
            throw new NetworkConfigException("Prev layer for Activation3D must be child of Layer3D");
        }

        if (previous == null) {
            throw new NetworkConfigException("Prev layer for activation layer cannot be null!");
        }
        previousLayer = previous;

        dimension = new Dimension( previous.getSize().getChannel(),
                                    previous.getSize().getHeightTens(),
                                    previous.getSize().getWidthTens(),
                                    previous.getSize().getStride(),
                                    previous.getSize().getHeightKernel(),
                                    previous.getSize().getWidthKernel());

        log.debug("Activation3DLayer layer: {} prev size", previous.getSize());
    }

    @Override
    public void unchain() {
        previousLayer = null;
    }

    @Override
    public Matrix3D propogateForward(Matrix3D inputTensor) {

        preActivation = inputTensor.copy();
        Matrix3D outputTensor = activationFunc.calculate(inputTensor);
//        ActivationParallelUtils.activationParallelMutable(activationFunc, inputTensor, 4);

        return inputTensor;
    }

    @Override
    public Matrix3D propogateBackward(Matrix3D errorTensor) {
//        ActivationParallelUtils.activationDerivationParallelMutable(activationFunc, preActivation, 4);
//        Matrix3D localGradient = Matrix3DUtils.hadamard(preActivation, errorTensor);
        Matrix3D localGradient = MatrixUtils.hadamard(activationFunc.calculateDerivation(preActivation), errorTensor);
        return localGradient;
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
    public void correctWeights(Optimizer optimizer) {

    }

    @Override
    public Dimension getSize() {
        return dimension;
    }
}
