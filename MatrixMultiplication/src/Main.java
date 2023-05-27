import models.Matrix;
import models.Result;
import stripe.StripeCalculator;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        int matrixSize = 1000;

        Matrix m = new Matrix(matrixSize);
        Matrix m2 = new Matrix(matrixSize);

        Instant start = Instant.now();
        Result result = StripeCalculator.multiply(m, m2, matrixSize);
        long millis = Duration.between(start, Instant.now()).toMillis();

        result.print();
        System.out.println("Result time = " + millis + " ms");
    }
}