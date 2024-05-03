package util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

public class FileSystemUtilsTest {

    @Test
    public void getCountOfSubDirectories() {
        String path = "D:\\cifar10_10\\test";
        File file = new File(path);

        int countOfSubDirectories = FileSystemUtils.getCountOfSubDirectories(file);
        Assertions.assertEquals(10, countOfSubDirectories);
    }

    @Test
    public void getAllFilesInSubDirectories() {
        String path = "D:\\cifar10_10\\train";
        File file = new File(path);

        Map<String, List<BufferedImage>> result = FileSystemUtils.getAllFilesInSubDirectories(file);
        System.out.println(result);
    }
}
