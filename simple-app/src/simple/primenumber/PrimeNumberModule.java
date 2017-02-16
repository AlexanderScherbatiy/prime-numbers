package simple.primenumber;

import java.util.List;

/**
 * Created by alexsch on 2/16/2017.
 */
public interface PrimeNumberModule {

    void run(boolean flag);

    void shutdown();

    List<Pair> getLastPrimeNumbers(int number);

    public class Pair {
        private final int index;
        private final int prime;

        public Pair(int index, int prime) {
            this.index = index;
            this.prime = prime;
        }

        public int getIndex() {
            return index;
        }

        public int getPrime() {
            return prime;
        }

        @Override
        public String toString() {
            return String.format("prime number[%d] = %d", index, prime);
        }
    }
}
