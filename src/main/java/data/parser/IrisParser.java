package data.parser;

import data.DataFrame;
import data.DataFrameFormer;
import org.apache.commons.math3.linear.RealMatrix;
import util.FileSystemUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IrisParser implements DataFrameFormer<RealMatrix, RealMatrix> {

    private int labelsCount;

    @Override
    public List<DataFrame<RealMatrix, RealMatrix>> getTrainData(File train) {
        return fillListFromFile(train);
    }

    @Override
    public List<DataFrame<RealMatrix, RealMatrix>> getTestData(File test) {
        return fillListFromFile(test);
    }

    @Override
    public List<DataFrame<RealMatrix, RealMatrix>> getValidData(File valid) {
        return fillListFromFile(valid);
    }

    @Override
    public int getLabelsCount() {
        return labelsCount;
    }

    private List<DataFrame<RealMatrix, RealMatrix>> fillListFromFile(File file) {
        List<DataFrame<RealMatrix, RealMatrix>> result = new ArrayList<>();
        Map<String, List<BufferedImage>> imagesWithLabels = FileSystemUtils.getAllFilesInSubDirectories(file);
        int labelsCount = imagesWithLabels.keySet().size();
        int labelNumber = 1;

        return result;
    }
}
