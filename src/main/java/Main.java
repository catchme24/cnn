
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

    public static void main(String[] args) throws IOException, ParseException {

        BufferedImage image = ImageIO.read(new File("D:\\0001.png"));

        int w = image.getWidth();
        int h = image.getHeight();

        System.out.println(w + " " + h);

        int[] dataBuffInt = image.getRGB(0, 0, w, h, null, 0, w);
        Color c = new Color(dataBuffInt[512 + 15]);



        System.out.println(c.getRed());   // = (dataBuffInt[100] >> 16) & 0xFF
        System.out.println(c.getGreen()); // = (dataBuffInt[100] >> 8)  & 0xFF
        System.out.println(c.getBlue());  // = (dataBuffInt[100] >> 0)  & 0xFF
        System.out.println(c.getAlpha()); // = (

        Matrix3D dataFrame = getDataFrame(image);
        double[][][] dataFrameArray = dataFrame.getMatrix3d();

        for (int i = 0; i < dataFrameArray.length; i++) {
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            RealMatrix matrix = MatrixUtils.createInstance(dataFrameArray[i]);
            MatrixUtils.printMatrixTest(matrix);
        }
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
