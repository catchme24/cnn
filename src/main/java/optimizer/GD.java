package optimizer;

public class GD implements Optimizer {

    private double learningRate;

    public GD(double learningRate) {
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
