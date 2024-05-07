package function.loss;

import function.loss.LossFunc;
import org.apache.commons.math3.linear.RealMatrix;

public class DefaultLossFunction implements LossFunc<RealMatrix> {

    private double loss;
    private int countOfExamples;

//    @Override
//    public void calculate(RealMatrix errorVector) {
//        countOfExamples++;
//        for (int i = 0; i < errorVector.getRowDimension(); i++) {
//            loss = loss + Math.pow(errorVector.getEntry(i, 0), 2);
//        }
//    }

    @Override
    public void calculate(RealMatrix oneHot, RealMatrix result) {
        countOfExamples++;
        double lossOfSample = 0;
        for (int i = 0; i < oneHot.getRowDimension(); i++) {
            lossOfSample = lossOfSample - oneHot.getEntry(i, 0) * Math.log(result.getEntry(i, 0));
        }
        loss = loss + lossOfSample;
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
