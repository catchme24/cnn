package data;

import java.io.File;
import java.io.IOException;

public interface DatasetHelper<T, D> {

    Dataset<T, D> prepareDataset(File train, File valid, File test, DataFrameFormer<T, D> former) throws IOException;

    Dataset<T, D> prepareDataset(File test, DataFrameFormer<T, D> former) throws IOException;
}
