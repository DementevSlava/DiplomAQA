package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLUtils {

    private static String url = "jdbc:mysql://192.168.99.100:3306/app";
    private static String user = "app";
    private static String password = "pass";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
return conn;
    }

    public static void cleanDB(){
        SQLUtils.getConnection();
        val cleanCreditRequest = "DELETE FROM credit_request_entity;";
        val cleanPayment = "DELETE FROM payment_entity;";
        val cleanOrder = "DELETE FROM order_entity;";
        val runner = new QueryRunner();
        try (val conn = getConnection()) {
            runner.update(conn, cleanCreditRequest);
            runner.update(conn, cleanPayment);
            runner.update(conn, cleanOrder);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  static String getPaymentStatus() throws SQLException {
        val sQLStatus = "SELECT * FROM payment_entity";
        val runner = new QueryRunner();
        try (val conn = getConnection()){
            val cardStatus = runner.query(conn, sQLStatus, new BeanHandler<>(PaymentEntity.class));
            return cardStatus.getStatus();
        }
    }


}
