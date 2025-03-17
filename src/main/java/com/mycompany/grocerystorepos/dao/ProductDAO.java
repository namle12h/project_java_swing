package com.mycompany.grocerystorepos.dao;

import com.mycompany.grocerystorepos.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Unit"),
                        rs.getString("SupplierID"),
                        rs.getString("Category"),
                        rs.getInt("MinStock")
                );

                // Kiểm tra cảnh báo hàng tồn kho
                if (product.getQuantity() < product.getMinStock()) {
                    System.out.println("⚠️ Cảnh báo: " + product.getProductName() + " có số lượng thấp hơn mức tối thiểu!");
                }

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void addProduct(Product product) {
        String query = "INSERT INTO Product (ProductName, Price, Quantity, Unit, SupplierID, Category, MinStock) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setString(4, product.getUnit());
            pstmt.setString(5, product.getSupplier());
            pstmt.setString(6, product.getCategory());
            pstmt.setInt(7, product.getMinStock());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🛠️ Cập nhật sản phẩm
    public void updateProduct(Product product) {
        String query = "UPDATE Product SET ProductName=?, Price=?, Quantity=?, Unit=?, SupplierID=?, Category=?, MinStock=? WHERE ProductID=?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setString(4, product.getUnit());
            pstmt.setString(5, product.getSupplier());
            pstmt.setString(6, product.getCategory());
            pstmt.setInt(7, product.getMinStock());
            pstmt.setInt(8, product.getProductID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🛠️ Xóa sản phẩm
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

    // 🛠️ Lấy sản phẩm theo ID
    public Product getProductByID(int productID) {
        String query = "SELECT * FROM Product WHERE ProductID=?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Unit"),
                        rs.getString("SupplierID"),
                        rs.getString("Category"),
                        rs.getInt("MinStock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<String> searchProducts(String keyword) {
    List<String> products = new ArrayList<>();
    String sql = "SELECT ProductName FROM Product WHERE ProductName LIKE ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, "%" + keyword + "%"); // Tìm kiếm gần đúng
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            products.add(rs.getString("ProductName"));
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return products;
}

    

    // 🛠️ Lấy danh sách danh mục sản phẩm
//    public ArrayList<String> getAllCategories() {
//        ArrayList<String> categories = new ArrayList<>();
//        String query = "SELECT DISTINCT Category FROM Product";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//
//            while (rs.next()) {
//                categories.add(rs.getString("Category"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return categories;
//    }
    
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT Category FROM Product"; // Lấy tất cả danh mục có trong database

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                categories.add(rs.getString("Category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    
 // 🛠️ Lấy danh sách sản phẩm theo danh mục
    public static List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE Category = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Unit"),
                        rs.getString("SupplierID"),
                        rs.getString("Category"),
                        rs.getInt("MinStock")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public Product getProductByName(String productName) {
    String query = "SELECT * FROM Product WHERE ProductName = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, productName);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new Product(
                rs.getInt("ProductID"),
                rs.getString("ProductName"),
                rs.getDouble("Price"),
                rs.getInt("Quantity"),
                rs.getString("Unit"),
                rs.getString("SupplierID"),
                rs.getString("Category"),
                rs.getInt("MinStock")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


}
