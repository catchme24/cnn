package network.layer;

public interface Layer {

    Object propogateBackward(Object input);

    Object propogateForward(Object input);
    void correctWeights(double learnRate);
    void initWeight();
    void setPrevious(Layer layer);
    void unchain();
    Dimension getSize();
}
