package network.layer;

public interface LearningLayer {

    Object getWeights();

    Object getBaises();

    Object getWeightsGrad();

    Object getBaisesGrad();
    void initWeightsAndBaises();
}
