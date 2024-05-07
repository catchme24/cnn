package function.accuracy;

import org.apache.commons.math3.linear.RealMatrix;

public interface AccuracyFunc<D> {

    void calculate(int labelNumber, D result);

    double getOveralAccuracy();
}
