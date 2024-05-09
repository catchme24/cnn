package optimizer;

import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;
import util.model.Matrix3D;

public class GD implements Optimizer {

    private double learningRate;

    private double weightDecay;

    public GD() {
        this(0.001, 0.0);
    }

    public GD(double learningRate, double weightDecay) {
        this.learningRate = learningRate;
        this.weightDecay = weightDecay;
    }

    @Override
    public void optimize(RealMatrix params, RealMatrix gradients) {
        //correct weights
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
                params.setEntry(i, j, (params.getEntry(i, j) - learningRate * gradients.getEntry(i, j) - weightDecay * learningRate * params.getEntry(i, j)));
            }
        }
//        System.out.println("Весс " + params.getEntry(0, 0));
//        System.out.println("Град " + gradients.getEntry(0, 0));
    }

    @Override
    public void optimize(Matrix3D[] params, Matrix3D[] gradients) {
        //correct weights
        for (int i = 0; i < params.length; i++) {
            double[][][] paramsMatrix = params[i].getMatrix3d();
            double[][][] gradientsMatrix = gradients[i].getMatrix3d();
            for (int j = 0; j < paramsMatrix.length; j++) {
                for (int k = 0; k < paramsMatrix[0].length; k++) {
                    for (int g = 0; g < paramsMatrix[0][0].length; g++) {
                        paramsMatrix[j][k][g] -= learningRate * gradientsMatrix[j][k][g] + weightDecay * learningRate * paramsMatrix[j][k][g];
                    }
                }
            }
        }

    }

    @Override
    public void optimize(double[] params, double[] gradients) {
        //correct weights
        for (int i = 0; i < params.length; i++) {
            params[i] -= learningRate * gradients[i] + weightDecay * learningRate * params[i];
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
