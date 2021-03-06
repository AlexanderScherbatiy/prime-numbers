package simple.primenumber.commandline;


import java.util.List;
import java.util.Scanner;

import simple.primenumber.PrimeNumberStorage;
import simple.primenumber.PrimeNumberStorage.PrimeNumberItem;
import simple.primenumber.PrimeNumberCalculator;
import simple.primenumber.PrimeNumberView;

/**
 * Created by alexsch on 2/15/2017.
 */
public class CommandLineView implements PrimeNumberView {

    private static final String WELCOME = "Welcome";
    private static final String GOODBYE = "Goodbye";
    private static final String RUN = "run";
    private static final String PAUSE = "pause";
    private static final String HELP = "help";
    private static final String EXIT = "exit";
    private static final String LAST_PRIME_NUMBER = "last prime number";
    private static final String LAST_PRIME_NUMBERS = "last 10 prime numbers";

    private PrimeNumberStorage storage;
    private PrimeNumberCalculator calculator;


    private static final String[] COMMANDS = {
            HELP,
            LAST_PRIME_NUMBER,
            LAST_PRIME_NUMBERS,
            RUN,
            PAUSE,
            EXIT
    };

    @Override
    public void setStorage(PrimeNumberStorage storage) {
        this.storage = storage;
    }

    @Override
    public void setCalculator(PrimeNumberCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void start() {

        System.out.println(WELCOME);
        showCommands();
        Scanner in = new Scanner(System.in);

        mainLoop:
        while (true) {
            String command = in.next();

            try {
                int i = Integer.valueOf(command);
                if (i < 0 || i >= COMMANDS.length) {
                    System.out.printf("command %d is out of range.\n", i);
                    continue;
                }

                command = COMMANDS[i];
                System.out.printf("> %d) %s\n", i, command);

            } catch (NumberFormatException e) {
                // not a command
            }

            switch (command) {

                case HELP:
                    showCommands();
                    break;
                case LAST_PRIME_NUMBER:
                    PrimeNumberItem lastPrimeNumber = storage.getLastPrimeNumberItem();
                    System.out.printf("last %s\n", lastPrimeNumber);
                    break;

                case LAST_PRIME_NUMBERS:
                    List<PrimeNumberItem> lastPrimeNumbers =
                            storage.getLastPrimeNumberItems(10);
                    for (PrimeNumberItem primeNumberItem : lastPrimeNumbers) {
                        System.out.println(primeNumberItem);
                    }
                    break;
                case RUN:
                    calculator.run(true);
                    break;
                case PAUSE:
                    calculator.run(false);
                    break;
                case EXIT:
                    calculator.shutdown();
                    break mainLoop;
                default:
                    System.out.printf("Unknown command: '%s'\n", command);
            }
        }

        System.out.println(GOODBYE);
    }

    private static void showCommands() {
        for (int i = 0; i < COMMANDS.length; i++) {
            System.out.printf("%d) %s\n", i, COMMANDS[i]);
        }
    }
}