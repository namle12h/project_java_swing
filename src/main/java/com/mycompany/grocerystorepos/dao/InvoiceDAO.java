/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.dao;
import com.mycompany.grocerystorepos.model.Product;
import database.DBConnection;
import java.sql.*;
import java.util.List;

public class InvoiceDAO {

    // Lưu hóa đơn và chi tiết hóa đơn vào database
    public void saveInvoice(int customerId, double totalAmount, double discountAmount, double finalAmount, List<Product> cartItems) {
        Connection conn = null;
        try {
            // Lấy kết nối từ DBConnection
            conn = DBConnection.getConnection();
            
            // 1. Lưu thông tin hóa đơn vào bảng invoices
            String insertInvoiceSQL = "INSERT INTO invoices (customer_id, total_amount, discount_amount, final_amount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertInvoiceSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, customerId);
                stmt.setDouble(2, totalAmount);
                stmt.setDouble(3, discountAmount);
                stmt.setDouble(4, finalAmount);
                stmt.executeUpdate();

                // Lấy mã hóa đơn vừa tạo
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int invoiceId = rs.getInt(1);  // Lấy invoiceId từ kết quả vừa chèn

                    // 2. Lưu thông tin chi tiết sản phẩm vào bảng invoice_details
                    String insertDetailSQL = "INSERT INTO invoice_details (invoice_id, product_name, quantity, price, total_price) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement detailStmt = conn.prepareStatement(insertDetailSQL)) {
                        for (Product product : cartItems) {
                            detailStmt.setInt(1, invoiceId); // Gán invoice_id cho các chi tiết sản phẩm
                            detailStmt.setString(2, product.getProductName());
                            detailStmt.setInt(3, product.getQuantity());
                            detailStmt.setDouble(4, product.getPrice());
                            detailStmt.setDouble(5, product.getTotalPrice());
                            detailStmt.addBatch();  // Thêm vào batch để chèn nhiều bản ghi
                        }
                        detailStmt.executeBatch();  // Thực hiện batch insert
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối sau khi sử dụng
//            DBConnection.closeConnection(conn);
        }
    }
}
