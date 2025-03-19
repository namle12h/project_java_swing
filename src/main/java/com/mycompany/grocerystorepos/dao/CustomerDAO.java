/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.dao;

/**
 *
 * @author LENOVO
 */
import com.mycompany.grocerystorepos.model.Customer;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class CustomerDAO {

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    // Lấy danh sách tất cả khách hàng từ bảng Customer
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("CustomerID"),
                        rs.getString("CustomerName"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("point")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Thêm mới một khách hàng vào bảng Customer
    public boolean addCustomer(Customer customer) {
        String checkQuery = "SELECT COUNT(*) FROM Customer WHERE CustomerID = ?";
        String insertQuery = "INSERT INTO Customer (CustomerID, CustomerName, phone, email, point) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            conn.setAutoCommit(false); // 🔥 Tắt AutoCommit
            // Kiểm tra ID có tồn tại chưa
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, customer.getId());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("❌ ID đã tồn tại, không thể thêm!");
                    return false;
                }
            }

            // Thực hiện thêm nếu ID chưa tồn tại
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, customer.getId());
                pstmt.setString(2, customer.getName());
                pstmt.setString(3, customer.getPhone());
                pstmt.setString(4, customer.getEmail());
                pstmt.setString(5, customer.getPoint());

                int rowsAffected = pstmt.executeUpdate();
                conn.commit();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin khách hàng
    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE Customer SET CustomerName = ?, phone = ?, email = ?, point = ? WHERE CustomerID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getEmail()); // ✅ Thêm email vào đúng vị trí
            pstmt.setString(4, customer.getPoint()); // ✅ Cập nhật điểm thưởng
            pstmt.setString(5, customer.getId());  // ✅ ID phải ở vị trí cuối

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khách hàng theo CustomerID
    public boolean deleteCustomer(String customerId) {
        String query = "DELETE FROM Customer WHERE CustomerID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy thông tin khách hàng theo CustomerID
    public Customer getCustomerById(String customerId) {
        String query = "SELECT CustomerID, CustomerName, phone, email, point FROM Customer WHERE CustomerID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getString("CustomerID"),
                        rs.getString("CustomerName"),
                        rs.getString("phone"),
                        rs.getString("email"), // ✅ Đã thêm đúng `email`
                        rs.getString("point") // ✅ Đã sửa `point` thành `point`
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

 public Customer searchCustomerByPhone(String phone) {
    Customer customer = null;
    String query = "SELECT CustomerID, CustomerName, phone, email, point FROM customer WHERE phone = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setString(1, phone);  // Thiết lập tham số cho câu lệnh SQL
        ResultSet resultSet = pstmt.executeQuery();

        // Nếu tìm thấy khách hàng
        if (resultSet.next()) {
            String customerId = resultSet.getString("CustomerID");
            String name = resultSet.getString("CustomerName");
            String email = resultSet.getString("email");
            String point = resultSet.getString("point");

            customer = new Customer(customerId, name, phone, email, point);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return customer;
}

}