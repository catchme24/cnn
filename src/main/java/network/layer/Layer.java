package network.layer;

import util.Matrix3D;

public interface Layer {

    Object propogateBackward(Object input);

    Object propogateForward(Object input);
    void correctWeights(double learnRate);
    void setPrevious(Layer layer);
    Dimension getSize();
}
