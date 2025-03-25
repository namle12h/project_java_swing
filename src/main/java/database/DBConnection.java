/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Các thông tin kết nối tới database
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    // Phương thức tạo kết nối đến database
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Kết nối đến database
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("ket noi thanh cong!");
        } catch (SQLException e) {
            System.err.println("ket noi that bai!");
            e.printStackTrace();
        }
        return conn;
    }

    // Phương thức đóng kết nối (nếu cần)
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("dong ket noi");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
