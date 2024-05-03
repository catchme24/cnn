package data.dataset;

import data.converter.DatasetRowConverter;
import data.dataset.DataFrameFormer;
import data.dataset.DataSet;

import java.io.File;
import java.io.IOException;

public interface DatasetHelper<T, D> {

    DataSet<T, D> prepareDataset(File train, File valid, File test, DataFrameFormer<T, D> former) throws IOException;
}
