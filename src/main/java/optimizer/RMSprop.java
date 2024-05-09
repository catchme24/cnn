package optimizer;

import org.apache.commons.math3.linear.RealMatrix;
import util.ArraysUtils;
import util.MatrixUtils;
import util.model.Matrix3D;

import java.util.HashMap;
import java.util.Map;


/*
 ЕСЛИ НЕ ЗАДАВАТЬ НАЧАЛЬНОЕ ЗНАЧЕНИЕ БУДЕТ ДОЛГО НАКАПЛИВАТЬСЯ И СООТСТВЕННО ДОЛГО ОБУЧАТЬСЯ!!11
 РЕКОМЕНДУЕМ ЗАДАТЬ НАЧАЛЬНОЕ ЗНАЧЕНИЕ ОТЛИЧНОЕ ОТ НУЛЯ
*/
public class RMSprop implements Optimizer {

    private double learningRate;
    private double epsilon;

    private double gamma;

    private double initValue;

    private Map<Matrix3D[], Matrix3D[]> tensorParamsToPrevV;

    private Map<Integer, RealMatrix> matrixParamsToPrevV;

    private Map<double[], double[]> arrayParamsToPrevV;

    private double weightDecay;

    public RMSprop() {
        this(0.0000001, 0.9, 0.0, 0.0);
    }

    public RMSprop(double epsilon, double gamma, double initValue, double weightDecay) {
        this(0.001, epsilon, gamma, initValue, weightDecay);
    }

    public RMSprop(double learningRate, double epsilon, double gamma, double initValue, double weightDecay) {
        this.epsilon = epsilon;
        this.learningRate = learningRate;
        this.gamma = gamma;
        this.initValue = initValue;
        this.weightDecay = weightDecay;
        this.tensorParamsToPrevV = new HashMap<>();
        this.matrixParamsToPrevV = new HashMap<>();
        this.arrayParamsToPrevV = new HashMap<>();
    }

    @Override
    public void optimize(RealMatrix params, RealMatrix gradients) {
        int hashCode = System.identityHashCode(params);

        if (!matrixParamsToPrevV.containsKey(hashCode)) {
            matrixParamsToPrevV.put(hashCode, MatrixUtils.createInstance(params.getRowDimension(), params.getColumnDimension(), initValue));
        }
        RealMatrix movingAverage = matrixParamsToPrevV.get(hashCode);

        //form moving average
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
                movingAverage.setEntry(i, j, gamma * movingAverage.getEntry(i, j) + (1 - gamma) * Math.pow(gradients.getEntry(i, j), 2));
            }
        }

        //correct weights
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
                params.setEntry(i, j, (params.getEntry(i, j) - (learningRate * gradients.getEntry(i, j)) / (Math.sqrt(movingAverage.getEntry(i, j) + epsilon)) - weightDecay * learningRate * params.getEntry(i, j)));
            }
        }

    }

    @Override
    public void optimize(Matrix3D[] params, Matrix3D[] gradients) {
        double[][][] kernel = params[0].getMatrix3d();
        if (!tensorParamsToPrevV.containsKey(params)) {
            Matrix3D[] empty = new Matrix3D[params.length];
            for (int i = 0; i < params.length; i++) {
                empty[i] = new Matrix3D(kernel.length, kernel[0].length, kernel[0][0].length, initValue);
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
                        prevMovingAverage[j][k][g] += gamma * prevMovingAverage[j][k][g] + (1 - gamma) * Math.pow(gradientsMatrix[j][k][g], 2);
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
                        paramsMatrix[j][k][g] -= learningRate * gradientsMatrix[j][k][g] / (Math.sqrt(prevMovingAverage[j][k][g] + epsilon)) + weightDecay * learningRate * paramsMatrix[j][k][g];
                    }
                }
            }
        }
    }

    @Override
    public void optimize(double[] params, double[] gradients) {
        if (!arrayParamsToPrevV.containsKey(params)) {
            double[] doubles = new double[params.length];
            ArraysUtils.fillArray(doubles, initValue);
            arrayParamsToPrevV.put(params, doubles);
        }

        double[] movingAverage = arrayParamsToPrevV.get(params);

        //form sum of g^2
        for (int i = 0; i < params.length; i++) {
            movingAverage[i] += gamma * movingAverage[i] + (1 - gamma) * Math.pow(gradients[i], 2);
        }

        //correct weights
        for (int i = 0; i < params.length; i++) {
            params[i] -= learningRate * gradients[i] / (Math.sqrt(movingAverage[i] + epsilon)) + weightDecay * learningRate * params[i];
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
        return learningRate;
    }

    @Override
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
}
