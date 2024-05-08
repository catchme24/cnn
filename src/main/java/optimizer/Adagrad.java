package optimizer;

import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;
import util.model.Matrix3D;

import java.util.HashMap;
import java.util.Map;

public class Adagrad implements Optimizer {

    private double learningRate;
    private double epsilon;

    private Map<Matrix3D[], Matrix3D[]> tensorParamsToPrevV;

    private Map<Integer, RealMatrix> matrixParamsToPrevV;

    private Map<double[], double[]> arrayParamsToPrevV;

    public Adagrad() {
        this(0.001, 0.0000001);
    }

    public Adagrad(double epsilon) {
        this(0.001, epsilon);
    }

    public Adagrad(double learningRate, double epsilon) {
        this.epsilon = epsilon;
        this.learningRate = learningRate;
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
        RealMatrix prevGradientsPow = matrixParamsToPrevV.get(hashCode);

        //form sum of g^2
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
                prevGradientsPow.setEntry(i, j, prevGradientsPow.getEntry(i, j) + Math.pow(gradients.getEntry(i, j), 2));
            }
        }

        //correct weights
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
                params.setEntry(i, j, (params.getEntry(i, j) - (learningRate * gradients.getEntry(i, j)) / (Math.sqrt(prevGradientsPow.getEntry(i, j) + epsilon)) ));
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

        Matrix3D[] prevGradientsPow = tensorParamsToPrevV.get(params);

        //form currentV
        for (int i = 0; i < prevGradientsPow.length; i++) {
            double[][][] prevGradPowMatrix = prevGradientsPow[i].getMatrix3d();
            double[][][] gradientsMatrix = gradients[i].getMatrix3d();
            for (int j = 0; j < prevGradPowMatrix.length; j++) {
                for (int k = 0; k < prevGradPowMatrix[0].length; k++) {
                    for (int g = 0; g < prevGradPowMatrix[0][0].length; g++) {
                        prevGradPowMatrix[j][k][g] += Math.pow(gradientsMatrix[j][k][g], 2);
                    }
                }
            }
        }

//        System.out.println("КЕрнелы " + currentV[0].getMatrix3d()[0][0][0]);

        //correct weights
        for (int i = 0; i < params.length; i++) {
            double[][][] paramsMatrix = params[i].getMatrix3d();
            double[][][] prevGradPowMatrix = prevGradientsPow[i].getMatrix3d();
            double[][][] gradientsMatrix = gradients[i].getMatrix3d();
            for (int j = 0; j < paramsMatrix.length; j++) {
                for (int k = 0; k < paramsMatrix[0].length; k++) {
                    for (int g = 0; g < paramsMatrix[0][0].length; g++) {
                        paramsMatrix[j][k][g] -= learningRate * gradientsMatrix[j][k][g] / (Math.sqrt(prevGradPowMatrix[j][k][g] + epsilon));
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

        double[] prevGradientsPow = arrayParamsToPrevV.get(params);

        //form sum of g^2
        for (int i = 0; i < params.length; i++) {
            prevGradientsPow[i] += Math.pow(gradients[i], 2);
        }

        //correct weights
        for (int i = 0; i < params.length; i++) {
            params[i] -= learningRate * gradients[i] / (Math.sqrt(prevGradientsPow[i] + epsilon));
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
