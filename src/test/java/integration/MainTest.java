package integration;

public class MainTest {


//    @BeforeEach
//    public void setup() {
//        //Зафиксировать сид, чтобы тесты выдавали однотипные данные!
//        MatrixUtils.setSeed(10);
//    }
//
//
//    @Test
//    void FULLY_RELU_FULLY_RELU() throws IOException {
//
//        DatasetRowConverter converter = new IrisDatasetConverter(4, ",");
//        DatasetHelper datasetHelper = new DatasetHelperImpl();
//
//        String pathIris = "src/main/resources/iris.data";
//        File file = new File(pathIris);
//
//        data.dataset.Dataset dataset = datasetHelper.prepareDataset(file, file, file, new MyDatasetParser());
//        dataset.shuffleDataset();
//
//        //GOOOOOOOOOD
//        TrainableNetwork network = PerceptronBuilder.builder()
//                .append(new FullyConnected(4, 4))
//                .append(new ActivationLayer(new ReLu()))
//                .append(new FullyConnected(3))
//                .append(new ActivationLayer(new ReLu()))
//                .build();
//
//        double learnRate = 0.01;
//        int epoch = 70;
//
//        network.learn(epoch, learnRate, dataset);
//        network.test(dataset);
//    }
//
//    @Test
//    void FULLY_LEAKY_FULLY_SIN_FULLY_LEAKY_FULLY_RELU_FULLY_LEAKY() throws IOException {
//        DatasetRowConverter converter = new IrisDatasetConverter(4, ",");
//        DatasetHelper datasetHelper = new DatasetHelperImpl();
//
//        String pathIris = "src/main/resources/iris.data";
//        File file = new File(pathIris);
//
//        data.dataset.Dataset dataset = datasetHelper.prepareDataset(file, file, file, new MyDatasetParser());
////        dataset.shuffleDataset();
//
//        TrainableNetwork network = PerceptronBuilder.builder()
//                .append(new FullyConnected(3, 4))
//                .append(new ActivationLayer(new LeakyReLu()))
//                .append(new FullyConnected(6))
//                .append(new ActivationLayer(new Sin()))
//                .append(new FullyConnected(8))
//                .append(new ActivationLayer(new LeakyReLu()))
//                .append(new FullyConnected(10))
//                .append(new ActivationLayer(new ReLu()))
//                .append(new FullyConnected(3))
//                .append(new ActivationLayer(new LeakyReLu()))
//                .build();
//
//        double learnRate = 0.01;
//        int epoch = 120;
//
//        network.learn(epoch, learnRate, dataset);
//        network.test(dataset);
//    }
//
//    @Test
//    void FULLY_RELU_FULLY_SOFTMAX() throws IOException {
//        DatasetRowConverter converter = new IrisDatasetConverter(4, ",");
//        DatasetHelper datasetHelper = new DatasetHelperImpl();
//
//        String pathIris = "src/main/resources/iris.data";
//        File file = new File(pathIris);
//
//        data.dataset.Dataset dataset = datasetHelper.prepareDataset(file, file, file, new MyDatasetParser());
//
//        TrainableNetwork network = PerceptronBuilder.builder()
//                .append(new FullyConnected(4, 4))
//                .append(new ActivationLayer(new ReLu()))
//                .append(new FullyConnected(3))
//                .append(new ActivationLayer(new Softmax()))
//                .build();
//
//        double learnRate = 0.35;
//        int epoch = 5;
//
//        network.learn(epoch, learnRate, dataset);
////        network.test(dataset.getTestData());
//    }
//
//    @Test
//    void FULLY_LEAKY_FULLY_SIN_FULLY_LEAKY_FULLY_RELU_FULLY_SOFTMAX() throws IOException {
//        DatasetRowConverter converter = new IrisDatasetConverter(4, ",");
//        DatasetHelper datasetHelper = new DatasetHelperImpl();
//
//        String pathIris = "src/main/resources/iris.data";
//        File file = new File(pathIris);
//
//        data.dataset.Dataset dataset = datasetHelper.prepareDataset(file, file, file, new MyDatasetParser());
//        dataset.shuffleDataset();
//
//        TrainableNetwork network = PerceptronBuilder.builder()
//                .append(new FullyConnected(10, 4))
//                .append(new ActivationLayer(new ReLu()))
//
//                .append(new FullyConnected(8))
//                .append(new ActivationLayer(new ReLu()))
//
//                .append(new FullyConnected(3))
//                .append(new ActivationLayer(new ReLu()))
//                .append(new ActivationLayer(new Softmax()))
//                .build();
//
//        double learnRate = 0.01;
//        int epoch = 55;
//
//        network.learn(epoch, learnRate, dataset);
//        network.test(dataset);
//    }
}
