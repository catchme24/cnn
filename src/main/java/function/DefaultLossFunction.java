package function;

import org.apache.commons.math3.linear.RealMatrix;

public class DefaultLossFunction implements LossFunc {

    private double loss;
    private int countOfExamples;

    @Override
    public void calculate(RealMatrix errorVector) {
        countOfExamples++;
        for (int i = 0; i < errorVector.getRowDimension(); i++) {
            loss = loss + Math.pow(errorVector.getEntry(i, 0), 2);
        }
    }

    @Override
    public double getOveralLoss() {
        if (countOfExamples == 0) {
            throw new RuntimeException("Делим на ноль:)");
        }
        double overal = (loss / countOfExamples);
        loss = 0;
        countOfExamples = 0;
        return overal;
    }
}