package data.dataset;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3D;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BasicDataSet<T, D> implements DataSet<T, D> {

    private List<DataFrame<T, D>> train;
    private List<DataFrame<T, D>> test;
    private List<DataFrame<T, D>> valid;

    public BasicDataSet() {
    }

    public void addTestDataFrame(List<DataFrame<T, D>> testData) {
        test = testData;
    }

    public void addTrainDataFrame(List<DataFrame<T, D>> trainData) {
        train = trainData;
    }

    public void addValidDataFrame(List<DataFrame<T, D>> validData) {
        valid = validData;
    }

    @Override
    public List<DataFrame<T, D>> getTrainData() {
        return train;
    }

    @Override
    public List<DataFrame<T, D>> getTestData() {
        return test;
    }

    @Override
    public List<DataFrame<T, D>> getValidData() {
        return valid;
    }

    @Override
    public void shuffleDataset() {

    }

    @Override
    public int getClassesCount() {
        return 0;
    }
}