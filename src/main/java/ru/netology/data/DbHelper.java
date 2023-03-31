package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import static java.sql.DriverManager.getConnection;

public class DbHelper {
    private static final String URL = System.getProperty("db.url");
    private static final String USER = System.getProperty("db.user");
    private static final String PASSWORD = System.getProperty("db.password");

    private DbHelper() {
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        var statusBD = "SELECT status " +
                "FROM payment_entity " +
                "LIMIT 1";
        var conn = getConnection(URL, USER, PASSWORD);
        return new QueryRunner().query(conn, statusBD, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCreditStatus() {
        var statusBD = "SELECT status " +
                "FROM credit_request_entity " +
                "LIMIT 1";
        var conn = getConnection(URL, USER, PASSWORD);
        return new QueryRunner().query(conn, statusBD, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var deletePayment = "DELETE FROM payment_entity";
        var deleteCredit = "DELETE FROM credit_request_entity";
        var deleteOrder = "DELETE FROM order_entity";
        var conn = getConnection(URL, USER, PASSWORD);
        var runner = new QueryRunner();
        runner.update(conn, deletePayment);
        runner.update(conn, deleteCredit);
        runner.update(conn, deleteOrder);
    }
}