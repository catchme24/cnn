package util;

import lombok.Getter;


public class Matrix3D {

    @Getter
    private final double[][][] matrix3d;

    @Getter
    private final int dimension;

    public Matrix3D(int depth, int height, int width) {
        this.matrix3d = initMatrix(depth, height, width);
        this.dimension = depth == 1 ? 2 : 3;
    }

    public Matrix3D(int height, int width) {
        this.matrix3d = initMatrix(1, height, width);
        this.dimension = 2;
    }

    public Matrix3D(Matrix3D array3D) {
        this.matrix3d = array3D.getMatrix3d();
        this.dimension = 3;
    }

    public Matrix3D(double[][][] matrix3d) {
        this.matrix3d = matrix3d;
        this.dimension = 3;
    }

    public Matrix3D(double[][] matrix2d) {
        this.matrix3d = new double[1][matrix2d.length][matrix2d[0].length];
        this.matrix3d[0] = matrix2d;
        this.dimension = 2;
    }

    public void setMatrix2d(double[][] matrix, int depth) {
        matrix3d[depth] = matrix;
    }

    private double[][][] initMatrix(int depth, int height, int width) {
        return new double[depth][height][width];
    }
}
