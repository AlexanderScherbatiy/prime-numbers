package simple.storage;

import simple.primenumber.PrimeNumberStorage;
import simple.primenumber.db.DBPrimeNumberStorage;
import simple.primenumber.local.LocalPrimeNumberStorage;

import java.util.List;

/**
 * Created by alexsch on 2/18/2017.
 */
public class PrimeNumberStorageTest {

    private static int[] GOLDEN = {2, 3, 5, 7, 11, 13, 17};

    private static PrimeNumberStorage[] STORAGES = {
            new LocalPrimeNumberStorage(),
            new DBPrimeNumberStorage()
    };

    public static void main(String[] args) {

        for (PrimeNumberStorage storage : STORAGES) {
            test(storage);
        }
    }

    private static void test(PrimeNumberStorage storage) {

        System.out.printf("test %s\n", storage.getClass().getSimpleName());
        List<Integer> primes = storage.getPrimeNumbers();
        testGoldenPrimes(primes);

        checkLastPrimeNumber(primes, storage.getLastPrimeNumberItem());

    }

    private static void testGoldenPrimes(List<Integer> primes) {
        int N = Math.min(primes.size(), GOLDEN.length);

        for (int i = 0; i < N; i++) {
            System.out.printf("prime [%d] = %d\n", i, primes.get(i));
            assertTrue(primes.get(i) == GOLDEN[i],
                    String.format("%d prime is %d instead of %d", i, primes.get(i), GOLDEN[i]));
        }
    }

    private static void checkLastPrimeNumber(List<Integer> primes, PrimeNumberStorage.PrimeNumberItem primeNumberItem) {
        System.out.printf("primeNumberItem: %s\n", primeNumberItem);

        assertTrue(primes.size() - 1 == primeNumberItem.getIndex(), "");
        assertTrue(primes.get(primes.size() - 1) == primeNumberItem.getPrimeNumber(), "");
    }


    private static void assertTrue(boolean result, String message) {
        if (!result) {
            throw new RuntimeException(message);
        }
    }
}
