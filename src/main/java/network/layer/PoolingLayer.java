package network.layer;

import org.apache.commons.math3.linear.RealMatrix;

public class PoolingLayer implements Layer {
    @Override
    public RealMatrix propogateBackward(RealMatrix inputVector) {
        return null;
    }

    @Override
    public RealMatrix propogateForward(RealMatrix inputVector) {
        return null;
    }

    @Override
    public void correctWeights(double learnRate) {

    }

    @Override
    public void setPrevious(Layer layer) {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
