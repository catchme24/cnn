package optimizer;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;
import util.model.Matrix3D;

import java.util.HashMap;
import java.util.Map;


/*
 РЕКОМЕНДУЕТСЯ stepsMovingGrad = 10, stepsMovingGradSqr = 1000 по ХАБРУ, что означает отключение корректировок моментов M и V
 после 10 и 1000 шага соотвественно
*/
public class Adam implements Optimizer {

    private double learningRate;
    private double epsilon;

    private double beta1;

    private double beta2;

    private Map<Matrix3D[], Integer> tensorParamsToCountStep;

    private Map<Integer, Integer> matrixParamsToCountStep;

    private Map<double[], Integer> arrayParamsToCountStep;

    private Map<Matrix3D[], Matrix3D[]> tensorParamsToMovingGrad;

    private Map<Integer, RealMatrix> matrixParamsToMovingGrad;

    private Map<double[], double[]> arrayParamsToMovingGrad;

    private Map<Matrix3D[], Matrix3D[]> tensorParamsToMovingGradSqr;

    private Map<Integer, RealMatrix> matrixParamsToMovingGradSqr;

    private Map<double[], double[]> arrayParamsToMovingGradSqr;

    private int stepsMovingGrad;
    private int stepsMovingGradSqr;

    private double weightDecay;

    public Adam(int stepsMovingGrad, int stepsMovingGradSqr) {
        this(0.0000001, 0.9, 0.999, stepsMovingGrad, stepsMovingGradSqr);
    }

    public Adam(double epsilon, double beta1, double beta2, int stepsMovingGrad, int stepsMovingGradSqr) {
        this(0.001, epsilon, beta1, beta2, stepsMovingGrad, stepsMovingGradSqr);
    }

    public Adam(double learningRate, double epsilon, double beta1, double beta2, int stepsMovingGrad, int stepsMovingGradSqr) {
        this.stepsMovingGrad = stepsMovingGrad;
        this.stepsMovingGradSqr = stepsMovingGradSqr;
        this.epsilon = epsilon;
        this.learningRate = learningRate;
        this.beta1 = beta1;
        this.beta2 = beta2;
        this.tensorParamsToMovingGrad = new HashMap<>();
        this.tensorParamsToMovingGradSqr = new HashMap<>();
        this.matrixParamsToMovingGrad = new HashMap<>();
        this.matrixParamsToMovingGradSqr = new HashMap<>();
        this.arrayParamsToMovingGrad = new HashMap<>();
        this.arrayParamsToMovingGradSqr = new HashMap<>();

        this.tensorParamsToCountStep = new HashMap<>();
        this.arrayParamsToCountStep = new HashMap<>();
        this.matrixParamsToCountStep = new HashMap<>();
    }

    @Override
    public void optimize(RealMatrix params, RealMatrix gradients) {
        int hashCode = System.identityHashCode(params);
        int step = matrixParamsToCountStep.get(hashCode) == null ? 1000 : matrixParamsToCountStep.get(hashCode) + 1;
        matrixParamsToCountStep.put(hashCode, step);


        if (!matrixParamsToMovingGrad.containsKey(hashCode)) {
            matrixParamsToMovingGrad.put(hashCode, MatrixUtils.createInstance(params.getRowDimension(), params.getColumnDimension()));
        }
        if (!matrixParamsToMovingGradSqr.containsKey(hashCode)) {
            matrixParamsToMovingGradSqr.put(hashCode, MatrixUtils.createInstance(params.getRowDimension(), params.getColumnDimension()));
        }

        RealMatrix movingGrad = matrixParamsToMovingGrad.get(hashCode);
        RealMatrix movingGradSqr = matrixParamsToMovingGradSqr.get(hashCode);


        //form moving for grad
        //form moving for grad square (grad^2)
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
                movingGrad.setEntry(i, j, beta1 * movingGrad.getEntry(i, j) + (1 - beta1) * gradients.getEntry(i, j));
                movingGradSqr.setEntry(i, j, beta2 * movingGradSqr.getEntry(i, j) + (1 - beta2) * Math.pow(gradients.getEntry(i, j), 2));

                if (step < stepsMovingGrad) {
                    movingGrad.setEntry(i, j, movingGrad.getEntry(i, j) / (1 - Math.pow(beta1, step)) );

                }
                if (step < stepsMovingGradSqr) {
                    movingGradSqr.setEntry(i, j, movingGradSqr.getEntry(i, j) / (1 - Math.pow(beta2, step)));
                }
            }
        }

