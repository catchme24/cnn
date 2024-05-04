package network.model;

import com.google.gson.Gson;
import network.layer.Dimension;
import network.layer.Layer;
import network.layer.Layer2D;
import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;

import java.io.*;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractNetworkModel implements NetworkModel {

    protected Deque<Layer> layers;

    public AbstractNetworkModel(Deque<Layer> layers) {
        this.layers = layers;
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

//    @Override
//    public void buildFromFile(File file) {
//        Gson gson = new Gson();
//
//        String bigObject = null;
//
//        Deque<Layer> layers = new LinkedList<>();
//        Dimension imageDimension = new Dimension(3, 32, 32);
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
//            bigObject = br.readLine();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
