package data;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class BasicDataset<T, D> implements Dataset<T, D> {

    private List<DataFrame<T, D>> train;
    private List<DataFrame<T, D>> test;
    private List<DataFrame<T, D>> valid;
    private int labelsCount;

    public BasicDataset() {
    }

    public void setLabelsCount(int labelsCount) {
        this.labelsCount = labelsCount;
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
        Collections.shuffle(train);
    }

    @Override
    public int getLabelsCount() {
        return labelsCount;
    }
}