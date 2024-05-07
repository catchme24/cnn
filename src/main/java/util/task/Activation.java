package util.task;

import function.activation.ActivationFunc;
import util.ActivationParallelUtils;
import util.ConvolutionUtils;
import util.model.Matrix3D;

import java.util.concurrent.Callable;

public class Activation implements Callable<Matrix3D> {

    private ActivationFunc activationFunc;
    private int indexStart;
    private int indexEnd;
    private Matrix3D input;

    public Activation(ActivationFunc activationFunc,
                      int indexStart,
                      int indexEnd,
                      Matrix3D input) {

        this.activationFunc = activationFunc;
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
        this.input = input;
    }

    @Override
    public Matrix3D call() throws Exception {
        for(int i = indexStart; i <= indexEnd; i++){
            activationFunc.calculate(input.getMatrix3d()[i]);
        }
        return input;
    }
}
