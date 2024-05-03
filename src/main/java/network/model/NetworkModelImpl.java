package network.model;

import com.google.gson.Gson;
import network.layer.Dimension;
import network.layer.Layer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class NetworkModelImpl extends AbstractNetworkModel {

    public NetworkModelImpl(Deque<Layer> layers) {
        super(layers);
    }


    public void initFromFile(String path) {
        Gson gson = new Gson();

        String bigObject = null;

        Deque<Layer> layers = new LinkedList<>();
        Dimension imageDimension = new Dimension(3, 32, 32);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            bigObject = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
