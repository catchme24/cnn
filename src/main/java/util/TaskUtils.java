package util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TaskUtils {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static boolean makeTasks(List<Callable> tasks) {
        List<Future> results = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            results.add(executorService.submit(tasks.get(i)));
        }

        try {
            for (Future result: results) {
                result.get();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
