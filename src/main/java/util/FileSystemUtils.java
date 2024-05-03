package util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Slf4j
public class FileSystemUtils {
    private static final String ROOT_PATH = "";

    private static FileSystemUtils vfs = null;

    private FileSystemUtils() {

    }

    public static FileSystemUtils instance() {
        if (vfs == null) {
            vfs = new FileSystemUtils();
        }
        return vfs;
    }

    public static int getCountOfSubDirectories(File file) {
        if (!file.exists()) {
            throw new RuntimeException("Путь " + file.getAbsolutePath() + " не ок!");
        }
        if (!file.isDirectory()) {
            throw new RuntimeException("Путь " + file.getAbsolutePath() + " не является директорией!");
        }
        return file.listFiles().length;
    }

    public static Map<String, List<BufferedImage>> getAllFilesInSubDirectories(File file) {
        if (!file.isDirectory()) {
            throw new RuntimeException("Путь " + file.getAbsolutePath() + " не является директорией!");
        }

        Map<String, List<BufferedImage>> result = new HashMap<>();

        for (File directory: file.listFiles()) {
            if (directory.isDirectory()) {
                List<BufferedImage> pictures = new ArrayList<>();
                for (File picture: directory.listFiles()) {
                    try {
                        pictures.add(ImageIO.read(picture));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                result.put(directory.getName(), pictures);
            }
        }
        return result;
    }

    public Matrix3D getDataFrame(BufferedImage image) {
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
                red[i][j] =  c.getRed() / 255.0;
                green[i][j] = c.getGreen() / 255.0;
                blue[i][j] = c.getBlue() / 255.0;
            }
        }
        result.setMatrix2d(red, 0);
        result.setMatrix2d(green, 1);
        result.setMatrix2d(blue, 2);

        return result;
    }

    public List<Matrix3D> getDataFrames(String path) throws IOException {
        List<Matrix3D> inputData = new ArrayList<>();
        Iterator<String> iterator = FileSystemUtils.instance().getIterator(path);
        while (iterator.hasNext()) {
            String pathPicture = iterator.next();
            System.out.println("reading: " + pathPicture);
            BufferedImage image = ImageIO.read(new File(pathPicture));
            inputData.add(getDataFrame(image));
        }
        return inputData;
    }

    public Iterator<String> getIterator(String startDir) {
        return new FileIterator(startDir);
    }

    private static class FileIterator implements Iterator<String> {
        private final Queue<File> files = new LinkedList<>();

        public FileIterator(String path) {
            log.debug("FileIterator started with root path: " + new File(path).getAbsolutePath());
            this.files.add(new File(ROOT_PATH + path));
            log.debug("files size after start: " + files.size());
        }

        @Override
        public boolean hasNext() {
            return !files.isEmpty();
        }

        @Override
        public String next() {
            File file  = files.peek();

            while (file.isDirectory()) {
                file  = files.peek();
                log.debug("PEEKED FILE: {}", file.getAbsolutePath());
                log.debug("ADDING ALL FILES IN: {}", file.getAbsolutePath());
                Collections.addAll(files, file.listFiles());
                files.poll();
                file = files.peek();
            }
            return files.poll().getAbsolutePath();
        }
    }
}
