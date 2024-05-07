package function.initializer;

import java.util.Random;

public class RandomInitializer implements Initializer {

    private Random random;

    public RandomInitializer() {
        this.random = new Random();
    }

    @Override
    public double getValue() {
        return random.nextGaussian();
    }

    @Override
    public void setParams(double... params) {

    }
}
