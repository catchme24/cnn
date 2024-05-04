package data;

import data.parser.MyDatasetParser;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Matrix3D;

import java.io.File;
import java.io.IOException;

public class DatasetHelperImplTest {

    @Test
    void calculateOutput() throws IOException {
        String trainPath = "D:\\cifar10_10\\train";
        String validPath = "D:\\cifar10_10\\valid";
        String testPath = "D:\\cifar10_10\\test";

        File train = new File(trainPath);
        File valid = new File(validPath);
        File test = new File(testPath);

        MyDatasetParser myDatasetParser = new MyDatasetParser();

        DatasetHelperImpl<Matrix3D, RealMatrix> helper = new DatasetHelperImpl<>();
        Dataset<Matrix3D, RealMatrix> dataset = helper.prepareDataset(train, valid, test, myDatasetParser);

        Assertions.assertEquals(10, dataset.getLabelsCount());
        Assertions.assertEquals(100, dataset.getTrainData().size());
        Assertions.assertEquals(100, dataset.getValidData().size());
        Assertions.assertEquals(100, dataset.getTestData().size());
    }
}
