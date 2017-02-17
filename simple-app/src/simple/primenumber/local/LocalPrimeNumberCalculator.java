package simple.primenumber.local;

import simple.primenumber.PrimeNumberCalculator;
import simple.primenumber.PrimeNumberStorage;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexsch on 2/15/2017.
 */
public class LocalPrimeNumberCalculator implements PrimeNumberCalculator {

    private final Lock lock = new ReentrantLock();
    private volatile boolean run = false;
    private volatile boolean shutdown = false;
    private boolean init = false;
    private PrimeNumberStorage storage;

    public void run(boolean flag) {

        run = flag;
        if (!init) {
            init = true;
            new Thread(new PrimeNumbersRunnable()).start();
        }
    }

    public void shutdown() {
        shutdown = true;
    }

    @Override
    public void setStorage(PrimeNumberStorage storage) {
        this.storage = storage;
    }

    private class PrimeNumbersRunnable implements Runnable {
        List<Integer> localPrimes = new LinkedList<>();

        @Override
        public void run() {

            initPrimes();
            int localCount = localPrimes.get(localPrimes.size() - 1);
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

        void initPrimes() {
            List<Integer> storedPrimes = null;
            lock.lock();
            try {
                storedPrimes = storage.getPrimeNumbers();
            } finally {
                lock.unlock();
            }

            localPrimes.addAll(storedPrimes);
        }

        void addPrime(int prime) {
            localPrimes.add(prime);
            lock.lock();
            try {
                storage.addPrimeNumber(prime);
            } finally {
                lock.unlock();
            }
        }
    }
}