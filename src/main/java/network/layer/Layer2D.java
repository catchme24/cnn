package network.layer;

import org.apache.commons.math3.linear.RealMatrix;

public interface Layer2D extends Layer {

    RealMatrix propogateBackward(RealMatrix inputVector);

    RealMatrix propogateForward(RealMatrix inputVector);

}
