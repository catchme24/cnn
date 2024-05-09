package function.initializer;

import java.util.Random;

public class LecunItializer implements Initializer {

    private Random random;
    private int countIn;

    private double stdDev;

    public LecunItializer() {
        this.random = new Random();
    }

    @Override
    public double getValue() {
        return random.nextGaussian() * stdDev;
    }

    @Override
    public void setParams(double... params) {
        countIn = (int) params[0];
        stdDev = Math.sqrt(1.0 / countIn);
    }
}
