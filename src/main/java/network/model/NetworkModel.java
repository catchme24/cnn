package network.model;

import java.io.File;

public interface NetworkModel {

    Object propogateForward(Object input);

    Object propogateBackward(Object input);

//    void buildFromFile(File file);

    void correctWeights(double learnRate);

}
