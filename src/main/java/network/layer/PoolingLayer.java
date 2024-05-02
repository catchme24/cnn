package network.layer;

import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3D;

public class PoolingLayer implements Layer3D {

    @Override
    public Object propogateBackward(Object input) {
        return null;
    }

    @Override
    public Object propogateForward(Object input) {
        return null;
    }

    @Override
    public void correctWeights(double learnRate) {

    }

    @Override
    public void setPrevious(Layer layer) {

    }

    @Override
    public Matrix3D propogateBackward(Matrix3D inputTensor) {
        return null;
    }

    @Override
    public Matrix3D propogateForward(Matrix3D inputTensor) {
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }
}
