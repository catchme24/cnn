package data.dataset;

import data.DatasetItem;

import java.util.List;

public interface DataSet<T, D> {

    List<DataFrame<T, D>> getTrainData();

    List<DataFrame<T, D>>  getTestData();

    List<DataFrame<T, D>>  getValidData();

    void shuffleDataset();

    int getClassesCount();
}
