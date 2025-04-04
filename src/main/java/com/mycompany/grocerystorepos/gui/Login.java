/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.grocerystorepos.gui;


import com.mycompany.grocerystorepos.controller.ProductController;
import com.mycompany.grocerystorepos.dao.Employee1DAO;
import com.mycompany.grocerystorepos.dao.ProductDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author ADMIN
 */
public class Login extends javax.swing.JPanel {

    /**
     * Creates new form Login
     */
    
    
    public Login() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        edtUsername = new javax.swing.JTextField();
        edtPassword = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        btnForget = new javax.swing.JButton();
        bntReset = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Đăng Nhập Tài Khoản Của Bạn");

        jLabel2.setText("Username");

        jLabel3.setText("Password");

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnForget.setText("Forget Password");
        btnForget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForgetActionPerformed(evt);
            }
        });

        bntReset.setText("Reset");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                        .addGap(59, 59, 59))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(edtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(edtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnLogin)
                        .addGap(18, 18, 18)
                        .addComponent(btnForget)
                        .addGap(18, 18, 18)
                        .addComponent(bntReset)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(edtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(edtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnForget)
                    .addComponent(bntReset))
                .addGap(28, 28, 28))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;user=sa;password=123;encrypt=false;";

    try {
        // Đăng ký driver JDBC
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // Mở kết nối đến cơ sở dữ liệu
        conn = DriverManager.getConnection(connectionUrl);

        String username = edtUsername.getText();
        String password = new String(edtPassword.getText()); // Lấy mật khẩu từ JPasswordField

        // Kiểm tra đăng nhập trong bảng Employee1
        String SQL = "SELECT * FROM Employee1 WHERE Username=? AND Password=?";
        pstmt = conn.prepareStatement(SQL);
        pstmt.setString(1, username);
        pstmt.setString(2, password);  // So sánh mật khẩu đã nhập
        rs = pstmt.executeQuery();

        if (rs.next()) {
            String employeeName = rs.getString("Name");
            int employeeId = rs.getInt("EmployeeID");
            String role = rs.getString("Role");
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Chào " + employeeName);

            
            
            // Chuyển sang Main view và hiển thị tên nhân viên
            Main mainView = new Main(role); // Tạo một Main view mới
            mainView.setEmployeeName(employeeName);  // Gọi phương thức để hiển thị tên nhân viên

            // Hiển thị cửa sổ Main
            mainView.setVisible(true);
            this.setVisible(false);  // Ẩn cửa sổ Login
        } else {
            JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không chính xác!");
            edtUsername.setText("");
            edtPassword.setText("");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi đăng nhập: " + e.getMessage());
    }
}



    private void btnForgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForgetActionPerformed
           int option = JOptionPane.showConfirmDialog(null, 
            "Bạn có muốn reset password không?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // Người dùng chọn "Yes"
            System.out.println("User chose Yes");
            // Thực hiện hành động reset password
        } else if (option == JOptionPane.NO_OPTION) {
            // Người dùng chọn "No"
            System.out.println("User chose No");
        } else {
            // Người dùng đóng hộp thoại mà không chọn gì (Cancel hoặc đóng)
            System.out.println("User closed the dialog");
        }
    
    }//GEN-LAST:event_btnForgetActionPerformed

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntReset;
    private javax.swing.JButton btnForget;
    private javax.swing.JButton btnLogin;
    private javax.swing.JTextField edtPassword;
    private javax.swing.JTextField edtUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
