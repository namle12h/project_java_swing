/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.dao;

/**
 *
 * @author LENOVO
 */
import com.mycompany.grocerystorepos.model.Inventory;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class InventoryDAO {

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    // Lấy danh sách tồn kho của tất cả sản phẩm
    public List<Inventory> getAllInventories() {
        List<Inventory> inventories = new ArrayList<>();
        String query = "SELECT ProductID, Quantity_in_stock FROM Inventory";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Inventory inventory = new Inventory(
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity_in_stock")
                );
                inventories.add(inventory);
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
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity_in_stock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật số lượng tồn kho của một sản phẩm
    public boolean updateInventory(Inventory inventory) {
        String query = "UPDATE Inventory SET Quantity_in_stock = ? WHERE ProductID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, inventory.getQuantityInStock());
            pstmt.setInt(2, inventory.getProductID());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới một bản ghi tồn kho (ví dụ khi có sản phẩm mới)
    public boolean addInventory(Inventory inventory) {
        String query = "INSERT INTO Inventory (ProductID, Quantity_in_stock) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, inventory.getProductID());
            pstmt.setInt(2, inventory.getQuantityInStock());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