//        System.out.println("-----------");
//        System.out.println("Moving grad       : " + movingGrad.getEntry(0, 0));
//        System.out.println("Moving grad sqr   : " + movingGradSqr.getEntry(0, 0));
//        System.out.println("Grad              : " + gradients.getEntry(0, 0));

        //correct weights
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
                params.setEntry(i, j, (params.getEntry(i, j) - (learningRate * movingGrad.getEntry(i, j)) / (Math.sqrt(movingGradSqr.getEntry(i, j) + epsilon)) - weightDecay * learningRate * params.getEntry(i, j) ));
            }
        }

    }

    @Override
    public void optimize(Matrix3D[] params, Matrix3D[] gradients) {
        int step = tensorParamsToCountStep.get(params) == null ? 1000 : tensorParamsToCountStep.get(params) + 1;
        tensorParamsToCountStep.put(params, step);

        double[][][] kernel = params[0].getMatrix3d();

        if (!tensorParamsToMovingGrad.containsKey(params)) {
            Matrix3D[] empty = new Matrix3D[params.length];
            for (int i = 0; i < params.length; i++) {
                empty[i] = new Matrix3D(kernel.length, kernel[0].length, kernel[0][0].length);
            }
            tensorParamsToMovingGrad.put(params, empty);
        }

        if (!tensorParamsToMovingGradSqr.containsKey(params)) {
            Matrix3D[] empty = new Matrix3D[params.length];
            for (int i = 0; i < params.length; i++) {
                empty[i] = new Matrix3D(kernel.length, kernel[0].length, kernel[0][0].length);
            }
            tensorParamsToMovingGradSqr.put(params, empty);
        }

        Matrix3D[] movingGrad = tensorParamsToMovingGrad.get(params);
        Matrix3D[] movingGradSqr = tensorParamsToMovingGradSqr.get(params);


        //form moving for grad
        //form moving for grad square (grad^2)
        for (int i = 0; i < movingGrad.length; i++) {
            double[][][] movingGradMatrix = movingGrad[i].getMatrix3d();
            double[][][] movingGradSqrMatrix = movingGradSqr[i].getMatrix3d();
            double[][][] gradientsMatrix = gradients[i].getMatrix3d();
            for (int j = 0; j < movingGradMatrix.length; j++) {
                for (int k = 0; k < movingGradMatrix[0].length; k++) {
                    for (int g = 0; g < movingGradMatrix[0][0].length; g++) {
                        movingGradMatrix[j][k][g] += beta1 * movingGradMatrix[j][k][g] + (1 - beta1) * gradientsMatrix[j][k][g];
                        movingGradSqrMatrix[j][k][g] += beta2 * movingGradSqrMatrix[j][k][g] + (1 - beta2) * Math.pow(gradientsMatrix[j][k][g], 2);

                        if (step < stepsMovingGrad) {
                            movingGradMatrix[j][k][g] /= 1 - Math.pow(beta1, step);
                        }
                        if (step < stepsMovingGradSqr) {
                            movingGradSqrMatrix[j][k][g] /= 1 - Math.pow(beta2, step);
                        }
                    }
                }
            }
        }

//        System.out.println("КЕрнелы " + currentV[0].getMatrix3d()[0][0][0]);

        //correct weights
        for (int i = 0; i < params.length; i++) {
            double[][][] paramsMatrix = params[i].getMatrix3d();
            double[][][] movingGradMatrix = movingGrad[i].getMatrix3d();
            double[][][] movingGradSqrMatrix = movingGradSqr[i].getMatrix3d();
            double[][][] gradientsMatrix = gradients[i].getMatrix3d();
            for (int j = 0; j < paramsMatrix.length; j++) {
                for (int k = 0; k < paramsMatrix[0].length; k++) {
                    for (int g = 0; g < paramsMatrix[0][0].length; g++) {
                        paramsMatrix[j][k][g] -= learningRate * movingGradMatrix[j][k][g] / (Math.sqrt(movingGradSqrMatrix[j][k][g] + epsilon));
                    }
                }
            }
        }
    }

    @Override
    public void optimize(double[] params, double[] gradients) {
        int step = arrayParamsToCountStep.get(params) == null ? 1000 : arrayParamsToCountStep.get(params) + 1;
        arrayParamsToCountStep.put(params, step);

        if (!arrayParamsToMovingGrad.containsKey(params)) {
            arrayParamsToMovingGrad.put(params, new double[params.length]);
        }

        if (!arrayParamsToMovingGradSqr.containsKey(params)) {
            arrayParamsToMovingGradSqr.put(params, new double[params.length]);
        }

        double[] movingGrad = arrayParamsToMovingGrad.get(params);
        double[] movingGradSqr = arrayParamsToMovingGradSqr.get(params);

        //form moving for grad
        //form moving for grad square (grad^2)
        for (int i = 0; i < params.length; i++) {
            movingGrad[i] += beta1 * movingGrad[i] + (1 - beta1) * gradients[i];
            movingGradSqr[i] += beta2 * movingGradSqr[i] + (1 - beta2) * Math.pow(gradients[i], 2);

            if (step < stepsMovingGrad) {
                movingGrad[i] /= 1 - Math.pow(beta1, step);
            }
            if (step < stepsMovingGradSqr) {
                movingGradSqr[i] /= 1 - Math.pow(beta2, step);
            }
        }

        //correct weights
        for (int i = 0; i < params.length; i++) {
            params[i] -= learningRate * movingGrad[i] / (Math.sqrt(movingGradSqr[i] + epsilon));
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
