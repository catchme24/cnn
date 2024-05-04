package data;

import data.DataFrame;

import java.io.File;
import java.util.List;

public interface DataFrameFormer<T, D> {

    List<DataFrame<T, D>> getTrainData(File train);

    List<DataFrame<T, D>> getTestData(File test);

    List<DataFrame<T, D>> getValidData(File valid);

    int getLabelsCount();
}
