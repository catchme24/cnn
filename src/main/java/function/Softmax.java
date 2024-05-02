package function;

import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

import java.math.BigDecimal;

public class Softmax implements ActivationFunc {

//    @Override
//    public RealMatrix calculate(RealMatrix x) {
//        RealMatrix result = x.copy();
//        double max = 0;
//        double[] vector = x.getColumn(0);
//        for (int i = 0; i < vector.length; i++) {
//            if (vector[i] > max) max = vector[i];
//
//        }
//        double summ = 0;
//        for (int i = 0; i < vector.length; i++) {
//            summ = summ + Math.exp(vector[i] - max);
////            System.out.println(summ);
//        }
//        for (int i = 0; i < result.getRowDimension(); i++) {
////            System.out.println(Math.exp(result.getEntry(i, 0) - max));
//            result.setEntry(i, 0, Math.exp(result.getEntry(i, 0) - max) / summ);
//        }
//        return result;
//    }

    @Override
    public RealMatrix calculate(RealMatrix x) {
        RealMatrix result = x.copy();
        double[] vector = x.getColumn(0);
        double summ = 0;
        for (int i = 0; i < vector.length; i++) {
            summ = summ + Math.exp(vector[i]);
//            System.out.println(summ);
        }
        for (int i = 0; i < result.getRowDimension(); i++) {
//            System.out.println(Math.exp(result.getEntry(i, 0)-max));
            result.setEntry(i, 0, Math.exp(result.getEntry(i, 0)) / summ);
        }
        return result;
    }

//    @Override
//    public RealMatrix calculate(RealMatrix x) {
//        MatrixUtils.printMatrixTest(x);
//        System.out.println("--------");
//
//        RealMatrix result = x.copy();
//
//        double summ = 0;
//        double[] vector = x.getColumn(0);
//        for (int i = 0; i < vector.length; i++) {
//            summ = summ + vector[i];
//        }
//
//        for (int i = 0; i < result.getRowDimension(); i++) {
//            result.setEntry(i, 0, Math.abs(result.getEntry(i, 0)) / summ * 10);
//        }
//
//        MatrixUtils.printMatrixTest(result);
//        System.out.println("--------");
//
//
//        //Real Softmax
//        summ = 0;
//        vector = result.getColumn(0);
//        for (int i = 0; i < vector.length; i++) {
//            summ = summ + Math.exp(vector[i]);
//        }
//
//        for (int i = 0; i < result.getRowDimension(); i++) {
//            result.setEntry(i, 0, Math.exp(result.getEntry(i, 0)) / summ);
//        }
//        //END REAL SOFTMAX
//
//        double newSum = 0;
//        for (int i = 0; i < result.getRowDimension(); i++) {
//            newSum += result.getEntry(i, 0);
//        }
//
//        System.out.println("СУММА: " + newSum);
//
//        return result;
//    }

//    @Override
//    public RealMatrix calculate(RealMatrix x) {
//        RealMatrix result = x.copy();
//        BigDecimal summ = new BigDecimal(0);
//        double[] vector = x.getColumn(0);
//        double max = 0;
//        for (int i = 0; i < vector.length; i++) {
//            if (vector[i] > max) max = vector[i];
//        }
//        for (int i = 0; i < vector.length; i++) {
//            BigDecimal current = new BigDecimal(Math.exp(vector[i] - max));
//            summ = summ.add(current);
//        }
//        for (int i = 0; i < result.getRowDimension(); i++) {
//            BigDecimal curr = new BigDecimal(Math.exp(vector[i] - max));
//            double value = (curr.divide(summ)).doubleValue();
//            result.setEntry(i, 0, value);
//        }
//        return result;
//    }
    @Override
    public RealMatrix calculateDerivation(RealMatrix x) {
        return ActivationFunc.super.calculateDerivation(x);
    }


    @Override
    public double calculate(double x) {
        return 0;
    }

    @Override
    public double calculateDerivation(double x) {
        return 0;
    }
}
