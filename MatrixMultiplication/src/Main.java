import fox.FoxBlockMultiplierThread;
import fox.FoxCalculator;
import models.Matrix;
import models.Result;
import stripe.StripeCalculator;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

//        measureStripeTime();
//        measureFoxTime();

        Matrix m = new Matrix(500);
        Matrix m2 = new Matrix(500);

        StripeCalculator.multiply(m,m2,500,10).print();
        FoxCalculator.multiply(m,m2,500,50, 16).print();
    }

    private static void measureStripeTime() throws InterruptedException {

        for (int matrixSize : new int[]{100, 500, 1000}) {

            Matrix m = new Matrix(matrixSize);
            Matrix m2 = new Matrix(matrixSize);

            for (int stripesAmount : new int[]{4, 8, 16, 32, 64, 128, 256, 512}) {

                int summ = 0;
                for (int i = 0; i < 10; i++) {

                    Instant start = Instant.now();
                    Result result = StripeCalculator.multiply(m, m2, matrixSize, stripesAmount);
                    summ += Duration.between(start, Instant.now()).toMillis();
                }

                System.out.println("S: " + matrixSize + "; P: " + stripesAmount + "; T: " + summ/10);
            }
        }
    }

    private static void measureFoxTime() throws InterruptedException {

        for (int matrixSize : new int[]{100, 500, 1000}) {

            Matrix m = new Matrix(matrixSize);
            Matrix m2 = new Matrix(matrixSize);

            for (int threadsAmount : new int[]{4, 8, 16, 32, 64, 128, 256, 512}) {

                int summ = 0;
                for (int i = 0; i < 10; i++) {

                    Instant start = Instant.now();
                    Result result = FoxCalculator.multiply(m, m2, matrixSize, matrixSize / 10, threadsAmount);
                    summ += Duration.between(start, Instant.now()).toMillis();
                }

                System.out.println("S: " + matrixSize + "; P: " + threadsAmount + "; T: " + summ/10);
            }
        }
    }
}