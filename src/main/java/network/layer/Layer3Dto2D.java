package network.layer;

import org.apache.commons.math3.linear.RealMatrix;
import util.model.Matrix3D;

public interface Layer3Dto2D extends Layer {

    Matrix3D propogateBackward(RealMatrix inputVector);

    RealMatrix propogateForward(Matrix3D inputTensor);

}
