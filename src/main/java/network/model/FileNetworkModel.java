package network.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import network.layer.ConvolutionLayer;
import network.layer.Layer;

import java.io.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class FileNetworkModel extends AbstractNetworkModel {

    public FileNetworkModel(Deque<Layer> layers, File file) {
        super(layers);
        formFromFile(file);
    }

    private void formFromFile(File file) {
        Gson gson = new GsonBuilder()
//                .setLenient()
                .create();

        String stringLayer = null;

        Deque<Layer> parsedLayers = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath())))) {
            for (Layer layer: layers) {
                stringLayer = br.readLine();
                System.out.println(stringLayer);
                Layer parsedLayer = gson.fromJson(stringLayer, layer.getClass());
                parsedLayers.add(parsedLayer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.layers = parsedLayers;
        Layer prev = null;

        for (Layer layer: parsedLayers) {
            layer.setPrevious(prev);
            prev = layer;
        }
    }
}
