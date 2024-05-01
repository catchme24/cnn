package network.layer;

import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3D;

public interface Layer3D {

    Matrix3D propogateBackward(Matrix3D inputTensor);

    Matrix3D propogateForward(Matrix3D inputTensor);

    void correctWeights(double learnRate);

    void setPrevious(Layer3D layer);

    Dimension getSize();
}
