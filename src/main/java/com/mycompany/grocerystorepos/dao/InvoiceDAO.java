/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.dao;

import com.mycompany.grocerystorepos.model.Invoice;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    private Connection connection;

    public InvoiceDAO() {
        // Kết nối đến cơ sở dữ liệu
        try {
            // Giả sử bạn đang sử dụng MySQL, thay đổi URL, username, password theo hệ thống của bạn
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerystore", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lưu hóa đơn vào cơ sở dữ liệu
    public void save(Invoice invoice) {
        String query = "INSERT INTO Invoice (customerId, employeeId, totalAmount, discountAmount, date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, invoice.getCustomerId());
            stmt.setInt(2, invoice.getEmployeeId());
            stmt.setDouble(3, invoice.getTotalAmount());
            stmt.setDouble(4, invoice.getDiscountAmount());
            stmt.setDate(5, new java.sql.Date(invoice.getDate().getTime()));
            
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        invoice.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách hóa đơn từ cơ sở dữ liệu
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM Invoice";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setCustomerId(rs.getInt("customerId"));
                invoice.setEmployeeId(rs.getInt("employeeId"));
                invoice.setTotalAmount(rs.getDouble("totalAmount"));
                invoice.setDiscountAmount(rs.getDouble("discountAmount"));
                invoice.setDate(rs.getDate("date"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return invoices;
    }

    // Các phương thức khác tùy theo yêu cầu (cập nhật, xóa, tìm kiếm)
}
