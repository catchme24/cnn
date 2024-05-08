package function;

import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

public class DefaultErrorFunction implements ErrorFunc<RealMatrix> {

    @Override
    public RealMatrix calculate(RealMatrix oneHot, RealMatrix result) {
//        System.out.println("-------------");
//        MatrixUtils.printMatrixTest(result);

        return result.subtract(oneHot);
    }
}
