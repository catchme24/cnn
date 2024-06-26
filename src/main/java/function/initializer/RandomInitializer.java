package function.initializer;

import java.util.Random;

public class RandomInitializer implements Initializer {

    private Random random;

    public RandomInitializer() {
        this(5);
    }

    public RandomInitializer(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public double getValue() {
        return random.nextDouble() * 2 - 1;
    }

    @Override
    public void setParams(double... params) {

    }
}
