package stripe;

import models.Matrix;
import models.Result;

public class StripeCalculator {

    public static Result multiply(Matrix matrix1, Matrix matrix2, int size) {

        Result result = Result.instantiateClearResultMatrix(matrix1.getSize());

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {

            for (int secondRowIndex = 0; secondRowIndex < size; secondRowIndex++) {

                for (int column = 0; column < size; column++) {

                    double valueToAdd = matrix1.getItem(rowIndex, secondRowIndex) * matrix2.getItem(secondRowIndex, column);
                    result.addValue(valueToAdd, rowIndex, column);
                }
            }
        }

        return result;
    }
}
