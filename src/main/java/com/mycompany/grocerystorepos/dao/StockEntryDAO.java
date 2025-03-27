package com.mycompany.grocerystorepos.dao;

import com.mycompany.grocerystorepos.model.StockEntry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockEntryDAO {

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    // ✅ Kiểm tra ProductID có tồn tại không
    public boolean isProductExists(int productId) {
        String sql = "SELECT COUNT(*) FROM Product WHERE ProductID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Kiểm tra SupplierID có tồn tại không
    public boolean isSupplierExists(int supplierId) {
        String sql = "SELECT COUNT(*) FROM Supplier WHERE SupplierID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Thêm hàng nhập vào kho (ID tự động tăng)
    public void addStockEntry(StockEntry entry) {
        String sql = "INSERT INTO Stock_Entry (ProductID, SupplierID, Quantity, EntryDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, entry.getProductId());
            stmt.setInt(2, entry.getSupplierId());
            stmt.setInt(3, entry.getQuantity());
            stmt.setTimestamp(4, new Timestamp(entry.getEntryDate().getTime()));

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Lấy ID vừa được sinh
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    entry.setStockEntryId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Cập nhật hàng nhập
    public void updateStockEntry(StockEntry entry) {
        String sql = "UPDATE Stock_Entry SET ProductID = ?, SupplierID = ?, Quantity = ?, EntryDate = ? WHERE EntryID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entry.getProductId());
            stmt.setInt(2, entry.getSupplierId());
            stmt.setInt(3, entry.getQuantity());
            stmt.setTimestamp(4, new Timestamp(entry.getEntryDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Xóa hàng nhập
    public void deleteStockEntry(int stockEntryId) {
        String sql = "DELETE FROM Stock_Entry WHERE EntryID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockEntryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Lấy danh sách hàng nhập
    public List<StockEntry> getAllStockEntries() {
        List<StockEntry> list = new ArrayList<>();
        String sql = "SELECT EntryID, ProductID, SupplierID, Quantity, EntryDate FROM Stock_Entry";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                StockEntry entry = new StockEntry();
                entry.setStockEntryId(rs.getInt("EntryID"));
                entry.setProductId(rs.getInt("ProductID"));
                entry.setSupplierId(rs.getInt("SupplierID"));
                entry.setQuantity(rs.getInt("Quantity"));
                entry.setEntryDate(rs.getTimestamp("EntryDate"));

                list.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
