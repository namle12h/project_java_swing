/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.grocerystorepos.gui;

import com.mycompany.grocerystorepos.controller.CustomerController;
import com.mycompany.grocerystorepos.dao.CustomerDAO;
import com.mycompany.grocerystorepos.model.Customer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class CustomerView extends javax.swing.JFrame {

    public javax.swing.JButton getBtnAdd() {
        return btnadd;
    }

    public javax.swing.JButton getBtnUpdate() {
        return btnupdate;
    }

    public javax.swing.JButton getBtnSave() {
        return btnsave;
    }

    public javax.swing.JButton getBtnDelete() {
        return btndelete;
    }

    public javax.swing.JTable getTable() {
        return tbcustomer;
    }

    private String customerID;

    public String getCusID() {
        return customerID;
    }

    public void setCusID(String id) {
        this.customerID = id;
    }

    public String getCusName() {
        return txtnamekhachhang.getText();
    }

    public String getCusPhone() {
        return txtsodt.getText();
    }

    public String getCusEmail() {
        return txtemail.getText();
    }

    public String getCusPoint() {
        return txtdiemthuong.getText();
    }

    public void setCusPhone(String phone) {
        txtsodt.setText(phone);
    }

    public void setCusName(String name) {
        txtnamekhachhang.setText(name);
    }

    public void setCusEmail(String email) {
        txtemail.setText(email);
    }

    public void setCusPoint(String point) {
        txtdiemthuong.setText(point);
    }
    private List<Customer> list = new ArrayList<>();
    private DefaultTableModel tblModel;
    private final CustomerController controller; // Controller quản lý dữ liệu

    /**
     * Creates new form CustomerView
     */
    public CustomerView() {
        initComponents();
        setLocationRelativeTo(null);
        CustomerDAO model = new CustomerDAO(); // Model xử lý dữ liệu
        controller = new CustomerController(this, model); // Controller quản lý view
        initTable();
//        initData();
    }

    private void fillTable() {
        for (Customer cus : list) {
            tblModel.addRow(new Object[]{
                cus.getId(),
                cus.getName(),
                cus.getPhone(),
                cus.getEmail(),
                cus.getPoint()
            });
        }
        tblModel.fireTableDataChanged();
    }

//    private void initData() {
//        list.add(new Customer("15", "asdasd", "124", "42134", "123"));
//        list.add(new Customer("16", "asdasd", "124", "42134", "123"));
//        list.add(new Customer("13", "asdasd", "124", "42134", "123"));
//        list.add(new Customer("12", "asdasd", "124", "42134", "123"));
//    }
    private void initTable() {
        tblModel = new DefaultTableModel();
        String[] columns = new String[]{"Id", "Name", "Phone", "Email", "Points"};
        tblModel.setColumnIdentifiers(columns);
        tbcustomer.setModel(tblModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtnamekhachhang = new javax.swing.JTextField();
        txtsodt = new javax.swing.JTextField();
        txtdiemthuong = new javax.swing.JTextField();
        btnupdate = new javax.swing.JButton();
        btnadd = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnsave = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btndelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbcustomer = new javax.swing.JTable();
        txtemail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 0, 255));

        jLabel2.setBackground(new java.awt.Color(153, 153, 153));
        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Quản Lý Khách Hàng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel7.setText("Số Điên Thoại");

        txtnamekhachhang.setText("Nhập họ tên...");
        txtnamekhachhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamekhachhangActionPerformed(evt);
            }
        });

        txtsodt.setText("Nhập số điện thoại...");
        txtsodt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsodtActionPerformed(evt);
            }
        });

        txtdiemthuong.setText("Nhập điểm thưởng...");
        txtdiemthuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdiemthuongActionPerformed(evt);
            }
        });

        btnupdate.setText("UPDATE");
        btnupdate.setMaximumSize(new java.awt.Dimension(72, 23));
        btnupdate.setMinimumSize(new java.awt.Dimension(72, 23));
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btnadd.setText("ADD");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        jLabel4.setText("Tên Khách Hàng");

        btnsave.setText("RESET");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        jLabel5.setText("Điểm Thưởng");

        btndelete.setText("DELETE");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        tbcustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, "", null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Phone", "Email", "Points"
            }
        ));
        jScrollPane1.setViewportView(tbcustomer);

        txtemail.setText("Nhập email...");
        txtemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtemailActionPerformed(evt);
            }
        });

        jLabel6.setText("Email");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnadd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(btnupdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(btnsave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32)
                .addComponent(btndelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtemail)
                    .addComponent(txtsodt, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnamekhachhang, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtdiemthuong))
                .addGap(57, 57, 57))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtnamekhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsodt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtdiemthuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnamekhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamekhachhangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamekhachhangActionPerformed

    private void txtsodtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsodtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsodtActionPerformed

    private void txtdiemthuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdiemthuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdiemthuongActionPerformed

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        // TODO add your handling code here:

        StringBuilder sb = new StringBuilder();
