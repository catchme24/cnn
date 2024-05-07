package function;

import org.apache.commons.math3.linear.RealMatrix;

public class DefaultErrorFunction implements ErrorFunc<RealMatrix> {

    @Override
    public RealMatrix calculate(RealMatrix oneHot, RealMatrix result) {
        return result.subtract(oneHot);
    }
}
