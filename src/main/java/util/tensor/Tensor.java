package util.tensor;

import lombok.Getter;
import util.Matrix3D;

import java.util.Arrays;

public class Tensor {

    @Getter
    private Object[] data;

    @Getter
    private int dimension;

    /*

    */
    public Tensor(int... sizeOfDimensions) {
        this.dimension = sizeOfDimensions.length;

        data = new Object[sizeOfDimensions[0]];
        recursiveInit(data, 1, sizeOfDimensions);
    }

    public void recursiveInit(Object[] array, int index, int... sizeOfDimensions) {
        if (index == sizeOfDimensions.length) {
            fillArray(array, index);
            return;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = new Object[sizeOfDimensions[index]];
//            System.out.println(index + " " + array[i]);
            Object[] some = (Object[]) array[i];
            recursiveInit(some,index + 1, sizeOfDimensions);
        }
    }

    @Override
    public String toString() {
        return data[0].toString();
    }

    public void fillArray(Object[] array, int index) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0.0;
        }
    }

    public static void recursivePrint(Object[] array, int depth) {
        if (depth == 1) {
            System.out.print("[ ");
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.print("]");
            System.out.println();
            return;
        }
        for (Object o: array) {
            Object[] innerArray = (Object[]) o;
            recursivePrint(innerArray,depth - 1);
//            System.out.println("-------");
        }
        System.out.println("DEMENSION " + depth);
    }


    public static void printTensor(Tensor tensor) {
        recursivePrint(tensor.getData(), tensor.getDimension());
    }

    public Tensor(Matrix3D array3D) {

    }

    public Tensor(double[][][] matrix3d) {

    }

    public Tensor(double[][] matrix2d) {

    }

    public void setMatrix2d(double[][] matrix, int depth) {

    }

    public Tensor copy(){

        return null;
    }

    private double[][][] initMatrix(int depth, int height, int width) {
        return new double[depth][height][width];
    }

    public static void main(String[] args) {

        Tensor tensor1 = new Tensor(10);
//        Tensor.printTensor(tensor1);

        Tensor tensor2 = new Tensor(5, 7, 5, 3);
        Tensor.printTensor(tensor2);
    }
}
