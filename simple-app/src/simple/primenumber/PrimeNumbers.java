package simple.primenumber;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexsch on 2/15/2017.
 */
public class PrimeNumbers {

    private final Lock lock = new ReentrantLock();
    private volatile boolean run = false;
    private volatile boolean shutdown = false;
    private List<Integer> primes = new ArrayList<>();

    public PrimeNumbers() {
        new Thread(new PrimeNumbersCalculator()).start();
    }

    public void run(boolean flag) {
        run = flag;
    }

    public void shutdown() {
        shutdown = true;
    }

    public Pair getLastPrimeNumber() {
        lock.lock();
        try {
            int lastIndex = primes.size() - 1;
            return new Pair(lastIndex, primes.get(lastIndex));
        } finally {
            lock.unlock();
        }
    }

    public List<Pair> getLastPrimeNumbers(int number) {
        lock.lock();
        List<Pair> lastPrimeNumbers = new LinkedList<>();
        try {
            int N = primes.size();
            int n = number > N ? N : number;
            for (int i = N - n; i < N; i++) {
                int prime = primes.get(i);
                lastPrimeNumbers.add(new Pair(i, prime));
            }
            return lastPrimeNumbers;
        } finally {
            lock.unlock();
        }
    }

    public class Pair {
        private final int num;
        private final int prime;

        public Pair(int num, int prime) {
            this.num = num;
            this.prime = prime;
        }

        public int getNum() {
            return num;
        }

        public int getPrime() {
            return prime;
        }

        @Override
        public String toString() {
            return String.format("prime number[%d] = %d", num, prime);
        }
    }

    private class PrimeNumbersCalculator implements Runnable {
        List<Integer> localPrimes = new LinkedList<>();

        @Override
        public void run() {

            int localCount = 2;
            addPrime(localCount);

            mainLoop:
            while (true) {

                if (shutdown) {
                    return;
                }

                if (!run) {
                    continue;
                }

                localCount++;
                for (int prime : localPrimes) {
                    if (localCount % prime == 0) {
                        continue mainLoop;
                    }
                }

                addPrime(localCount);
            }
        }

        void addPrime(int prime) {
            localPrimes.add(prime);
            lock.lock();
            try {
                primes.add(prime);
            } finally {
                lock.unlock();
            }
        }
    }
}