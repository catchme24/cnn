package GUI.model;

import data.Dataset;
import data.DatasetHelperImpl;
import data.parser.MyDatasetParser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import network.TrainableNetwork;
import network.builder.NetworkBuilder;
import network.layer.Dimension;
import network.layer.Layer;
import optimizer.Optimizer;
import org.apache.commons.math3.linear.RealMatrix;
import util.model.Matrix3D;

import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class Model {

    //Модель данных
    private NetworkBuilder networkBuilder;
    private TrainableNetwork<Matrix3D, RealMatrix> trainableNetwork;
    private MyDatasetParser myDatasetParser;
    private Optimizer optimizer;
    private DatasetHelperImpl<Matrix3D, RealMatrix> helper;
    private Dataset<Matrix3D, RealMatrix> dataset;
    private String logsPath;
    private String savePath;
    private String trainPath;
    private String validPath;
    private String testPath;
    private String loadPath;
    private File train;
    private File valid;
    private File test;
    private File logs;
    private File save;
    private File load;

    public Model() {
        this.networkBuilder = NetworkBuilder.builder();
        this.myDatasetParser = new MyDatasetParser();
        this.helper = new DatasetHelperImpl<>();
    }

    public void downloadData() throws IOException {
        valid = new File(validPath);
        train = new File(trainPath);
        test = new File(testPath);

        dataset = helper.prepareDataset(train, valid, test, myDatasetParser);
    }

    public void addLayer(Layer layer) {
        networkBuilder.append(layer);
    }

    public void deleteLayer(int layerIndex) {
        networkBuilder.remove(layerIndex);
    }

    public void replace(int layerIndex, Layer layer) {
        networkBuilder
                .remove(layerIndex)
                .add(layerIndex, layer);
    }


    public void endBuild() {
        if (logs != null) {
            if (load != null) {
                trainableNetwork = networkBuilder.build(logs, load);
            } else {
                trainableNetwork = networkBuilder.build(logs);
            }

        } else {
            if (load != null) {
                trainableNetwork = networkBuilder.build(load, true);
            } else {
                trainableNetwork = networkBuilder.build();
            }
        }
    }

    public void saveInFile(File save) {
        System.out.println("START SAVE!!!");
        trainableNetwork.getNetworkModel().saveInFile(save);
        System.out.println("SAVED OR WHAT");
    }

}
