package network.layer;

import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3D;

public interface Layer3Dto2D extends Layer {

    RealMatrix propogateBackward(Matrix3D inputTensor);

    Matrix3D propogateForward(RealMatrix inputVector);

}
