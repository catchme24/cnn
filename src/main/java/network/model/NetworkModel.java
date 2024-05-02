package network.model;

public interface NetworkModel {

    Object propogateForward(Object input);

    Object propogateBackward(Object input);

    void correctWeights(double learnRate);

}
