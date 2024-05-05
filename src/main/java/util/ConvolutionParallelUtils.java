package util;

import util.model.Matrix3D;
import util.task.Convolution;
import util.task.ConvolutionForBack;
import util.task.ConvolutionWithoutBaises;
import util.task.MaxPooling3D;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ConvolutionParallelUtils {

    public static Matrix3D convolutionParallel(Matrix3D input, Matrix3D[] kernels, double[] biases, int stride, int threadsCount) {
        int rowSize = (input.getMatrix3d()[0].length - kernels[0].getMatrix3d()[0].length) / stride + 1;
        int columnSize = (input.getMatrix3d()[0][0].length - kernels[0].getMatrix3d()[0][0].length) / stride + 1;

        Matrix3D result = new Matrix3D(kernels.length, rowSize, columnSize);

        TaskUtils.makeTasks(convolutionParallelSplit(result, input, kernels, biases, stride, threadsCount), threadsCount);

        return result;
    }

    public static List<Callable> convolutionParallelSplit(Matrix3D result, Matrix3D input, Matrix3D[] kernels, double[] biases, int stride, int threads) {
        List<Callable> tasks = new ArrayList<>();

        int overallThreadCount = threads;
        int threadCount = 0;

        if (kernels.length % threads == 0) {
            threadCount = overallThreadCount;
        } else {
            threadCount = overallThreadCount - 1;
        }
        if (kernels.length < threadCount) {
            threadCount = kernels.length;
        }

        int itemsPerThread = kernels.length / threadCount;
        int lastTaskCountKernels = kernels.length % threadCount;

        int indexStart = 0;
        for (int i = 0; i < threadCount; i++) {
            indexStart = i * itemsPerThread;
            Callable task = new Convolution(result, indexStart, indexStart + itemsPerThread - 1, input, kernels, biases, stride);
            tasks.add(task);
        }

        if (kernels.length % threads != 0) {
//            System.out.println("ДОБАВИЛ");
            indexStart = indexStart + itemsPerThread - 1;
            Callable task = new Convolution(result, indexStart, indexStart + lastTaskCountKernels, input, kernels, biases, stride);
            tasks.add(task);
        }
        return tasks;
    }

    public static Matrix3D convolutionWithoutBaisesParallel(Matrix3D input, Matrix3D[] kernels, int stride, int threadsCount) {
        int rowSize = (input.getMatrix3d()[0].length - kernels[0].getMatrix3d()[0].length) / stride + 1;
        int columnSize = (input.getMatrix3d()[0][0].length - kernels[0].getMatrix3d()[0][0].length) / stride + 1;

        Matrix3D result = new Matrix3D(kernels.length, rowSize, columnSize);

        TaskUtils.makeTasks(convolutionWithoutBaisesParallelSplit(result, input, kernels, stride, threadsCount), threadsCount);

        return result;
    }

    public static List<Callable> convolutionWithoutBaisesParallelSplit(Matrix3D result, Matrix3D input, Matrix3D[] kernels, int stride, int threads) {
        List<Callable> tasks = new ArrayList<>();

        int overallThreadCount = threads;
        int threadCount = 0;

        if (kernels.length % threads == 0) {
            threadCount = overallThreadCount;
        } else {
            threadCount = overallThreadCount - 1;
        }
        if (kernels.length < threadCount) {
            threadCount = kernels.length;
        }

        int itemsPerThread = kernels.length / threadCount;
        int lastTaskCountKernels = kernels.length % threadCount;

        int indexStart = 0;
        for (int i = 0; i < threadCount; i++) {
            indexStart = i * itemsPerThread;
            Callable task = new ConvolutionWithoutBaises(result, indexStart, indexStart + itemsPerThread - 1, input, kernels, stride);
            tasks.add(task);
        }

        if (kernels.length % threads != 0) {
//            System.out.println("ДОБАВИЛ");
            indexStart = indexStart + itemsPerThread - 1;
            Callable task = new ConvolutionWithoutBaises(result, indexStart, indexStart + lastTaskCountKernels, input, kernels, stride);
            tasks.add(task);
        }
        return tasks;
    }

    public static Matrix3D[] convolutionForBackParallel(Matrix3D input, Matrix3D kernel, int stride, int threadsCount) {
        double[][][] kernel3d = kernel.getMatrix3d();
        Matrix3D[] result = new Matrix3D[kernel3d.length];

        TaskUtils.makeTasks(convolutionForBackParallelSplit(result, input, kernel, stride, threadsCount), threadsCount);

        return result;
    }

    public static List<Callable> convolutionForBackParallelSplit(Matrix3D[] result, Matrix3D input, Matrix3D kernel, int stride, int threads) {
        List<Callable> tasks = new ArrayList<>();

        int overallThreadCount = threads;
        int threadCount = 0;

        if (result.length % threads == 0) {
            threadCount = overallThreadCount;
        } else {
            threadCount = overallThreadCount - 1;
        }
        if (result.length < threadCount) {
            threadCount = result.length;
        }

        int itemsPerThread = result.length / threadCount;
        int lastTaskCountKernels = result.length % threadCount;

        int indexStart = 0;
        for (int i = 0; i < threadCount; i++) {
            indexStart = i * itemsPerThread;
//            System.out.println(i + "-ая задача: " + indexStart + " " + (indexStart + itemsPerThread - 1));
            Callable task = new ConvolutionForBack(result, indexStart, indexStart + itemsPerThread - 1, input, kernel, stride);
            tasks.add(task);
        }

        if (result.length % threads != 0) {
//            System.out.println("ДОБАВИЛ");
            indexStart = indexStart + itemsPerThread - 1;
            Callable task = new ConvolutionForBack(result, indexStart, indexStart + lastTaskCountKernels, input, kernel, stride);
            tasks.add(task);
        }
        return tasks;
    }

    public static Matrix3D maxPooling3DParallel(Matrix3D input, int size, int stride, int threadsCount) {
        double[][][] input3d = input.getMatrix3d();
        int rowSize = (input3d[0].length - size) / stride + 1;
        int columnSize = (input3d[0][0].length - size) / stride + 1;

        Matrix3D result = new Matrix3D(input3d.length, rowSize, columnSize);

        TaskUtils.makeTasks(maxPooling3DParallelSplit(result, input, size, stride, threadsCount), threadsCount);

        return result;
    }

    public static List<Callable> maxPooling3DParallelSplit(Matrix3D result, Matrix3D input, int size, int stride, int threads) {
        List<Callable> tasks = new ArrayList<>();
        double[][][] input3d = input.getMatrix3d();

        int overallThreadCount = threads;
        int threadCount = 0;

        if (input3d.length % threads == 0) {
            threadCount = overallThreadCount;
        } else {
            threadCount = overallThreadCount - 1;
        }
        if (input3d.length < threadCount) {
            threadCount = input3d.length;
        }

        int itemsPerThread = input3d.length / threadCount;
        int lastTaskCountKernels = input3d.length % threadCount;

        int indexStart = 0;
        for (int i = 0; i < threadCount; i++) {
            indexStart = i * itemsPerThread;
            Callable task = new MaxPooling3D(result, indexStart, indexStart + itemsPerThread - 1, input, size, stride);
            tasks.add(task);
        }

        if (input3d.length % threads != 0) {
//            System.out.println("ДОБАВИЛ");
            indexStart = indexStart + itemsPerThread - 1;
            Callable task = new MaxPooling3D(result, indexStart, indexStart + lastTaskCountKernels, input, size, stride);
            tasks.add(task);
        }
        return tasks;
    }

    public static Matrix3D maxPooling3DForBackParallel(Matrix3D input, Matrix3D pool, int size, int stride, int threadsCount) {
        double[][][] input3d = input.getMatrix3d();
        double[][][] pool3d = pool.getMatrix3d();

        Matrix3D result = new Matrix3D(input3d.length, input3d[0].length, input3d[0][0].length);

        TaskUtils.makeTasks(maxPooling3DForBackParallelSplit(result, input, pool, size, stride, threadsCount), threadsCount);

        return result;
    }

    public static List<Callable> maxPooling3DForBackParallelSplit(Matrix3D result, Matrix3D input, Matrix3D pool, int size, int stride, int threads) {
        List<Callable> tasks = new ArrayList<>();
        double[][][] result3d = result.getMatrix3d();

        int overallThreadCount = threads;
        int threadCount = 0;

        if (result3d.length % threads == 0) {
            threadCount = overallThreadCount;
        } else {
            threadCount = overallThreadCount - 1;
        }
        if (result3d.length < threadCount) {
            threadCount = result3d.length;
        }

        int itemsPerThread = result3d.length / threadCount;
        int lastTaskCountKernels = result3d.length % threadCount;

        int indexStart = 0;
        for (int i = 0; i < threadCount; i++) {
            indexStart = i * itemsPerThread;
            Callable task = new MaxPooling3D(result, indexStart, indexStart + itemsPerThread - 1, input, size, stride);
            tasks.add(task);
        }

        if (result3d.length % threads != 0) {
//            System.out.println("ДОБАВИЛ");
            indexStart = indexStart + itemsPerThread - 1;
            Callable task = new MaxPooling3D(result, indexStart, indexStart + lastTaskCountKernels, input, size, stride);
            tasks.add(task);
        }
        return tasks;
    }

}
