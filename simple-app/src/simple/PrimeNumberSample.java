package simple;

import simple.primenumber.commandline.CommandLineView;
import simple.primenumber.PrimeNumberCalculator;
import simple.primenumber.PrimeNumberStorage;
import simple.primenumber.PrimeNumberView;
import simple.primenumber.local.LocalPrimeNumberCalculator;
import simple.primenumber.local.LocalPrimeNumberStorage;

/**
 * Created by alexsch on 2/17/2017.
 */
public class PrimeNumberSample {


    public static void main(String[] args) throws Exception {

        PrimeNumberStorage storage = new LocalPrimeNumberStorage();
        //PrimeNumberStorage storage = new DBPrimeNumberStorage();
        PrimeNumberCalculator calculator = new LocalPrimeNumberCalculator();
        calculator.setStorage(storage);
        PrimeNumberView view = new CommandLineView();
        view.setStorage(storage);
        view.setCalculator(calculator);
        view.start();
    }
}
