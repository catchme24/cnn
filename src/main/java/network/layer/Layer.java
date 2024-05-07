package network.layer;

import optimizer.Optimizer;

public interface Layer {

    Object propogateBackward(Object input);
    Object propogateForward(Object input);
    void correctWeights(Optimizer optimizer);
    void initWeight();
    void setPrevious(Layer layer);
    void unchain();
    Dimension getSize();
}
