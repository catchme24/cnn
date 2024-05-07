package optimizer;

public class SGD implements Optimizer {

    private double learningRate;

    public SGD(double learningRate) {
        this.learningRate = learningRate;
    }

    @Override
    public double optimize(double weight) {
        return weight * learningRate;
    }

    @Override
    public double getLearingRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
}
