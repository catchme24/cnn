package network.layer;

import lombok.extern.slf4j.Slf4j;
import network.NetworkConfigException;
import org.apache.commons.math3.linear.RealMatrix;
import util.Matrix3D;
import util.MatrixUtils;

@Slf4j
public class Flatten implements Layer3Dto2D {

    private Matrix3D preActivation;

    private RealMatrix postActivation;

    private Layer previousLayer;

    private Dimension inputDimension;

    private Dimension outputDimension;

    public Flatten() {
    }

    @Override
    public void unchain() {
        previousLayer = null;
    }

    @Override
    public void initWeight() {

    }

    @Override
    public void setPrevious(Layer previous) {
        if (!(previous instanceof Layer3D)) {
            throw new NetworkConfigException("Prev layer for Flatten must be child of Layer3D");
        }

        if (previous == null) {
            throw new NetworkConfigException("Prev layer for Flatten cannot be null!");
        }
        previousLayer = previous;

        Dimension outputDimensionOfPrevLayer = previous.getSize();
        inputDimension = new Dimension( outputDimensionOfPrevLayer.getChannel(),
                                        outputDimensionOfPrevLayer.getHeightTens(),
                                        outputDimensionOfPrevLayer.getWidthTens(),
                                        outputDimensionOfPrevLayer.getStride(),
                                        outputDimensionOfPrevLayer.getHeightKernel(),
                                        outputDimensionOfPrevLayer.getWidthKernel());

        int calcHeight = inputDimension.getChannel() * inputDimension.getHeightTens() * inputDimension.getWidthTens();
        outputDimension = new Dimension(0, calcHeight, 0);

        log.debug("Activation3DLayer layer: {} prev size", previous.getSize());
    }

    @Override
    public Object propogateForward(Object input) {
        return propogateForward((Matrix3D) input);
    }

    @Override
    public Object propogateBackward(Object input) {
        return propogateBackward((RealMatrix) input);
    }

    @Override
    public RealMatrix propogateForward(Matrix3D inputTensor) {
        int size = inputTensor.getCountOfItems();
        double[][][] matrix3d = inputTensor.getMatrix3d();
        RealMatrix output = MatrixUtils.createEmptyVector(size);
        int index = 0;
        for (int i = 0; i < matrix3d.length; i++){
            for (int j = 0; j < matrix3d[0].length; j++){
                for (int k = 0; k < matrix3d[0][0].length; k++){
                    output.setEntry(index, 0, matrix3d[i][j][k]);
                    index++;
                }
            }
        }
        return output;
    }

    @Override
    public Matrix3D propogateBackward(RealMatrix inputVector) {
        double[][][] errorTensor = new double[inputDimension.getChannel()]
                                            [inputDimension.getWidthTens()]
                                            [inputDimension.getHeightTens()];
        int index = 0;
        for (int i = 0; i < errorTensor.length; i++){
            for (int j = 0; j < errorTensor[0].length; j++){
                for (int k = 0; k < errorTensor[0][0].length; k++){
                    errorTensor[i][j][k] = inputVector.getEntry(index, 0);
                    index++;
                }
            }
        }
        return new Matrix3D(errorTensor);
    }

    @Override
    public void correctWeights(double learnRate) {

    }

    @Override
    public Dimension getSize() {
        return outputDimension;
    }
}
