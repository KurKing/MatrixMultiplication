package fox;

import models.Matrix;
import models.Result;
import models.Row;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FoxCalculator {

    public static Result multiply(Matrix matrix1, Matrix matrix2, int size, int processNumber, int threadsAmount) throws InterruptedException {

        int blockSize = (int) Math.ceil((double) size / processNumber);

        List<Row> resultRows = new ArrayList<Row>(size);
        for (int i = 0; i < size; i++) { resultRows.add(Row.instantiateClearRow(i, size)); }

        ExecutorService pool = Executors.newFixedThreadPool(threadsAmount);

        for (var i = 0; i < processNumber; i++) {
            for (var j = 0; j < processNumber; j++) {

                List<Matrix> blockNeedsToBeAdded = getBlocks(matrix1, matrix2, blockSize, i, j, threadsAmount, processNumber);

                pool.submit(new FoxBlockAddingThread(blockNeedsToBeAdded, resultRows, i*blockSize, j*blockSize));
            }
        }

        pool.shutdown();
        pool.awaitTermination(100L, TimeUnit.SECONDS);

        double [][] result = new double[size][size];

        for (Row row: resultRows) { result[row.getIndex()] = row.getData(); }

        return new Result(result);
    }

    private static List<Matrix> getBlocks(Matrix matrix1, Matrix matrix2,
                                          int blockSize, int fromIndex, int tillIndex, int threadsAmount, int processNumber) throws InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(threadsAmount);

        List<Matrix> blocks = Collections.synchronizedList(new ArrayList<>());

        for (int block = 0; block < processNumber; block++) {

            int leftStartRow = fromIndex * blockSize;
            int rightStartColumn = tillIndex * blockSize;
            int startC = block * blockSize;

            FoxBlock leftBlock = new FoxBlock(leftStartRow, startC, leftStartRow + blockSize, startC + blockSize);
            FoxBlock rightBlock = new FoxBlock(startC, rightStartColumn, startC + blockSize, rightStartColumn + blockSize);

            pool.submit(new FoxBlockMultiplierThread(blocks, matrix1, matrix2, leftBlock, rightBlock));
        }

        pool.shutdown();
        pool.awaitTermination(100L, TimeUnit.SECONDS);

        return blocks;
    }
}