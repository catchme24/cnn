package optimizer;

import org.apache.commons.math3.linear.RealMatrix;
import util.model.Matrix3D;

public interface Optimizer {

    void optimize(RealMatrix params, RealMatrix gradients);

    void optimize(Matrix3D[] params, Matrix3D[] gradients);

    void optimize(double[] params, double[] gradients);

    void optimize(Object params, Object gradients);

    double getLearingRate();

    void setLearningRate(double learningRate);
}
