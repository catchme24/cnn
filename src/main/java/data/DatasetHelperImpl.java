package data;

import java.io.File;
import java.io.IOException;

public class DatasetHelperImpl<T, D> implements DatasetHelper<T, D> {

    public DatasetHelperImpl() {}

    @Override
    public Dataset<T, D> prepareDataset(File train, File valid, File test, DataFrameFormer<T, D> former) throws IOException {
        System.out.println(train.getAbsolutePath());
        System.out.println(valid.getAbsolutePath());
        System.out.println(test.getAbsolutePath());

        BasicDataset<T, D> dataset = new BasicDataset<>();
        dataset.addTestDataFrame(former.getTestData(test));
        dataset.addTrainDataFrame(former.getTrainData(train));
        dataset.addValidDataFrame(former.getValidData(valid));
        dataset.setLabelsCount(former.getLabelsCount());
        System.out.println("helper ended");
        return dataset;
    }

    @Override
    public Dataset<T, D> prepareDataset(File test, DataFrameFormer<T, D> former) throws IOException {
        BasicDataset<T, D> dataset = new BasicDataset<>();
        dataset.addTestDataFrame(former.getTestData(test));
        dataset.setLabelsCount(former.getLabelsCount());
        return dataset;
    }

}
