package data;

import java.io.File;
import java.io.IOException;

public class DatasetHelperImpl<T, D> implements DatasetHelper<T, D> {

    public DatasetHelperImpl() {}

    @Override
    public Dataset<T, D> prepareDataset(File train, File valid, File test, DataFrameFormer<T, D> former) throws IOException {
        BasicDataset<T, D> dataset = new BasicDataset<>();
        dataset.addTestDataFrame(former.getTestData(test));
        dataset.addTrainDataFrame(former.getTrainData(train));
        dataset.addValidDataFrame(former.getValidData(valid));
        dataset.setLabelsCount(former.getLabelsCount());
        return dataset;
    }

}
