package function.initializer;

import org.apache.commons.math3.linear.RealMatrix;
import util.model.Matrix3D;

public interface Initializer {

    default void initializeMutable(RealMatrix matrix) {
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                matrix.setEntry(i, j, getValue());
            }
        }
    };

    default void initializeMutable(Matrix3D tensor) {
        double[][][] array3d = tensor.getMatrix3d();
        for (int i = 0; i < array3d.length; i++) {
            for (int j = 0; j < array3d[0].length; j++) {
                for (int k = 0; k < array3d[0][0].length; k++) {
                    array3d[i][j][k] = getValue();
                }
            }
        }
    };

    default void initializeMutable(Matrix3D[] tensors) {
        for (int i = 0; i < tensors.length; i++) {
            initializeMutable(tensors[i]);
        }
    };

    default void initializeMutable(double[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = getValue();
        }
    };

    public double getValue();

    void setParams(double... params);
}
