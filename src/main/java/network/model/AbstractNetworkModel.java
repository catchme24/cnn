package network.model;

import network.layer.Layer;
import network.layer.Layer2D;
import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractNetworkModel implements NetworkModel {

    protected final Deque<Layer> layers;

    public AbstractNetworkModel(Deque<Layer> layers) {
        this.layers = layers;
        Layer prev = null;

        for (Layer layer: layers) {
            layer.setPrevious(prev);
            prev = layer;
        }
    }


    @Override
    public Object propogateForward(Object input) {
        Object output = input;
        for (Layer layer: layers) {
            output = layer.propogateForward(output);
        }
        return output;
    }

    @Override
    public Object propogateBackward(Object input) {
        Iterator<Layer> it = layers.descendingIterator();
        Object output = input;

        while (it.hasNext()) {
            Layer layer = it.next();
            output = layer.propogateBackward(output);
        }
        return output;
    }

    @Override
    public void correctWeights(double learnRate) {
        for (Layer layer: layers) {
            layer.correctWeights(learnRate);
        }
    }
}
