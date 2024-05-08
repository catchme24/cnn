package optimizer;

import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;
import util.model.Matrix3D;

import java.util.HashMap;
import java.util.Map;

public class SGD implements Optimizer {

    private double learningRate;
    private double saveCoefficient;

    private Map<Matrix3D[], Matrix3D[]> tensorParamsToPrevV;

    private Map<Integer, RealMatrix> matrixParamsToPrevV;

    private Map<double[], double[]> arrayParamsToPrevV;

    public SGD(double saveCoefficient) {
        this(0.001, saveCoefficient);
    }

    public SGD(double learningRate, double saveCoefficient) {
        this.saveCoefficient = saveCoefficient;
        this.learningRate = learningRate;
        this.tensorParamsToPrevV = new HashMap<>();
        this.matrixParamsToPrevV = new HashMap<>();
        this.arrayParamsToPrevV = new HashMap<>();
    }

    @Override
    public void optimize(RealMatrix params, RealMatrix gradients) {
        int hashCode = System.identityHashCode(params);

        if (!matrixParamsToPrevV.containsKey(hashCode)) {
//            System.out.println("первый раз матрицы");
            matrixParamsToPrevV.put(hashCode, MatrixUtils.createInstance(params.getRowDimension(), params.getColumnDimension()));
        }
//        RealMatrix copyOfCurrentValues = params.copy();
//        RealMatrix prevValues = matrixParamsToPrevV.get(params);

        RealMatrix prevV = matrixParamsToPrevV.get(hashCode);
        RealMatrix currentV = MatrixUtils.createInstance(params.getRowDimension(), params.getColumnDimension());

        //form currentV
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
//                double value = saveCoefficient * prevV.getEntry(i, j) + learningRate * gradients.getEntry(i, j);
                currentV.setEntry(i, j, learningRate * gradients.getEntry(i, j) - saveCoefficient * prevV.getEntry(i, j));
            }
        }

        System.out.println("Vcur " + currentV.getEntry(0, 0));
        System.out.println("Grad " + gradients.getEntry(0, 0));
        System.out.println("До к " + currentV.getEntry(0, 0));

        //correct weights
        for (int i = 0; i < params.getRowDimension(); i++) {
            for (int j = 0; j < params.getColumnDimension(); j++) {
//                System.out.println("До " + params.getEntry(i, j));
                params.setEntry(i, j, (params.getEntry(i, j) - currentV.getEntry(i, j)));
//                System.out.println("После " + params.getEntry(i, j));
            }
        }
        System.out.println("Посл " + currentV.getEntry(0, 0));
        matrixParamsToPrevV.put(hashCode, currentV);
    }

    @Override
    public void optimize(Matrix3D[] params, Matrix3D[] gradients) {
//        System.out.println(params);
        double[][][] kernel = params[0].getMatrix3d();
        if (!tensorParamsToPrevV.containsKey(params)) {
//            System.out.println("первый раз кернелы");
            Matrix3D[] empty = new Matrix3D[params.length];
            for (int i = 0; i < params.length; i++) {
                empty[i] = new Matrix3D(kernel.length, kernel[0].length, kernel[0][0].length);
            }
            tensorParamsToPrevV.put(params, empty);
        }

//        Matrix3D[] copyOfCurrentValues = params.copy();
//        Matrix3D[] prevValues = tensorParamsToPrevV.get(params);

        Matrix3D[] prevV = tensorParamsToPrevV.get(params);
        Matrix3D[] currentV = new Matrix3D[params.length];
        for (int i = 0; i < params.length; i++) {
            currentV[i] = new Matrix3D(kernel.length, kernel[0].length, kernel[0][0].length);
        }

        //form currentV
        for (int i = 0; i < currentV.length; i++) {
            double[][][] currentVmatrix = currentV[i].getMatrix3d();
            double[][][] prevVmatrix = prevV[i].getMatrix3d();
            double[][][] gradientsMatrix = gradients[i].getMatrix3d();
            for (int j = 0; j < currentVmatrix.length; j++) {
                for (int k = 0; k < currentVmatrix[0].length; k++) {
                    for (int g = 0; g < currentVmatrix[0][0].length; g++) {
//                        double value = saveCoefficient * prevVmatrix[j][k][g] + learningRate * gradientsMatrix[j][k][g];
                        currentVmatrix[j][k][g] = saveCoefficient * prevVmatrix[j][k][g] + learningRate * gradientsMatrix[j][k][g];
                    }
                }
            }
        }

//        System.out.println("КЕрнелы " + currentV[0].getMatrix3d()[0][0][0]);

        //correct weights
        for (int i = 0; i < currentV.length; i++) {
            double[][][] paramsMatrix = params[i].getMatrix3d();
            double[][][] currentVmatrix = currentV[i].getMatrix3d();
            for (int j = 0; j < currentVmatrix.length; j++) {
                for (int k = 0; k < currentVmatrix[0].length; k++) {
                    for (int g = 0; g < currentVmatrix[0][0].length; g++) {
                        paramsMatrix[j][k][g] += currentVmatrix[j][k][g];
                    }
                }
            }
        }

        tensorParamsToPrevV.put(params, currentV);
    }

    @Override
    public void optimize(double[] params, double[] gradients) {
        if (!arrayParamsToPrevV.containsKey(params)) {
//            System.out.println("первый раз массивы");
            arrayParamsToPrevV.put(params, new double[params.length]);
        }
//        double[] copyOfCurrentValues = Arrays.copyOf(params, params.length);
//        double[] prevValues = arrayParamsToPrevV.get(params);

        double[] prevV = arrayParamsToPrevV.get(params);
        double[] currentV = new double[params.length];

        //form currentV
        for (int i = 0; i < params.length; i++) {
//            double value = saveCoefficient * prevV[i] + learningRate * gradients[i];
            currentV[i] = saveCoefficient * prevV[i] + learningRate * gradients[i];
        }

//        System.out.println("Биасы " + currentV[0]);

        //correct weights
        for (int i = 0; i < params.length; i++) {
            params[i] -= currentV[i];
        }
        arrayParamsToPrevV.put(params, currentV);
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
