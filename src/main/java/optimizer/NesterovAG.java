package optimizer;

import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;
import util.model.Matrix3D;

import java.util.HashMap;
import java.util.Map;

public class NesterovAG implements Optimizer {

//    private double learningRate;
    private double gamma;

    private Map<Matrix3D[], Matrix3D[]> tensorParamsToPrevV;

    private Map<Integer, RealMatrix> matrixParamsToPrevV;

    private Map<double[], double[]> arrayParamsToPrevV;

    public NesterovAG() {
        this(0.99);
    }

    public NesterovAG(double gamma) {
//        this.learningRate = learningRate;
        this.gamma = gamma;
        this.tensorParamsToPrevV = new HashMap<>();
        this.matrixParamsToPrevV = new HashMap<>();
        this.arrayParamsToPrevV = new HashMap<>();
    }

    @Override
    public void optimize(RealMatrix params, RealMatrix gradients) {
        int hashCode = System.identityHashCode(params);

        if (!matrixParamsToPrevV.containsKey(hashCode)) {
            matrixParamsToPrevV.put(hashCode, MatrixUtils.createInstance(params.getRowDimension(), params.getColumnDimension()));
        }
        RealMatrix movingAverage = matrixParamsToPrevV.get(hashCode);

        //form moving average
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
                movingAverage.setEntry(i, j, gamma * movingAverage.getEntry(i, j) + (1 - gamma) * gradients.getEntry(i, j));
            }
        }

        //correct weights
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
                params.setEntry(i, j, (params.getEntry(i, j) - movingAverage.getEntry(i, j)));
            }
        }

    }

    @Override
    public void optimize(Matrix3D[] params, Matrix3D[] gradients) {
        double[][][] kernel = params[0].getMatrix3d();
        if (!tensorParamsToPrevV.containsKey(params)) {
            Matrix3D[] empty = new Matrix3D[params.length];
            for (int i = 0; i < params.length; i++) {
                empty[i] = new Matrix3D(kernel.length, kernel[0].length, kernel[0][0].length);
            }
            tensorParamsToPrevV.put(params, empty);
        }

        Matrix3D[] movingAverage = tensorParamsToPrevV.get(params);

        //form moving average
        for (int i = 0; i < movingAverage.length; i++) {
            double[][][] prevMovingAverage = movingAverage[i].getMatrix3d();
            double[][][] gradientsMatrix = gradients[i].getMatrix3d();
            for (int j = 0; j < prevMovingAverage.length; j++) {
                for (int k = 0; k < prevMovingAverage[0].length; k++) {
                    for (int g = 0; g < prevMovingAverage[0][0].length; g++) {
                        prevMovingAverage[j][k][g] += gamma * prevMovingAverage[j][k][g] + (1 - gamma) * gradientsMatrix[j][k][g];
                    }
                }
            }
        }

//        System.out.println("КЕрнелы " + currentV[0].getMatrix3d()[0][0][0]);

        //correct weights
        for (int i = 0; i < params.length; i++) {
            double[][][] paramsMatrix = params[i].getMatrix3d();
            double[][][] prevMovingAverage = movingAverage[i].getMatrix3d();
            double[][][] gradientsMatrix = gradients[i].getMatrix3d();
            for (int j = 0; j < paramsMatrix.length; j++) {
                for (int k = 0; k < paramsMatrix[0].length; k++) {
                    for (int g = 0; g < paramsMatrix[0][0].length; g++) {
                        paramsMatrix[j][k][g] -= prevMovingAverage[j][k][g];
                    }
                }
            }
        }
    }

    @Override
    public void optimize(double[] params, double[] gradients) {
        if (!arrayParamsToPrevV.containsKey(params)) {
            arrayParamsToPrevV.put(params, new double[params.length]);
        }

        double[] movingAverage = arrayParamsToPrevV.get(params);

        //form sum of g^2
        for (int i = 0; i < params.length; i++) {
            movingAverage[i] += gamma * movingAverage[i] + (1 - gamma) * gradients[i];
        }

        //correct weights
        for (int i = 0; i < params.length; i++) {
            params[i] -= movingAverage[i];
        }
    }

    @Override
    public void optimize(Object params, Object gradients) {
        if (params instanceof RealMatrix) {
            optimize((RealMatrix) params, (RealMatrix) gradients);
        } else if (params instanceof double[]) {
            optimize((double[]) params, (double[]) gradients);
        } else {
            optimize((Matrix3D[]) params, (Matrix3D[]) gradients);
        }
    }

    @Override
    public double getLearingRate() {
        return 1 - gamma;
    }

    @Override
    public void setLearningRate(double learningRate) {
        this.gamma = 1 - learningRate;
    }
}
