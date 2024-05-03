package data;

import data.dataset.BasicDataSet;
import data.dataset.DataFrameFormer;
import data.dataset.DataSet;
import data.dataset.DatasetHelper;

import java.io.File;
import java.io.IOException;

public class DatasetHelperImpl<T, D> implements DatasetHelper<T, D> {

    public DatasetHelperImpl() {}

    @Override
    public DataSet<T, D> prepareDataset(File train, File valid, File test, DataFrameFormer<T, D> former) throws IOException {
        BasicDataSet<T, D> dataset = new BasicDataSet<>();
        dataset.addTestDataFrame(former.getTestData(test));
        dataset.addTrainDataFrame(former.getTrainData(train));
        dataset.addValidDataFrame(former.getValidData(valid));
        return dataset;
    }

}
