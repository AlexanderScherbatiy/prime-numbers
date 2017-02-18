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
    private static final String TEMPLATE_LAST_PRIME_NUMBER = "SELECT * FROM %1$s where %2$s=(select max(%2$s) from %1$s)";

    private static final String DB_NAME = "PrimeNumberDB";
    private static final String TABLE_NAME = "PRIME_NUMBER";
    private static final String COLUMN_INDEX = "INDEX";
    private static final String COLUMN_PRIME = "PRIME";
    private static final String DB_URL = String.format(TEMPLATE_DB_URL, DB_NAME);
    private static final String SQL_PRIME_NUMBER_COUNT = String.format(TEMPLATE_PRIME_COUNT, COLUMN_INDEX, TABLE_NAME);
    private static final String SQL_PRIME_NUMBERS = String.format(TEMPLATE_PRIME_NUMBERS, TABLE_NAME);
    private static final String SQL_LAST_PRIME_NUMBER = String.format(TEMPLATE_LAST_PRIME_NUMBER, TABLE_NAME, COLUMN_INDEX);


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
                    addPrimeNumber(2);
                    addPrimeNumber(3);
                    addPrimeNumber(5);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPrimeNumber(int primeNumber) {
        try {
            statement.execute(String.format(TEMPLATE_INSERT_PRIME, TABLE_NAME,
                    getPrimNumbersCount(), primeNumber));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        try (ResultSet resultSet = statement.executeQuery(SQL_PRIME_NUMBER_COUNT)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PrimeNumberItem getLastPrimeNumberItem() {
        try (ResultSet resultSet = statement.executeQuery(SQL_LAST_PRIME_NUMBER)) {
            if (resultSet.next()) {
                int index = resultSet.getInt(COLUMN_INDEX);
                int primeNumber = resultSet.getInt(COLUMN_PRIME);
                return new PrimeNumberItem(index, primeNumber);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PrimeNumberItem> getLastPrimeNumberItems(int number) {
        return null;
    }
}
