package models;

public class Result extends Matrix {

    public static Result instantiateClearResultMatrix(int size) {

        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }
        }

        return new Result(matrix);
    }

    public Result(double[][] matrix) {
        super(matrix);
    }

    @Override
    public void print() {

        System.out.println("\nResult object:");
        super.print();
    }
}
