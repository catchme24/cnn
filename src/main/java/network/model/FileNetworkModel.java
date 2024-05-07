package network.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.json.RealMatrixDeserializer;
import network.layer.Activation3DLayer;
import network.layer.ActivationLayer;
import network.layer.Layer;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.*;
import java.util.Deque;
import java.util.LinkedList;

public class FileNetworkModel extends AbstractNetworkModel {

    private static final Class[] NOT_LOADED_CLASSES = {ActivationLayer.class, Activation3DLayer.class};

    private Gson loader;

    public FileNetworkModel(Deque<Layer> layers, File load) {
        super(layers);
        this.loader = new GsonBuilder()
                .registerTypeAdapter(RealMatrix.class, new RealMatrixDeserializer())
                .create();

        this.layers =  loadLayersFromFile(load);
        Layer prev = null;

        for (Layer layer: layers) {
            layer.setPrevious(prev);
            prev = layer;
        }
    }

    private Deque<Layer> loadLayersFromFile(File load) {
        String stringLayer = null;

        Deque<Layer> parsedLayers = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(load.getAbsolutePath())))) {
            for (Layer layer: layers) {
                stringLayer = br.readLine();
                boolean clazzFromNotLoaded = false;
                for (Class clazz: NOT_LOADED_CLASSES) {
                    if (layer.getClass().equals(clazz)) {
                        clazzFromNotLoaded = true;
                        break;
                    }
                }
                if (clazzFromNotLoaded) {
                    parsedLayers.add(layer);
                } else {
                    Layer parsedLayer = loader.fromJson(stringLayer, layer.getClass());
                    parsedLayers.add(parsedLayer);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return parsedLayers;
    }
}
