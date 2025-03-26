/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.dao;
import com.mycompany.grocerystorepos.model.InvoiceDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO {

    private Connection connection;

    public InvoiceDetailDAO() {
        try {
            // Giả sử bạn đang sử dụng MySQL, thay đổi URL, username, password theo hệ thống của bạn
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerystore", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lưu chi tiết hóa đơn vào cơ sở dữ liệu
    public void save(InvoiceDetail invoiceDetail) {
        String query = "INSERT INTO Invoice_Detail (invoiceId, productId, quantity, price, totalPrice) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, invoiceDetail.getInvoiceId());
            stmt.setInt(2, invoiceDetail.getProductId());
            stmt.setInt(3, invoiceDetail.getQuantity());
            stmt.setDouble(4, invoiceDetail.getPrice());
            stmt.setDouble(5, invoiceDetail.getTotalPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả chi tiết hóa đơn cho một hóa đơn cụ thể
    public List<InvoiceDetail> getInvoiceDetailsByInvoiceId(int invoiceId) {
        List<InvoiceDetail> details = new ArrayList<>();
        String query = "SELECT * FROM Invoice_Detail WHERE invoiceId = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, invoiceId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                InvoiceDetail detail = new InvoiceDetail();
                detail.setId(rs.getInt("id"));
                detail.setInvoiceId(rs.getInt("invoiceId"));
                detail.setProductId(rs.getInt("productId"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setPrice(rs.getDouble("price"));
                detail.setTotalPrice(rs.getDouble("totalPrice"));
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return details;
    }

 
}
