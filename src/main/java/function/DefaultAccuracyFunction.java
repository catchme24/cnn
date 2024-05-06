package function;

import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

import java.sql.SQLOutput;

public class DefaultAccuracyFunction {

    private double accuracy;
    private int countOfExamples;
    private double countOfTrueExamples;

//    @Override
//    public void calculate(RealMatrix errorVector) {
//        countOfExamples++;
//        for (int i = 0; i < errorVector.getRowDimension(); i++) {
//            loss = loss + Math.pow(errorVector.getEntry(i, 0), 2);
//        }
//    }

    public void calculate(int labelNumber, RealMatrix result) {
        System.out.println("Номер класс: " + labelNumber + ", в прямом вектор получился: ");
        MatrixUtils.printMatrixTest(result);
        countOfExamples++;
        double max = result.getEntry(0, 0);
        int indexMax = 0;
        for (int i = 1; i < result.getRowDimension(); i++) {
            if (result.getEntry(i, 0) > max){
                indexMax = i;
                max = result.getEntry(i, 0);
            }
        }
        if (indexMax + 1 == labelNumber){
            countOfTrueExamples++;
        }
    }

    public double getOveralAccuracy() {
        if (countOfExamples == 0) {
            throw new RuntimeException("Делим на ноль:)");
        }
        double accuracy = (countOfTrueExamples / countOfExamples);
        System.out.println("Всего примеров: " + countOfExamples);
        System.out.println("Правильных: " + countOfTrueExamples);
        countOfTrueExamples = 0;
        countOfExamples = 0;
        return accuracy;
    }
}
