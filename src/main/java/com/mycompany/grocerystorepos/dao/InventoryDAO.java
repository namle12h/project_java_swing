package com.mycompany.grocerystorepos.dao;

import com.mycompany.grocerystorepos.model.Inventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    // Lấy đối tượng Inventory theo ProductID
    public Inventory getInventoryByProductId(int productId) {
        Inventory inv = null;
        String sql = "SELECT * FROM Inventory WHERE ProductID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                inv = new Inventory(
                        rs.getInt("InventoryID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity"),
                        rs.getTimestamp("LastUpdated")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inv;
    }

    // INSERT Inventory mới (LastUpdated tự động lấy giá trị GETDATE() theo định nghĩa của bảng)
    public boolean insertInventory(Inventory inv) {
        String sql = "INSERT INTO Inventory(ProductID, Quantity) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, inv.getProductId());
            stmt.setInt(2, inv.getQuantity());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE Inventory hiện có: cập nhật số lượng và cập nhật LastUpdated theo thời gian hiện tại
    public boolean updateInventory(Inventory inv) {
        String sql = "UPDATE Inventory SET Quantity = ?, LastUpdated = GETDATE() WHERE InventoryID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, inv.getQuantity());
            stmt.setInt(2, inv.getInventoryId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật số lượng tồn kho theo ProductID với delta số lượng
    // - Nếu chưa có bản ghi cho ProductID và delta > 0 => INSERT mới
    // - Nếu đã có thì cộng dồn (hoặc trừ nếu bán hàng) và cập nhật LastUpdated
    public boolean updateInventoryQuantity(int productId, int quantityDelta) {
        Inventory inv = getInventoryByProductId(productId);
        if (inv == null) {
            if (quantityDelta > 0) {
                // Nếu là nhập hàng và chưa có bản ghi tồn kho => INSERT mới
                return insertInventory(new Inventory(productId, quantityDelta));
            } else {
                // Nếu số lượng âm mà chưa có bản ghi thì báo lỗi
                System.out.println("Không tồn tại bản ghi tồn kho cho ProductID: " + productId);
                return false;
            }
        } else {
            int newQuantity = inv.getQuantity() + quantityDelta;
            if (newQuantity < 0) {
                // Không cho phép số lượng âm
                System.out.println("Số lượng không đủ cho ProductID: " + productId);
                return false;
            }
            inv.setQuantity(newQuantity);
            return updateInventory(inv);
        }
    }

    // Lấy danh sách tất cả các bản ghi Inventory
    public List<Inventory> getAllInventories() {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT * FROM Inventory";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Inventory inv = new Inventory(
                        rs.getInt("InventoryID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity"),
                        rs.getTimestamp("LastUpdated")
                );
                list.add(inv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
