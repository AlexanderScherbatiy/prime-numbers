package simple;

import simple.primenumber.PrimeNumberStorage;
import simple.primenumber.db.DBPrimeNumberStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

/**
 * Created by alexsch on 2/17/2017.
 */
public class PrimeNumberSample {


    public static void main(String[] args) throws Exception {

        PrimeNumberStorage storage = new DBPrimeNumberStorage();
        int count = storage.getPrimNumbersCount();
        List<Integer> primeNumbers = storage.getPrimeNumbers();
        System.out.printf("prime numbers count: %s\n", count);
        for (int prime : primeNumbers) {
            System.out.printf("prime: %d\n", prime);
        }
    }
}
