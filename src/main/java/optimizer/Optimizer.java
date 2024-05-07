package optimizer;

public interface Optimizer {

    double optimize(double weight);

    double getLearingRate();
}
