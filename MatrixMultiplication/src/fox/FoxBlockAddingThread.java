package fox;

import models.Matrix;
import models.Row;

import java.util.List;

public class FoxBlockAddingThread implements Runnable {

    private final List<Matrix> blocksNeedsToBeAdded;
    private final List<Row> resultRows;

    private final int startRow;
    private final int startColumn;

    private final int blockSize;

    public FoxBlockAddingThread(List<Matrix> blocksNeedsToBeAdded, List<Row> resultRows, int startRow, int startColumn) {

        this.blocksNeedsToBeAdded = blocksNeedsToBeAdded;
        this.resultRows = resultRows;
        this.blockSize = blocksNeedsToBeAdded.get(0).getSize();
        this.startRow = startRow;
        this.startColumn = startColumn;
    }

    @Override
    public void run() {

        for (Matrix block: blocksNeedsToBeAdded) {

            for (int i = 0; i < blockSize; i++) {

                for (int j = 0; j < blockSize; j++) {

                    resultRows.get(i+ startRow).addValue(block.getItem(i, j), j+ startColumn);
                }
            }
        }
    }
}
