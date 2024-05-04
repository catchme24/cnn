package function;

import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3D;

public interface LossFunc {

    void calculate(RealMatrix x, RealMatrix y);

    double getOveralLoss();

}
