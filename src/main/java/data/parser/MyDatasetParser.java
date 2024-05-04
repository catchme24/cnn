package data.parser;

import data.DataFrame;
import data.DataFrameFormer;
import data.DataFrameImage;
import org.apache.commons.math3.linear.RealMatrix;
import util.FileSystemUtils;
import util.Matrix3D;
import util.MatrixUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyDatasetParser implements DataFrameFormer<Matrix3D, RealMatrix> {

    private int labelsCount;

    @Override
    public List<DataFrame<Matrix3D, RealMatrix>> getTrainData(File train) {
        return fillListFromFile(train);
    }

    @Override
    public List<DataFrame<Matrix3D, RealMatrix>> getTestData(File test) {
        return fillListFromFile(test);
    }

    @Override
    public List<DataFrame<Matrix3D, RealMatrix>> getValidData(File valid) {
        return fillListFromFile(valid);
    }

    @Override
    public int getLabelsCount() {
        return labelsCount;
    }

    private List<DataFrame<Matrix3D, RealMatrix>> fillListFromFile(File file) {
        List<DataFrame<Matrix3D, RealMatrix>> result = new ArrayList<>();
        Map<String, List<BufferedImage>> imagesWithLabels = FileSystemUtils.getAllFilesInSubDirectories(file);
        int labelsCount = imagesWithLabels.keySet().size();
        int labelNumber = 1;

        this.labelsCount = labelsCount;

        for (Map.Entry<String, List<BufferedImage>> entry: imagesWithLabels.entrySet()) {
            String labelName = entry.getKey();
            for (BufferedImage image: entry.getValue()) {
                Matrix3D dataMatri3d = MatrixUtils.getDataFrame(image);
                RealMatrix oneHotEncoding = MatrixUtils.createOneHotEncoding(labelNumber, labelsCount);
                DataFrame<Matrix3D, RealMatrix> dataFrame = new DataFrameImage(dataMatri3d,
                        oneHotEncoding,
                        labelName,
                        labelNumber);

                result.add(dataFrame);
            }
            labelNumber++;
        }
        return result;
    }
}
