package util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class FileSystemUtils {
    private static final String ROOT_PATH = "";

    private static FileSystemUtils vfs = null;

    private FileSystemUtils() {

    }

    public static FileSystemUtils instance() {
        log.debug("VFSImpl started with root path: " + new File(ROOT_PATH).getAbsolutePath());
        if (vfs == null) {
            vfs = new FileSystemUtils();
        }
        return vfs;
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
