package function.initializer;

import java.util.Random;

public class HeInitializer implements Initializer {

    private Random random;
    private int count;
    private double stdDev;

    public HeInitializer() {
        this(5);
    }

    public HeInitializer(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public double getValue() {
        return random.nextGaussian() * stdDev;
    }

    @Override
    public void setParams(double... params) {
        count = (int) params[0];
        stdDev = Math.sqrt(2.0 / count);
    }
}
