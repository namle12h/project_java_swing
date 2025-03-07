package com.mycompany.grocerystorepos.dao;

import com.mycompany.grocerystorepos.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    // Lấy danh sách tất cả sản phẩm từ CSDL
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Unit"),
                        rs.getString("SupplierID"),
                        rs.getInt("MinStock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Thêm sản phẩm mới vào CSDL
//    public void addProduct(Product product) {
//        String query = "INSERT INTO Product (ProductName, Price, Quantity, Unit, Supplier, MinStock) VALUES (?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setString(1, product.getProductName());
//            pstmt.setDouble(2, product.getPrice());
//            pstmt.setInt(3, product.getQuantity());
//            pstmt.setString(4, product.getUnit());
//            pstmt.setString(5, product.getSupplier());
//            pstmt.setInt(6, product.getMinStock());
//
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void addProduct(Product product) {
        String query = "INSERT INTO Product (ProductName, Price, Quantity, Unit, SupplierID, MinStock) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setString(4, product.getUnit());
            pstmt.setString(5, product.getSupplier());
            pstmt.setInt(6, product.getMinStock());

            int rowsInserted = pstmt.executeUpdate(); // Trả về số dòng bị ảnh hưởng
            if (rowsInserted > 0) {
                System.out.println("✅ Thêm sản phẩm thành công: " + product.getProductName());
            } else {
                System.out.println("❌ Không có dòng nào được thêm vào database!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi SQL khi thêm sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Cập nhật thông tin sản phẩm
    public void updateProduct(Product product) {
        String query = "UPDATE Product SET ProductName=?, Price=?, Quantity=?, Unit=?, SupplierID=? WHERE ProductID=?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setString(4, product.getUnit());
            pstmt.setString(5, product.getSupplier());
            pstmt.setInt(6, product.getProductID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa sản phẩm khỏi CSDL
    public void deleteProduct(int productID) {
        String query = "DELETE FROM Product WHERE ProductID=?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tìm sản phẩm theo ID
    public Product getProductByID(int productID) {
        String query = "SELECT * FROM Product WHERE ProductID=?";
        Product product = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Unit"),
                        rs.getString("SupplierID"),
                        rs.getInt("MinStock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
