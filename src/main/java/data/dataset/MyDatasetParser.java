package data.dataset;

import org.apache.commons.math3.linear.RealMatrix;
import util.FileSystemUtils;
import util.Matrix3D;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyDatasetParser implements DataFrameFormer<Matrix3D, RealMatrix> {
    @Override
    public List<DataFrame<Matrix3D, RealMatrix>> getTrainData(File train) {
        List<DataFrame<Matrix3D, RealMatrix>> trainList = new ArrayList<>();
        FileSystemUtils.instance().getDataFrame(null);
        return null;
    }

    @Override
    public List<DataFrame<Matrix3D, RealMatrix>> getTestData(File test) {
        List<DataFrame<Matrix3D, RealMatrix>> testList = new ArrayList<>();
        return null;
    }

    @Override
    public List<DataFrame<Matrix3D, RealMatrix>> getValidData(File valid) {
        List<DataFrame<Matrix3D, RealMatrix>> validList = new ArrayList<>();
        return null;
    }
}
