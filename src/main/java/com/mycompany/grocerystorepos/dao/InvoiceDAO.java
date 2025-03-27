/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.dao;
import com.mycompany.grocerystorepos.model.CurrentUser;
import com.mycompany.grocerystorepos.model.Product;
import database.DBConnection;
import java.sql.*;
import java.util.List;

public class InvoiceDAO {





public int getEmployeeIdByName(String employeeName) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int employeeId = -1;

    try {
        conn = DBConnection.getConnection();
        String sql = "SELECT EmployeeID FROM Employee1 WHERE Name = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, employeeName);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            employeeId = rs.getInt("EmployeeID");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return employeeId;
}

        
         // Phương thức để lưu hóa đơn vào cơ sở dữ liệu
    public boolean saveInvoice(int customerId, double totalAmount, double discountAmount, double finalAmount, List<Product> cartItems,int employeeID, String paymentMethod) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int invoiceId = -1;

        try {
            // Mở kết nối cơ sở dữ liệu (ví dụ: dùng DriverManager.getConnection())
            conn = DBConnection.getConnection(); // Giả sử Database là lớp để kết nối đến DB
            
         
            // 1. Lưu thông tin hóa đơn vào bảng invoices
            String sqlInvoice = "INSERT INTO invoices (customer_id, total_amount, discount_amount, final_amount, invoice_date, EmployeeID,payment_method) VALUES (?, ?, ?, ?, GETDATE(),?,?)";
            pstmt = conn.prepareStatement(sqlInvoice, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, customerId);
            pstmt.setDouble(2, totalAmount);
            pstmt.setDouble(3, discountAmount);
            pstmt.setDouble(4, finalAmount);
            pstmt.setDouble(5, employeeID);
            pstmt.setString(6, paymentMethod);  // Thêm phương thức thanh toán
            pstmt.executeUpdate();

            // Lấy invoice_id vừa tạo
            var rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                invoiceId = rs.getInt(1);
            }

            // 2. Lưu chi tiết hóa đơn vào bảng invoice_details
            if (invoiceId != -1) {
                String sqlDetails = "INSERT INTO invoice_details (invoice_id, product_name, quantity, price, total_price) VALUES (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sqlDetails);

                for (Product product : cartItems) {
                    pstmt.setInt(1, invoiceId);
                    pstmt.setString(2, product.getProductName());
                    pstmt.setInt(3, product.getQuantity());
                    pstmt.setDouble(4, product.getPrice());
                    pstmt.setDouble(5, product.getTotalPrice());
                    pstmt.addBatch(); // Thêm vào batch để tối ưu hóa việc insert nhiều bản ghi
                }
                pstmt.executeBatch(); // Thực thi batch insert

                return true; // Lưu thành công
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi, trả về false
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Nếu không có invoiceId hợp lệ
    }
}
//}