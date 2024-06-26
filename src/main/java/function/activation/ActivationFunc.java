package function.activation;

import org.apache.commons.math3.linear.RealMatrix;
import util.model.Matrix3D;

public interface ActivationFunc {

//    default RealMatrix calculate(RealMatrix x) {
//        RealMatrix result = x.copy();
//        for (int i = 0; i < result.getRowDimension(); i++) {
//            result.setEntry(i, 0, calculate(x.getEntry(i, 0)));
//        }
//        return result;
//    };

    default RealMatrix calculate(RealMatrix x) {
        RealMatrix result = x.copy();
        for (int i = 0; i < result.getRowDimension(); i++) {
            for (int j = 0; j < result.getColumnDimension(); j++) {
                result.setEntry(i, j, calculate(x.getEntry(i, j)));
            }
        }
        return result;
    };

    default RealMatrix calculateDerivation(RealMatrix x) {
        RealMatrix result = x.copy();
        for (int i = 0; i < result.getRowDimension(); i++) {
            double calculated = calculateDerivation(x.getEntry(i, 0));
//            System.out.println(calculated);
            result.setEntry(i, 0, calculated);
        }
        return result;
    };

    default Matrix3D calculate(Matrix3D x) {
        double[][][] tensor = x.getMatrix3d();
        for (int i = 0; i < tensor.length; i++) {
            for (int j = 0; j < tensor[0].length; j++) {
                for (int k = 0; k < tensor[0][0].length; k++) {
                    tensor[i][j][k] = calculate(tensor[i][j][k]);
                }
            }
        }
        return new Matrix3D(tensor);
    };

    default void calculate(double[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = calculate(array[i][j]);
            }
        }
    };

    default void calculateDerivation(double[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = calculateDerivation(array[i][j]);
            }
        }
    };

    default Matrix3D calculateDerivation(Matrix3D x) {
        double[][][] tensor = x.getMatrix3d();
        for (int i = 0; i < tensor.length; i++) {
            for (int j = 0; j < tensor[0].length; j++) {
                for (int k = 0; k < tensor[0][0].length; k++) {
                    tensor[i][j][k] = calculateDerivation(tensor[i][j][k]);
                }
            }
        }
        return x;
    };

    double calculate(double x);
    double calculateDerivation(double x);
}
