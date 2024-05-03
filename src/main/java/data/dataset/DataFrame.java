package data.dataset;

public interface DataFrame<T, R> {

    T getSample();

    R getGroundTruth();

    int getLabelNumber();

    String getLabelName();
}
