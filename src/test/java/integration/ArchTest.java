package integration;

import data.Dataset;
import data.DatasetHelperImpl;
import data.parser.MyDatasetParser;
import function.LabelSmoothing;
import function.activation.ReLu;
import function.activation.Softmax;
import function.initializer.HeInitializer;
import network.TrainableNetwork;
import network.builder.NetworkBuilder;
import network.layer.*;
import optimizer.Adagrad;
import optimizer.GD;
import optimizer.SGD;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Test;
import util.MatrixUtils;
import util.model.Matrix3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ArchTest {

    @Test
    public void tes_Conv_ReLu_Conv_ReLu_Conv_ReLu() throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));

        int w = image.getWidth();
        int h = image.getHeight();

        System.out.println(w + " " + h);

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        ConvolutionLayer layer1 = new ConvolutionLayer(32, 5, 1, imageDimension);
        Activation3DLayer layer2 = new Activation3DLayer(new ReLu());
        ConvolutionLayer layer3 = new ConvolutionLayer(64, 4, 2);
        Activation3DLayer layer4 = new Activation3DLayer(new ReLu());
        ConvolutionLayer layer5 = new ConvolutionLayer(128, 3, 2);
        Activation3DLayer layer6 = new Activation3DLayer(new ReLu());
        ConvolutionLayer layer7 = new ConvolutionLayer(256, 3, 1);
        Activation3DLayer layer8 = new Activation3DLayer(new ReLu());

        layer2.setPrevious(layer1);
        layer3.setPrevious(layer2);
        layer4.setPrevious(layer3);
        layer5.setPrevious(layer4);
        layer6.setPrevious(layer5);
        layer7.setPrevious(layer6);
        layer8.setPrevious(layer7);

        Matrix3D result8 = null;
        for (int i = 0; i < 5; i++) {
            Matrix3D result1 = layer1.propogateForward(dataFrame);
            Matrix3D result2 = layer2.propogateForward(result1);
            Matrix3D result3 = layer3.propogateForward(result2);
            Matrix3D result4 = layer4.propogateForward(result3);
            Matrix3D result5 = layer5.propogateForward(result4);
            Matrix3D result6 = layer6.propogateForward(result5);
            Matrix3D result7 = layer7.propogateForward(result6);
            result8 = layer8.propogateForward(result7);
        }

        System.out.println("---------AFTER CONNECT FULL--------");
        MatrixUtils.printMatrix3D(result8);
    }

    @Test
    public void testBuildNetwork() throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));

        Matrix3D dataFrame = MatrixUtils.getDataFrame(image);

        Dimension imageDimension = new Dimension(3, 32, 32);

        TrainableNetwork network = NetworkBuilder.builder()
                .append(new ConvolutionLayer(32, 5, 1, imageDimension))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(64, 4, 2))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(128, 3, 2))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(256, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new Flatten())
                .append(new FullyConnected(100))
                .append(new ActivationLayer(new ReLu()))
                .append(new FullyConnected(50))
                .append(new ActivationLayer(new ReLu()))
                .build();

        System.out.println("---------AFTER testBuildNetwork--------");
    }

    @Test
    public void testFinalArchitecture() throws IOException {
//        String trainPath = "C:\\cifar10_50\\train";
//        String validPath = "C:\\cifar10_50\\valid";
//        String testPath = "C:\\cifar10_50\\test";

        String trainPath = "D:\\cifar10_50\\cifar10_50\\train";
        String validPath = "D:\\cifar10_50\\cifar10_50\\valid";
        String testPath = "D:\\cifar10_50\\cifar10_50\\test";

//        String trainPath = "D:\\cifar10_1000\\train";
//        String validPath = "D:\\cifar10_1000\\valid";
//        String testPath = "D:\\cifar10_1000\\test";

        File train = new File(trainPath);
        File valid = new File(validPath);
        File test = new File(testPath);

        MyDatasetParser myDatasetParser = new MyDatasetParser();

        DatasetHelperImpl<Matrix3D, RealMatrix> helper = new DatasetHelperImpl<>();
        Dataset<Matrix3D, RealMatrix> dataset = helper.prepareDataset(train, valid, test, myDatasetParser);


        Dimension imageDimension = new Dimension(3, 32, 32);

        TrainableNetwork network = NetworkBuilder.builder()
                .append(new ConvolutionLayer(32, 3, 1, imageDimension))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(64, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new PoolingLayer(2, 2))
                .append(new ConvolutionLayer(128, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(256, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new PoolingLayer(2, 2))
                //5 x 5 x 256
                .append(new Flatten())
                .append(new FullyConnected(1024))
                .append(new ActivationLayer(new ReLu()))
//                .append(new DropoutLayer(0.3))
                .append(new FullyConnected(128))
                .append(new ActivationLayer(new ReLu()))
                .append(new FullyConnected(10))
//                .append(new ActivationLayer(new ReLu()))
                .append(new ActivationLayer(new Softmax()))
                .build();


        network.train(10, 1, dataset, true);
    }

    @Test
    public void testArchitectureKradennaya() throws IOException {
//        String trainPath = "C:\\cifar10_50\\train";
//        String validPath = "C:\\cifar10_50\\valid";
//        String testPath = "C:\\cifar10_50\\test";

        String trainPath = "D:\\cifar10_50\\train";
        String validPath = "D:\\cifar10_50\\valid";
        String testPath = "D:\\cifar10_50\\test";

        String logsPath = "D:\\logs.txt";
        String savePath = "D:\\network.txt";

//        String trainPath = "D:\\cifar10_1000\\train";
//        String validPath = "D:\\cifar10_1000\\valid";
//        String testPath = "D:\\cifar10_1000\\test";

        File train = new File(trainPath);
        File valid = new File(validPath);
        File test = new File(testPath);
        File logs = new File(logsPath);
        File save = new File(savePath);

        MyDatasetParser myDatasetParser = new MyDatasetParser();

        DatasetHelperImpl<Matrix3D, RealMatrix> helper = new DatasetHelperImpl<>();
        Dataset<Matrix3D, RealMatrix> dataset = helper.prepareDataset(train, valid, test, myDatasetParser);


        Dimension imageDimension = new Dimension(3, 32, 32);

        TrainableNetwork network = NetworkBuilder.builder()
                .append(new ConvolutionLayer(32, 3, 1, imageDimension))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(32, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new PoolingLayer(2, 2))
                .append(new ConvolutionLayer(64, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(64, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new PoolingLayer(2, 2))
                .append(new Flatten())
                .append(new FullyConnected(512))
                .append(new ActivationLayer(new ReLu()))
//                .append(new DropoutLayer(0.3))
                .append(new FullyConnected(10))
//                .append(new ActivationLayer(new ReLu()))
                .append(new ActivationLayer(new Softmax()))
                .build(logs, save, true);


        network.train(25, 1, dataset, true);
        network.test(dataset);
    }

    @Test
    public void testArchitectureKradennayaMain() throws IOException {
//        String trainPath = "C:\\cifar10_50\\train";
//        String validPath = "C:\\cifar10_50\\valid";
//        String testPath = "C:\\cifar10_50\\test";

//        String trainPath = "D:\\cifar10\\train";
//        String validPath = "D:\\cifar10\\valid";
//        String testPath = "D:\\cifar10\\test";

        String logsPath = "D:\\logs.txt";
        String savePath = "D:\\network";

//        String loadPath = "D:\\network10epoch50000";

        String trainPath = "D:\\cifar10_50\\train";
        String validPath = "D:\\cifar10_50\\valid";
        String testPath = "D:\\cifar10_50\\test";

        File train = new File(trainPath);
        File valid = new File(validPath);
        File test = new File(testPath);
        File logs = new File(logsPath);
//        File save = new File(savePath);

//        File load = new File(loadPath);

        MyDatasetParser myDatasetParser = new MyDatasetParser();

        DatasetHelperImpl<Matrix3D, RealMatrix> helper = new DatasetHelperImpl<>();
        Dataset<Matrix3D, RealMatrix> dataset = helper.prepareDataset(train, valid, test, myDatasetParser);

        Dimension imageDimension = new Dimension(3, 32, 32);

        TrainableNetwork<Matrix3D, RealMatrix> network = NetworkBuilder.builder()
                .append(new ConvolutionLayer(32, 3, 1,
                                            imageDimension,
                                            new HeInitializer()))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(32, 3, 1,
                                            new HeInitializer()))
                .append(new Activation3DLayer(new ReLu()))
                .append(new PoolingLayer(2, 2))
                .append(new ConvolutionLayer(64, 3, 1,
                                            new HeInitializer()))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(64, 3, 1,
                                            new HeInitializer()))
                .append(new Activation3DLayer(new ReLu()))
                .append(new PoolingLayer(2, 2))
                .append(new Flatten())
                .append(new FullyConnected(512,new HeInitializer()))
                .append(new ActivationLayer(new ReLu()))
                .append(new FullyConnected(10,new HeInitializer()))
//                .append(new ActivationLayer(new ReLu()))
                .append(new ActivationLayer(new Softmax()))
                .build();

        network.setOptimizer(new Adagrad());
//        network.setOptimizer(new GD(0.001));
//        network.setErrorFunction(new LabelSmoothing(0.01));
//        network.setErrorFunction(new LabelSmoothing(0.001));
        network.train(10, dataset, true);
        network.test(dataset);
    }

    @Test
    public void saveKradArch10epoch_500() throws IOException {
        String logsPath = "D:\\logs.txt";
        String savePath = "D:\\network.txt";

//        String loadPath = "D:\\network.txt";

        String trainPath = "D:\\cifar10_50\\train";
        String validPath = "D:\\cifar10_50\\valid";
        String testPath = "D:\\cifar10_50\\test";

        File train = new File(trainPath);
        File valid = new File(validPath);
        File test = new File(testPath);
        File logs = new File(logsPath);
//        File save = new File(savePath);

//        File load = new File(loadPath);

        MyDatasetParser myDatasetParser = new MyDatasetParser();

        DatasetHelperImpl<Matrix3D, RealMatrix> helper = new DatasetHelperImpl<>();
        Dataset<Matrix3D, RealMatrix> dataset = helper.prepareDataset(train, valid, test, myDatasetParser);

        Dimension imageDimension = new Dimension(3, 32, 32);

        TrainableNetwork<Matrix3D, RealMatrix> network = NetworkBuilder.builder()
                .append(new ConvolutionLayer(32, 3, 1, imageDimension))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(32, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new PoolingLayer(2, 2))
                .append(new ConvolutionLayer(64, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new ConvolutionLayer(64, 3, 1))
                .append(new Activation3DLayer(new ReLu()))
                .append(new PoolingLayer(2, 2))
                .append(new Flatten())
                .append(new FullyConnected(512))
                .append(new ActivationLayer(new ReLu()))
                .append(new FullyConnected(10))
                .append(new ActivationLayer(new Softmax()))
                .build();


        network.setErrorFunction(new LabelSmoothing(0.01));
        network.train(25, dataset, true);
        network.test(dataset);
    }
}
