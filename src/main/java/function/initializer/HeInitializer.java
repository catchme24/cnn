package function.initializer;

import java.util.Random;

public class HeInitializer implements Initializer {

    private Random random;
    private int count;
    private double stdDev;

    public HeInitializer() {
        this.random = new Random();
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
