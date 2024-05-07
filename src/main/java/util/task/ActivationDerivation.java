package util.task;

import function.activation.ActivationFunc;
import util.model.Matrix3D;

import java.util.concurrent.Callable;

public class ActivationDerivation implements Callable<Matrix3D> {

    private ActivationFunc activationFunc;
    private int indexStart;
    private int indexEnd;
    private Matrix3D input;

    public ActivationDerivation(ActivationFunc activationFunc,
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
            activationFunc.calculateDerivation(input.getMatrix3d()[i]);
        }
        return input;
    }
}
