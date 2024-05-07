package util;

import function.activation.ActivationFunc;
import util.model.Matrix3D;
import util.task.Activation;
import util.task.ActivationDerivation;
import util.task.Convolution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ActivationParallelUtils {

    public static void activationParallelMutable(ActivationFunc activationFunc, Matrix3D input, int threadsCount) {

        TaskUtils.makeTasks(activationParallelMutableSplit(activationFunc, input, threadsCount), threadsCount);
    }

    public static List<Callable> activationParallelMutableSplit(ActivationFunc activationFunc, Matrix3D input, int threads) {
        List<Callable> tasks = new ArrayList<>();

        int overallThreadCount = threads;
        int threadCount = 0;

        if (input.getMatrix3d().length % threads == 0) {
            threadCount = overallThreadCount;
        } else {
            threadCount = overallThreadCount - 1;
        }
        if (input.getMatrix3d().length < threadCount) {
            threadCount = input.getMatrix3d().length;
        }

        int itemsPerThread = input.getMatrix3d().length / threadCount;
        int lastTaskCountKernels = input.getMatrix3d().length % threadCount;

        int indexStart = 0;
        for (int i = 0; i < threadCount; i++) {
            indexStart = i * itemsPerThread;
            Callable task = new Activation(activationFunc, indexStart, indexStart + itemsPerThread - 1, input);
            tasks.add(task);
        }

        if (input.getMatrix3d().length % threads != 0) {
//            System.out.println("ДОБАВИЛ");
            indexStart = indexStart + itemsPerThread - 1;
            Callable task = new Activation(activationFunc, indexStart, indexStart + lastTaskCountKernels, input);
            tasks.add(task);
        }
        return tasks;
    }

    public static void activationDerivationParallelMutable(ActivationFunc activationFunc, Matrix3D input, int threadsCount) {

        TaskUtils.makeTasks(activationDerivationParallelMutableSplit(activationFunc, input, threadsCount), threadsCount);
    }

    public static List<Callable> activationDerivationParallelMutableSplit(ActivationFunc activationFunc, Matrix3D input, int threads) {
        List<Callable> tasks = new ArrayList<>();

        int overallThreadCount = threads;
        int threadCount = 0;

        if (input.getMatrix3d().length % threads == 0) {
            threadCount = overallThreadCount;
        } else {
            threadCount = overallThreadCount - 1;
        }
        if (input.getMatrix3d().length < threadCount) {
            threadCount = input.getMatrix3d().length;
        }

        int itemsPerThread = input.getMatrix3d().length / threadCount;
        int lastTaskCountKernels = input.getMatrix3d().length % threadCount;

        int indexStart = 0;
        for (int i = 0; i < threadCount; i++) {
            indexStart = i * itemsPerThread;
            Callable task = new ActivationDerivation(activationFunc, indexStart, indexStart + itemsPerThread - 1, input);
            tasks.add(task);
        }

        if (input.getMatrix3d().length % threads != 0) {
//            System.out.println("ДОБАВИЛ");
            indexStart = indexStart + itemsPerThread - 1;
            Callable task = new ActivationDerivation(activationFunc, indexStart, indexStart + lastTaskCountKernels, input);
            tasks.add(task);
        }
        return tasks;
    }

}
