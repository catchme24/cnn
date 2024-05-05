package data;

import org.apache.commons.math3.linear.RealMatrix;
import util.model.Matrix3D;

public class DataFrameImage implements DataFrame<Matrix3D, RealMatrix> {

    private Matrix3D sample;
    private RealMatrix oneHotEncoding;
    private String labalName;
    private int labelNumber;

    public DataFrameImage(Matrix3D sample, RealMatrix oneHotEncoding, String labelName, int labelNumber) {
        this.sample = sample;
        this.labalName = labelName;
        this.labelNumber = labelNumber;
        this.oneHotEncoding = oneHotEncoding;
    }

    @Override
    public Matrix3D getSample() {
        return sample;
    }

    @Override
    public RealMatrix getGroundTruth() {
        return oneHotEncoding;
    }

    @Override
    public int getLabelNumber() {
        return labelNumber;
    }

    @Override
    public String getLabelName() {
        return labalName;
    }
}
