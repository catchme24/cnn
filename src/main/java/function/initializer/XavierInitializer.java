package function.initializer;

import java.util.Random;

public class XavierInitializer implements Initializer {

    private Random random;
    private int countIn;

    private int countOut;
    private double stdDev;

    public XavierInitializer() {
        this.random = new Random();
    }

    @Override
    public double getValue() {
        return random.nextGaussian() * stdDev;
    }

    @Override
    public void setParams(double... params) {
        countIn = (int) params[0];
        countOut = (int) params[1];
        stdDev = Math.sqrt(2.0 / (countIn + countOut));
    }
}