// Kiểm tra các trường không được để trống
        if (txtnamekhachhang.getText().trim().isEmpty()) {
            sb.append("Tên khách hàng không được để trống\n");
        }
        if (txtsodt.getText().trim().isEmpty()) {
            sb.append("Số điện thoại không được để trống\n");
        }
        if (txtemail.getText().trim().isEmpty()) {
            sb.append("Email không được để trống\n");
        }
        if (txtdiemthuong.getText().trim().isEmpty()) {
            sb.append("Điểm thưởng không được để trống\n");
        }

// Nếu có lỗi, hiển thị thông báo và dừng lại
        if (sb.length() > 0) {
            JOptionPane.showMessageDialog(null, sb.toString(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }

// Kiểm tra định dạng số điện thoại
        if (!txtsodt.getText().matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại phải có 10-11 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

// Kiểm tra định dạng email
        if (!txtemail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(null, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

// Kiểm tra điểm thưởng có phải là số không
        try {
            Integer.valueOf(txtdiemthuong.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Điểm thưởng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
// Tạo đối tượng khách hàng
        Customer customer = new Customer(
                txtnamekhachhang.getText().trim(),
                txtsodt.getText().trim(),
                txtemail.getText().trim(),
                txtdiemthuong.getText().trim()
        );
        if (list == null) {
            list = new ArrayList<>();
        }
// Kiểm tra trùng mã khách hàng
        for (Customer c : list) {
            if (c.getId().equals(customer.getId())) {
                JOptionPane.showMessageDialog(null, "Mã khách hàng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
            }
        }
        list.add(customer);
        controller.loadCustomers();
        fillTable();
        controller.addCustomer();
    }//GEN-LAST:event_btnaddActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        // TODO add your handling code here:
        txtnamekhachhang.setText("");
        txtsodt.setText("");
        txtdiemthuong.setText("");
        txtemail.setText("");
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = tbcustomer.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int customerId = Integer.parseInt(tbcustomer.getValueAt(selectedRow, 0).toString()); // ✅ Chỉ khai báo 1 lần với kiểu int
            CustomerDAO customerDAO = new CustomerDAO();
            boolean success = customerDAO.deleteCustomer(customerId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.invokeLater(() -> controller.loadCustomers()); // Load lại bảng sau khi xóa
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void txtemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtemailActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
//        fillTable();
        controller.loadCustomers();
        /////////////////////////////////////////////////////////////////////////
        JTextField[] fields = {txtemail, txtnamekhachhang, txtsodt, txtdiemthuong};
        String[] placeholders = {"Nhập email...", "Nhập mã khách hàng...", "Nhập họ tên...", "Nhập số điện thoại...", "Nhập điểm thưởng..."};

        for (int i = 0; i < fields.length; i++) {
            setPlaceholder(fields[i], placeholders[i]);
        }
    }

// Hàm dùng chung để đặt placeholder cho JTextField
    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });

    }//GEN-LAST:event_formWindowOpened

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnupdateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbcustomer;
    private javax.swing.JTextField txtdiemthuong;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtnamekhachhang;
    private javax.swing.JTextField txtsodt;
    // End of variables declaration//GEN-END:variables

}
