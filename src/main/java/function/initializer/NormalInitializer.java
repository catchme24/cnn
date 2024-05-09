package function.initializer;

import java.util.Random;

public class NormalInitializer implements Initializer {

    private Random random;

    public NormalInitializer() {
        this(5);
    }

    public NormalInitializer(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public double getValue() {
        return random.nextGaussian();
    }

    @Override
    public void setParams(double... params) {

    }
}
