package simple.primenumber.db;

import simple.primenumber.PrimeNumberStorage;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexsch on 2/17/2017.
 */
public class DBPrimeNumberStorage implements PrimeNumberStorage {

    private static final String TEMPLATE_DB_URL = "jdbc:derby:%s;create=true";
    private static final String TEMPLATE_CREATE_TABLE = "create table %s(%s int, %s int)";
    private static final String TEMPLATE_INSERT_PRIME = "insert into %s values (%s,%s)";
    private static final String TEMPLATE_PRIME_COUNT = "SELECT COUNT(%s) FROM %s";
    private static final String TEMPLATE_PRIME_NUMBERS = "SELECT * FROM %s";
    private static final String DB_NAME = "PrimeNumberDB";
    private static final String TABLE_NAME = "PRIME_NUMBER";
    private static final String COLUMN_INDEX = "INDEX";
    private static final String COLUMN_PRIME = "PRIME";
    private static final String DB_URL = String.format(TEMPLATE_DB_URL, DB_NAME);
    private static final String SQL_PRIME_NUMBERS = String.format(TEMPLATE_PRIME_NUMBERS, TABLE_NAME);

    private Connection connection;
    private Statement statement;

    public DBPrimeNumberStorage() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();

            initDB();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initDB() {
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            try (ResultSet rs = dbmd.getTables(null, null, TABLE_NAME, null)) {
                if (!rs.next()) {
                    statement.execute(String.format(TEMPLATE_CREATE_TABLE,
                            TABLE_NAME, COLUMN_INDEX, COLUMN_PRIME));
                    addPrimeNumberDB(0, 2);
                    addPrimeNumberDB(1, 3);
                    addPrimeNumberDB(2, 5);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getPrimeCountDB() {
        try (ResultSet resultSet = statement.executeQuery(SQL_PRIME_NUMBERS)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPrimeNumberDB(int index, int prime) {
        try {
            statement.execute(String.format(TEMPLATE_INSERT_PRIME, TABLE_NAME, index, prime));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPrimeNumber(int prime) {
        addPrimeNumberDB(getPrimeCountDB(), prime);
    }

    @Override
    public List<Integer> getPrimeNumbers() {

        List<Integer> primeNumbers = new LinkedList<>();
        try (ResultSet resultSet = statement.executeQuery(SQL_PRIME_NUMBERS)) {

            while (resultSet.next()) {
                int index = resultSet.getInt(COLUMN_INDEX);
                int prime = resultSet.getInt(COLUMN_PRIME);
                primeNumbers.add(prime);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return primeNumbers;
    }

    @Override
    public int getPrimNumbersCount() {
        return getPrimeCountDB();
    }

    @Override
    public Pair getLastPrimeNumber() {
        return null;
    }

    @Override
    public List<Pair> getLastPrimeNumbers(int number) {
        return null;
    }
}
