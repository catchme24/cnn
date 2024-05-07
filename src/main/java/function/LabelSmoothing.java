package function;

import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

public class LabelSmoothing implements ErrorFunc<RealMatrix> {

    private double epsilon;

    public LabelSmoothing(double epsilon) {
        this.epsilon = epsilon;
    }

    @Override
    public RealMatrix calculate(RealMatrix oneHot, RealMatrix result) {
        RealMatrix smoothing = MatrixUtils.createEmptyVector(oneHot.getRowDimension());

        for (int i = 0; i < oneHot.getRowDimension(); i++) {
            if (oneHot.getEntry(i, 0) == 1) {
                smoothing.setEntry(i, 0, 1 - epsilon);
            } else {
                smoothing.setEntry(i, 0, epsilon / (oneHot.getRowDimension() - 1));
            }
        }

        return result.subtract(smoothing);
    }
}
