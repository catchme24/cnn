package function.activation;

import function.activation.ActivationFunc;

public class LeakyReLu implements ActivationFunc {

    @Override
    public double calculate(double x) {
        return x > 0 ? x : 0.1 * x;
    }

    @Override
    public double calculateDerivation(double x) {
        return x > 0 ? 1 : 0.1;
    }
}
