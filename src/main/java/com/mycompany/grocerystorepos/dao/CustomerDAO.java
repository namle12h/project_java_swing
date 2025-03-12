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
        String query = "SELECT id, name, phone, points FROM Customer";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("id"),
                        rs.getString("name"),
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
        String query = "INSERT INTO Customer (id, name, phone, points) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getEmail());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin khách hàng
    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE Customer SET name = ?, phone = ?, points = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa khách hàng theo id
    public boolean deleteCustomer(String customerId) {
        String query = "DELETE FROM Customer WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy thông tin khách hàng theo id
    public Customer getCustomerById(String customerId) {
        String query = "SELECT id, name, phone, points FROM Customer WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("point")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
