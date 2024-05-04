package data;

import java.util.List;

public interface Dataset<T, D> {

    List<DataFrame<T, D>> getTrainData();

    List<DataFrame<T, D>>  getTestData();

    List<DataFrame<T, D>>  getValidData();

    void shuffleDataset();

    int getLabelsCount();
}
