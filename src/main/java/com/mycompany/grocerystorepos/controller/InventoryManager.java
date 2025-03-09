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
import com.mycompany.grocerystorepos.model.Inventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    // Lấy danh sách tất cả các sản phẩm (tồn kho) từ bảng Inventory
    public List<Inventory> getAllInventory() {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT productCode, productName, price, quantity, unit FROM Inventory";
        try (Connection con = connectDB.connect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Inventory item = new Inventory(
                        rs.getString("productCode"),
                        rs.getString("productName"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("unit")
                );
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm mới một sản phẩm vào bảng Inventory
    public boolean addInventory(Inventory item) {
        String sql = "INSERT INTO Inventory (productCode, productName, price, quantity, unit) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = connectDB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, item.getProductCode());
            ps.setString(2, item.getProductName());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.setString(5, item.getUnit());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin một sản phẩm trong bảng Inventory
    public boolean updateInventory(Inventory item) {
        String sql = "UPDATE Inventory SET productName = ?, price = ?, quantity = ?, unit = ? WHERE productCode = ?";
        try (Connection con = connectDB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, item.getProductName());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getQuantity());
            ps.setString(4, item.getUnit());
            ps.setString(5, item.getProductCode());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa sản phẩm khỏi bảng Inventory theo productCode
    public boolean deleteInventory(String productCode) {
        String sql = "DELETE FROM Inventory WHERE productCode = ?";
        try (Connection con = connectDB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, productCode);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
