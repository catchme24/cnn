
import data.Dataset;
import data.DatasetHelperImpl;
import data.parser.MyDatasetParser;
import org.apache.commons.math3.linear.RealMatrix;
import util.model.Matrix3D;
import util.MatrixUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread.sleep(5000);

        String trainPath = "C:\\cifar10\\train";
        String validPath = "C:\\cifar10\\valid";
        String testPath = "C:\\cifar10\\test";

        File train = new File(trainPath);
        File valid = new File(validPath);
        File test = new File(testPath);

        MyDatasetParser myDatasetParser = new MyDatasetParser();

        System.out.println("ПОЕХАЛА ГРУЗИТЬ");
        DatasetHelperImpl<Matrix3D, RealMatrix> helper = new DatasetHelperImpl<>();
        Dataset<Matrix3D, RealMatrix> dataset = helper.prepareDataset(train, valid, test, myDatasetParser);
        System.out.println("ЗАКОНЧИЛА ГРУЗИТЬ");

        Thread.sleep(10000);
    }

    public static Matrix3D getDataFrame(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        double[][] red = new double[h][w];
        double[][] green = new double[h][w];
        double[][] blue = new double[h][w];

        Matrix3D result = new Matrix3D(3, h, w);

        int[] dataBuffInt = image.getRGB(0, 0, w, h, null, 0, w);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color c = new Color(dataBuffInt[i * w + j]);
                red[i][j] = c.getRed();
                green[i][j] = c.getGreen();
                blue[i][j] = c.getBlue();
            }
        }
        result.setMatrix2d(red, 0);
        result.setMatrix2d(green, 1);
        result.setMatrix2d(blue, 2);

        return result;
    }
}
