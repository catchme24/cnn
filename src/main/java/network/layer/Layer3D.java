package network.layer;

import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3D;

public interface Layer3D extends Layer {

    Matrix3D propogateBackward(Matrix3D inputTensor);

    Matrix3D propogateForward(Matrix3D inputTensor);

}
