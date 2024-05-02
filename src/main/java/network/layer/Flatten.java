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
    public Object propogateBackward(Object input) {
        return propogateBackward((Matrix3D) input);
    }

    @Override
    public Object propogateForward(Object input) {
        return propogateBackward((Matrix3D) input);
    }

    @Override
    public void correctWeights(double learnRate) {

    }

    @Override
    public RealMatrix propogateBackward(Matrix3D inputTensor) {
        int size = inputTensor.getCountOfItems();

        double[][][] matrix3d = inputTensor.getMatrix3d();

        RealMatrix output = MatrixUtils.createEmptyVector(size);


        // TO-DO cast Matrix3D to RealMatrix
        return output;
    }

    @Override
    public Matrix3D propogateForward(RealMatrix inputVector) {
        // TO-DO cast RealMatrix to Matrix3D
        return null;
    }

    @Override
    public Dimension getSize() {
        return outputDimension;
    }
}
