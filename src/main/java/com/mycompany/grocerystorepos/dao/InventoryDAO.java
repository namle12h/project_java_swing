/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.dao;

/**
 *
 * @author LENOVO
 */
import com.mycompany.grocerystorepos.conDB.connectDB;
import com.mycompany.grocerystorepos.model.Inventory;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class InventoryDAO {

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    // Lấy danh sách tồn kho của tất cả sản phẩm
    public List<Inventory> getAllInventories() {
        List<Inventory> inventories = new ArrayList<>();
        String query = "SELECT ProductID, Quantity_in_stock FROM Inventory";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Inventory item = new Inventory(
                        rs.getString("productCode"),
                        rs.getString("productName"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("unit")
                );
                inventories.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventories;
    }

    // Lấy thông tin tồn kho của một sản phẩm theo ProductID
    public Inventory getInventoryByProductID(int productID) {
        String query = "SELECT ProductID, Quantity_in_stock FROM Inventory WHERE ProductID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Inventory(
                        rs.getString("productCode"),
                        rs.getString("productName"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("unit")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật số lượng tồn kho của một sản phẩm
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

    // Thêm mới một bản ghi tồn kho (ví dụ khi có sản phẩm mới)
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
