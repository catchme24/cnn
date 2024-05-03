package data.dataset;

import java.io.File;
import java.util.List;

public interface DataFrameFormer<T, D> {

    public List<DataFrame<T, D>> getTrainData(File train);

    public List<DataFrame<T, D>> getTestData(File test);

    public List<DataFrame<T, D>> getValidData(File valid);
}
