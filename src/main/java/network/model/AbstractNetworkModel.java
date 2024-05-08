package network.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.json.RealMatrixDeserializer;
import network.layer.Dimension;
import network.layer.Layer;
import network.layer.Layer2D;
import network.layer.LearningLayer;
import optimizer.Optimizer;
import org.apache.commons.math3.linear.RealMatrix;
import util.MatrixUtils;
import util.model.Matrix3D;

import java.io.*;
import java.util.*;

public abstract class AbstractNetworkModel implements NetworkModel {

    protected Deque<Layer> layers;
    protected List<Object> params;
    protected Gson saver;

    public AbstractNetworkModel(Deque<Layer> layers) {
        this.layers = layers;
        this.saver = new GsonBuilder().create();
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
    public void correctWeights(Optimizer optimizer) {
        for (Layer layer: this.layers) {
            if (layer instanceof LearningLayer) {
                LearningLayer learningLayer = (LearningLayer) layer;
                optimizer.optimize(learningLayer.getWeights(), learningLayer.getWeightsGrad());
                optimizer.optimize(learningLayer.getBaises(), learningLayer. getBaisesGrad());
            }
        }
    }

    @Override
    public boolean saveInFile(File file) {
        if (file == null) {
            return true;
        }

        List<String> stringLayers = new ArrayList<>();

        for (Layer layer : layers) {
            layer.unchain();
        }

        for (Layer layer : layers) {
            stringLayers.add(saver.toJson(layer));
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath())))) {
            for (String stringLayer: stringLayers) {
                bw.write(stringLayer);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
