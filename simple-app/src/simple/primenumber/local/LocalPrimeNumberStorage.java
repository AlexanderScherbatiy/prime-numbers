package simple.primenumber.local;

import simple.primenumber.PrimeNumberStorage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexsch on 2/17/2017.
 */
public class LocalPrimeNumberStorage implements PrimeNumberStorage {

    private final List<Integer> primes = new ArrayList<>();

    public LocalPrimeNumberStorage() {
        primes.add(2);
    }

    @Override
    public void addPrimeNumber(int primeNumber) {
        primes.add(primeNumber);
    }

    @Override
    public List<Integer> getPrimeNumbers() {
        return primes;
    }

    @Override
    public int getPrimNumbersCount() {
        return primes.size();
    }

    @Override
    public PrimeNumberItem getLastPrimeNumberItem() {
        int n = primes.size() - 1;
        int prime = primes.get(n);
        return new PrimeNumberItem(n, prime);
    }

    @Override
    public List<PrimeNumberItem> getLastPrimeNumberItems(int number) {
        List<PrimeNumberItem> lastPrimeNumbers = new LinkedList<>();
        int N = primes.size();
        int n = number > N ? N : number;
        for (int i = N - n; i < N; i++) {
            int prime = primes.get(i);
            lastPrimeNumbers.add(new PrimeNumberItem(i, prime));
        }
        return lastPrimeNumbers;
    }
}
