package network.layer;

import util.model.Matrix3D;

public interface Layer3D extends Layer {

    Matrix3D propogateBackward(Matrix3D inputTensor);

    Matrix3D propogateForward(Matrix3D inputTensor);

}
