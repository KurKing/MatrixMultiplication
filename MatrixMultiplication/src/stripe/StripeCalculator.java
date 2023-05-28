package stripe;

import models.Matrix;
import models.Result;
import models.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StripeCalculator {

    public static Result multiply(Matrix matrix1, Matrix matrix2, int size, int stripesAmount) throws InterruptedException {

        List<Row> resultRows = new ArrayList<Row>(size);

        ExecutorService pool = Executors.newFixedThreadPool(stripesAmount);

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {

            final Row dataRow = matrix1.getRow(rowIndex);
            final Row resultRow = Row.instantiateClearRow(dataRow.getIndex(), dataRow.getData().length);
            resultRows.add(resultRow);

            pool.submit(new Runnable() {
                @Override
                public void run() {

                    for (int secondRowIndex = 0; secondRowIndex < size; secondRowIndex++) {

                        Row secondDataRow = matrix2.getRow(secondRowIndex);

                        for (int column = 0; column < size; column++) {

                            double valueToAdd = dataRow.getItem(secondRowIndex) * secondDataRow.getItem(column);
                            resultRow.addValue(valueToAdd, column);
                        }
                    }
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(100L, TimeUnit.SECONDS);

        double [][] result = new double[size][size];

        for (Row row: resultRows) { result[row.getIndex()] = row.getData(); }

        return new Result(result);
    }
}
