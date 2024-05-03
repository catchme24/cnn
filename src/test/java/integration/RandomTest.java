package integration;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class RandomTest {

    @Test
    public void test() {

        Random random = new Random();

        System.out.println(random.nextGaussian());
        System.out.println(random.nextGaussian());
        System.out.println(random.nextGaussian());
        System.out.println(random.nextGaussian());
        System.out.println(random.nextGaussian());
        System.out.println(random.nextGaussian());
        System.out.println(random.nextGaussian());
        System.out.println(random.nextGaussian());
        System.out.println(random.nextGaussian());
        System.out.println(random.nextGaussian());
        System.out.println(random.nextGaussian());
    }
}
