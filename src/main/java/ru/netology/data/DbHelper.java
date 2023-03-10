package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class DbHelper {
    private static final String URL = System.getProperty("db.url");
    private static final String USER = System.getProperty("db.user");
    private static final String PASSWORD = System.getProperty("db.password");

    private DbHelper() {
    }

    public static List<String> getPaymentStatuses() {
        var statusBD = "SELECT status " +
                "FROM payment_entity";
        var runner = new QueryRunner();
        try (var conn = getConnection(URL, USER, PASSWORD)) {
            return runner.query(conn, statusBD, new ColumnListHandler<>());
        } catch (SQLException e) {
            throw new AssertionError("Ошибка при подключении к БД: " + e.getMessage());
        }
    }

    public static List<String> getCreditStatuses() {
        var statusBD = "SELECT status " +
                "FROM credit_request_entity";
        var runner = new QueryRunner();
        try (var conn = getConnection(URL, USER, PASSWORD)) {
            return runner.query(conn, statusBD, new ScalarHandler<>());
        } catch (SQLException e) {
            throw new AssertionError("Ошибка при подключении к БД: " + e.getMessage());
        }
    }

    public static void cleanDataBase() {
        var deletePayment = "DELETE FROM payment_entity";
        var deleteCredit = "DELETE FROM credit_request_entity";
        var deleteOrder = "DELETE FROM order_entity";
        var runner = new QueryRunner();
        try (var conn = getConnection(URL, USER, PASSWORD)) {
            runner.update(conn, deletePayment);
            runner.update(conn, deleteCredit);
            runner.update(conn, deleteOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}