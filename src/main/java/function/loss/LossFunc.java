package function.loss;

import org.apache.commons.math3.linear.RealMatrix;

public interface LossFunc<D> {

    void calculate(D oneHot, D result);

    double getOveralLoss();

}
