package network.model;

import optimizer.Optimizer;

import java.io.File;
import java.io.IOException;

public interface NetworkModel<T, D> {

    D propogateForward(T input);
    T propogateBackward(D input);
    boolean saveInFile(File file);
    void correctWeights(Optimizer optimizer);
}
