package fox;

import models.Matrix;
import java.util.List;

public class FoxBlockMultiplierThread implements Runnable {

    private final List<Matrix> blocks;
    private final Matrix leftMatrix;
    private final Matrix rightMatrix;

    private final FoxBlock leftBlock;
    private final FoxBlock rightBlock;

    public FoxBlockMultiplierThread(List<Matrix> blocks,
                                    Matrix leftMatrix,
                                    Matrix rightMatrix,
                                    FoxBlock leftBlock,
                                    FoxBlock rightBlock) {

        this.blocks = blocks;
        this.leftMatrix = leftMatrix;
        this.rightMatrix = rightMatrix;
        this.leftBlock = leftBlock;
        this.rightBlock = rightBlock;
    }

    @Override
    public void run() {

        Matrix newBlock = new Matrix(multiplyMatrixes(leftMatrix.getData(), rightMatrix.getData()));

        blocks.add(newBlock);
    }

    private double[][] multiplyMatrixes(double[][] lhs, double[][] rhs) {

        int matrixSize = leftBlock.endRow - leftBlock.startRow;

        double[][] multiplied = new double[matrixSize][matrixSize];

        for (int rowIndex = leftBlock.startRow; rowIndex < leftBlock.endRow; rowIndex++) {

            for (int writeColumnIndex = rightBlock.startColumn; writeColumnIndex < rightBlock.endColumn; writeColumnIndex++) {

                int item = 0;

                for (int leftColumnIndex = leftBlock.startColumn; leftColumnIndex < leftBlock.endColumn; leftColumnIndex++) {

                    item += lhs[rowIndex][leftColumnIndex] * rhs[leftColumnIndex][writeColumnIndex];
                }

                multiplied[rowIndex%matrixSize][writeColumnIndex%matrixSize] = item;
            }
        }

        return multiplied;
    }
}
