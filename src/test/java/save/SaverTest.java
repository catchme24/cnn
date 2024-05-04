package save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import function.ReLu;
import function.Softmax;
import network.TrainableNetwork;
import network.builder.NetworkBuilder;
import network.layer.*;
import network.model.NetworkModel;
import network.model.NetworkModelImpl;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Test;
import util.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class SaverTest {

    @Test
    public void testSaveNetwork() throws IOException {
        Deque<Layer> layers = new LinkedList<>();
        Dimension imageDimension = new Dimension(3, 32, 32);
        layers.add(new ConvolutionLayer(32, 3, 1, imageDimension));
        layers.add(new Activation3DLayer(new ReLu()));
//        layers.add(new ConvolutionLayer(64, 3, 1));
//        layers.add(new Activation3DLayer(new ReLu()));
//        layers.add(new PoolingLayer(2, 2));
//        layers.add(new ConvolutionLayer(128, 3, 1));
//        layers.add(new Activation3DLayer(new ReLu()));
//        layers.add(new ConvolutionLayer(256, 3, 1));
//        layers.add(new Activation3DLayer(new ReLu()));
//        layers.add(new PoolingLayer(2, 2));
        //5 x 5 x 256
//        layers.add(new Flatten());
//        layers.add(new FullyConnected(1024));
//        layers.add(new ActivationLayer(new ReLu()));
//        layers.add(new DropoutLayer(0.3));
//        layers.add(new FullyConnected(128));
//        layers.add(new ActivationLayer(new ReLu()));
//        layers.add(new FullyConnected(10));
//        layers.add(new ActivationLayer(new ReLu()));
//        layers.add(new ActivationLayer(new Softmax()));

        NetworkModel networkModel = new NetworkModelImpl(layers);

        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));
        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Object object = networkModel.propogateForward(dataFrame);
        MatrixUtils.printMatrix3D((Matrix3D) object);

        //SAVING
        Gson gson = new Gson();
        String json = gson.toJson(networkModel);
        String path = "D:\\file3.txt";

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))) {
            bw.write(json);
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testLoadNetwork() throws IOException {
        Gson gson = new Gson();

        String path = "D:\\file3.txt";
        String bigObject = null;

        Deque<Layer> layers = new LinkedList<>();
        Dimension imageDimension = new Dimension(3, 32, 32);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            bigObject = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NetworkModelImpl networkModel = gson.fromJson(bigObject, NetworkModelImpl.class);

        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));
        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Object object = networkModel.propogateForward(dataFrame);
        MatrixUtils.printMatrix3D((Matrix3D) object);

//        RealMatrix oneHotEncodingVector = MatrixUtils.createEmptyVector(10);
//        oneHotEncodingVector.setEntry(0, 0, 1.0);
//        for (int i = 0; i < 10; i++){
//            RealMatrix object = (RealMatrix) networkModel.propogateForward(dataFrame);
//            RealMatrix error = object.subtract(oneHotEncodingVector);
//            networkModel.propogateBackward(error);
//            System.out.println(i);
//        }
    }

    @Test
    public void testSaveSmallObject() throws IOException {

        Dimension imageDimension = new Dimension(3, 32, 32);
        ConvolutionLayer layer = new ConvolutionLayer(1, 8, 8, imageDimension);

        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));
        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Matrix3D object = layer.propogateForward(dataFrame);
        MatrixUtils.printMatrix3D(object);

        Gson gson = new Gson();
        String json = gson.toJson(layer);

        String path = "D:\\file2.txt";

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))) {
            bw.write(json);
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testLoadSmallObject() throws IOException {
        Gson gson = new Gson();

        String path = "D:\\file2.txt";
        String bigObject = null;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            bigObject = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ConvolutionLayer convolutionLayer = gson.fromJson(bigObject, ConvolutionLayer.class);

        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));
        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Matrix3D object = convolutionLayer.propogateForward(dataFrame);
        MatrixUtils.printMatrix3D(object);
    }

    @Test
    public void saveSmallArch() {
        Deque<Layer> layers = new LinkedList<>();
        Dimension imageDimension = new Dimension(3, 32, 32);
        layers.add(new ConvolutionLayer(32, 3, 1, imageDimension));
        layers.add(new Activation3DLayer(new ReLu()));
//        layers.add(new ConvolutionLayer(64, 3, 1));
//        layers.add(new Activation3DLayer(new ReLu()));
//        layers.add(new PoolingLayer(2, 2));
//        layers.add(new ConvolutionLayer(128, 3, 1));
//        layers.add(new Activation3DLayer(new ReLu()));
//        layers.add(new ConvolutionLayer(256, 3, 1));
//        layers.add(new Activation3DLayer(new ReLu()));
//        layers.add(new PoolingLayer(2, 2));
//        layers.add(new Flatten());
//        layers.add(new FullyConnected(1024));
//        layers.add(new ActivationLayer(new ReLu()));
//        layers.add(new DropoutLayer(0.3));
//        layers.add(new FullyConnected(128));
//        layers.add(new ActivationLayer(new ReLu()));
//        layers.add(new FullyConnected(10));
//        layers.add(new ActivationLayer(new ReLu()));
//        layers.add(new ActivationLayer(new Softmax()));

        //Прогон сигнала для кеширования преактиваций
//        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));
//        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);
//        Matrix3D object = layer.propogateForward(dataFrame);
//        MatrixUtils.printMatrix3D(object);

        Gson gson = new Gson();
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        List<String> stringLayers = new ArrayList<>();
        String path = "D:\\network.txt";

        for (Layer layer : layers) {
            stringLayers.add(gson.toJson(layer));
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))) {
            for (String stringLayer: stringLayers) {
                bw.write(stringLayer);
                bw.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loadSmallArch() throws IOException {
        String path = "D:\\network.txt";
        File file = new File(path);

        Dimension imageDimension = new Dimension(3, 32, 32);
        TrainableNetwork network = NetworkBuilder.builder()
                .append((new ConvolutionLayer(32, 3, 1, imageDimension)))
                .append(new Activation3DLayer(new ReLu()))
//                .append(new ConvolutionLayer(64, 3, 1))
//                .append(new Activation3DLayer(new ReLu()))
//                .append(new PoolingLayer(2, 2))
//                .append(new ConvolutionLayer(128, 3, 1))
//                .append(new Activation3DLayer(new ReLu()))
//                .append(new ConvolutionLayer(256, 3, 1))
//                .append(new Activation3DLayer(new ReLu()))
//                .append(new PoolingLayer(2, 2))
//                .append(new Flatten())
//                .append(new FullyConnected(1024))
//                .append(new ActivationLayer(new ReLu()))
//                .append(new DropoutLayer(0.3))
//                .append(new FullyConnected(128))
//                .append(new ActivationLayer(new ReLu()))
//                .append(new FullyConnected(10))
//                .append(new ActivationLayer(new ReLu()))
//                .append(new ActivationLayer(new Softmax()))
                .build(file);

        network.learn(1, 1, null);

        //Прогон сигнала для теста
//        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));
//        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);
//        Matrix3D object = layer.propogateForward(dataFrame);
//        MatrixUtils.printMatrix3D(object);
    }
}
