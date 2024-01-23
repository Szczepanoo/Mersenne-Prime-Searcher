import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Random;

public class Main {
    public static boolean lucasLehmerTest(BigInteger n) {

       int certainty = 1;

        if (n.compareTo(BigInteger.valueOf(2)) < 0) {
            return false;
        }
        if (n.compareTo(BigInteger.valueOf(3)) <= 0) {
            return true;
        }

        BigInteger d = n.subtract(BigInteger.ONE);
        int s = 0;
        while (d.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.valueOf(2));
            s++;
        }

        Random rand = new Random();
        for (int i = 0; i < certainty; i++) {
            BigInteger a = new BigInteger(n.bitLength() - 2, rand).add(BigInteger.TWO);
            BigInteger x = a.modPow(d, n);
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
                continue;
            }
            boolean isProbablePrime = false;
            for (int r = 1; r < s; r++) {
                x = x.modPow(BigInteger.TWO, n);
                if (x.equals(BigInteger.ONE)) {
                    return false;
                }
                if (x.equals(n.subtract(BigInteger.ONE))) {
                    isProbablePrime = true;
                    break;
                }
            }
            if (!isProbablePrime) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        int maxExponent = 82589933;
        int numThreads = Runtime.getRuntime().availableProcessors();
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Void>> futures = new ArrayList<>();

        for (int exponent = 1; exponent <= maxExponent; exponent+=2) {
            final int finalExponent = exponent;
            Future<Void> future = executor.submit(() -> {
                BigInteger mersenneExponent = BigInteger.TWO.pow(finalExponent).subtract(BigInteger.ONE);

                if (lucasLehmerTest(mersenneExponent)) {
                    System.out.println("Time: "+ (System.currentTimeMillis() - startTime)/1000 + "s. Mersenne prime for 2^" + finalExponent + " is found: " + mersenneExponent);
                }

                return null;
            });
            futures.add(future);
        }

        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

    }

}