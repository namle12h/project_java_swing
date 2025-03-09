/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.controller;

/**
 *
 * @author LENOVO
 */

import com.mycompany.grocerystorepos.conDB.connectDB;
import com.mycompany.grocerystorepos.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager {

    // Lấy danh sách tất cả khách hàng từ bảng Customer
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT id, name, phone, email FROM Customer";
        try (Connection con = connectDB.connect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
                list.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm mới một khách hàng vào bảng Customer
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (id, name, phone, email) VALUES (?, ?, ?, ?)";
        try (Connection con = connectDB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getEmail());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin khách hàng trong bảng Customer
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE Customer SET name = ?, phone = ?, email = ? WHERE id = ?";
        try (Connection con = connectDB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa khách hàng khỏi bảng Customer theo id
    public boolean deleteCustomer(String customerId) {
        String sql = "DELETE FROM Customer WHERE id = ?";
        try (Connection con = connectDB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
