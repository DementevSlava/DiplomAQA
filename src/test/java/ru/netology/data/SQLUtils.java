package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils {

    private static String url = "jdbc:mysql://192.168.99.100:3306/app";
    private static String user = "app";
    private static String password = "pass";
    private static Connection conn;


    public static Connection getOldConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return conn;
    }

    public static Connection getNewConnection() {

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return conn;
    }

    public static void cleanDB() {
        SQLUtils.getNewConnection();
        val cleanCreditRequest = "DELETE FROM credit_request_entity";
        val cleanPayment = "DELETE FROM payment_entity";
        val cleanOrder = "DELETE FROM order_entity";
        val runner = new QueryRunner();
        try (val conn = getNewConnection()) {
            runner.update(conn, cleanCreditRequest);
            runner.update(conn, cleanPayment);
            runner.update(conn, cleanOrder);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getPaymentStatus() throws SQLException {
        val selectStatus = "SELECT * FROM payment_entity";
        val runner = new QueryRunner();
        try (val conn = getNewConnection()) {
            val cardStatus = runner.query(conn, selectStatus, new BeanHandler<>(PaymentEntity.class));
            return cardStatus.getStatus();
        }
    }

    public static String getPaymentTransactionId() throws SQLException{
        val selectTransactionId = "SELECT * FROM payment_entity";
        val runner = new QueryRunner();
        try (val conn = getNewConnection()){
            val transactionId = runner.query(conn, selectTransactionId, new BeanHandler<>(PaymentEntity.class));
            return transactionId.getTransactionId();
        }
    }

    public static String getCreditRequestStatus() throws SQLException {
        val selectStatus = "SELECT * FROM credit_request_entity";
        val runner = new QueryRunner();
        try (val conn = getNewConnection()) {
            val cardStatus = runner.query(conn, selectStatus, new BeanHandler<>(CreditRequestEntity.class));
            return cardStatus.getStatus();
        }
    }

    public static String getCreditRequestBankId() throws SQLException{
        val selectBankId = "SELECT * FROM credit_request_entity";
        val runner = new QueryRunner();
        try (val conn = getNewConnection()){
            val bankId = runner.query(conn, selectBankId, new BeanHandler<>(CreditRequestEntity.class));
            return bankId.getBankId();
        }
    }

    public static String getOrderPaymentId() throws SQLException{
        val selectPaymentId = "SELECT * FROM order_entity";
        val runner = new QueryRunner();
        try (val conn = getNewConnection()){
            val paymentId = runner.query(conn, selectPaymentId, new BeanHandler<>(OrderEntity.class));
            return paymentId.getPaymentId();
        }
    }
}
