package simple.primenumber;

import java.util.List;

/**
 * Created by alexsch on 2/16/2017.
 */
public interface PrimeNumberCalculator {

    void run(boolean flag);

    void shutdown();

    void setStorage(PrimeNumberStorage storage);
}
