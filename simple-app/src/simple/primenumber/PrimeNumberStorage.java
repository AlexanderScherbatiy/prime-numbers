package simple.primenumber;

import java.util.List;

/**
 * Created by alexsch on 2/17/2017.
 */
public interface PrimeNumberStorage {

    void addPrimeNumber(int primeNumber);

    List<Integer> getPrimeNumbers();

    int getPrimNumbersCount();

    PrimeNumberItem getLastPrimeNumberItem();

    List<PrimeNumberItem> getLastPrimeNumberItems(int number);


    public class PrimeNumberItem {
        private final int index;
        private final int primeNumber;

        public PrimeNumberItem(int index, int primeNumber) {
            this.index = index;
            this.primeNumber = primeNumber;
        }

        public int getIndex() {
            return index;
        }

        public int getPrimeNumber() {
            return primeNumber;
        }

        @Override
        public String toString() {
            return String.format("prime number[%d] = %d", index, primeNumber);
        }
    }
}
