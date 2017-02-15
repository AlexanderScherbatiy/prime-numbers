package simple.comandline;


import java.util.List;
import java.util.Scanner;

import simple.primenumber.PrimeNumbers;

/**
 * Created by alexsch on 2/15/2017.
 */
public class CommandLine {

    private static final String WELCOME = "Welcome";
    private static final String GOODBYE = "Goodbye";
    private static final String RUN = "run";
    private static final String PAUSE = "pause";
    private static final String HELP = "help";
    private static final String EXIT = "exit";
    private static final String LAST_PRIME_NUMBER = "last prime number";
    private static final String LAST_PRIME_NUMBERS = "last 10 prime numbers";

    private static final String[] COMMANDS = {
            HELP,
            LAST_PRIME_NUMBER,
            LAST_PRIME_NUMBERS,
            RUN,
            PAUSE,
            EXIT
    };

    public static void main(String[] args) {

        PrimeNumbers primeNumbers = new PrimeNumbers();
        System.out.println(WELCOME);
        showCommands();
        Scanner in = new Scanner(System.in);

        mainLoop:
        while (true) {
            String command = in.next();

            try {
                int i = Integer.valueOf(command);
                if (i >= 0 && i < COMMANDS.length) {
                    command = COMMANDS[i];
                }
            } catch (NumberFormatException e) {
                // not a command
            }

            switch (command) {

                case HELP:
                    showCommands();
                    break;
                case LAST_PRIME_NUMBER:
                    PrimeNumbers.Pair lastPrimeNumber = primeNumbers.getLastPrimeNumber();
                    System.out.printf("last %s\n", lastPrimeNumber);
                    break;

                case LAST_PRIME_NUMBERS:
                    List<PrimeNumbers.Pair> lastPrimeNumbers =
                            primeNumbers.getLastPrimeNumbers(10);

                    for (PrimeNumbers.Pair pair : lastPrimeNumbers) {
                        System.out.println(pair);
                    }
                    break;
                case RUN:
                    primeNumbers.run(true);
                    break;
                case PAUSE:
                    primeNumbers.run(false);
                    break;
                case EXIT:
                    primeNumbers.shutdown();
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
