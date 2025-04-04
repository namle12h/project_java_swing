/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.grocerystorepos.gui;

import com.mycompany.grocerystorepos.controller.InvoiceController;
import com.mycompany.grocerystorepos.model.Product;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class Payments extends javax.swing.JDialog {

   

    public Payments(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    
     // Phương thức getter cho các trường private
    public String getCustomerName() {
        return txttenkh.getText();
    }

    public String getEmployeeName() {
            return lbnhanvien.getText();  // Trả về tên nhân viên từ JLabel
        }
    public String getEmail() {
        return txtemail.getText();
    }

    public String getPhone() {
        return txtsdt.getText();
    }

    public double getTotalAmount() {
        return Double.parseDouble(lbtongtien.getText());
    }

    public double getDiscountAmount() {
        return Double.parseDouble(lbchietkhau.getText());
    }

    public double getFinalAmount() {
        return Double.parseDouble(lbthanhtien.getText());
    }

    public JTable getTbhoadon() {
        return tbhoadon;
    }
    
    // Getter cho các radio button
    public boolean isCashSelected() {
        return rdtienmat.isSelected();  // Kiểm tra xem "Tiền mặt" có được chọn không
    }

    public boolean isQrSelected() {
        return rdqr.isSelected();  // Kiểm tra xem "QR" có được chọn không
    }

    

    // Các phương thức setValue để thiết lập giá trị
    public void setCustomerDetails(String customerName, String email, String phone) {
        txttenkh.setText(customerName);
        txtemail.setText(email);
        txtsdt.setText(phone);
    }

    public void setPaymentDetails(double totalAmount, double discountAmount, double finalAmount) {
        lbtongtien.setText(String.valueOf(totalAmount));
        lbchietkhau.setText(String.valueOf(discountAmount));
        lbthanhtien.setText(String.valueOf(finalAmount));
    }
    
   
 

      // Phương thức để thiết lập thông tin khách hàng
//    public void setCustomerDetails(String customerName, String email, String phone) {
//        txttenkh.setText("" + customerName);
//        txtemail.setText("" + email);
//        txtsdt.setText("" + phone);
//    }
//
//    // Phương thức để thiết lập thông tin thanh toán
//    public void setPaymentDetails(double totalAmount, double discountAmount, double finalAmount) {
//        lbtongtien.setText("" + totalAmount);
//        lbchietkhau.setText("" + discountAmount);
//        lbthanhtien.setText("" + finalAmount);
//    }
//    
    
    public void SetEmployeeDetails(String employeeName)
    {
        lbnhanvien.setText(""+employeeName);
    }
    // Phương thức để thiết lập thông tin giỏ hàng (sản phẩm)
    public void setProductList(List<Product> cartItems) {
        DefaultTableModel model = (DefaultTableModel) tbhoadon.getModel();

        // Xóa các dòng cũ trong bảng (nếu có)
        model.setRowCount(0);

        // Thêm các sản phẩm vào bảng
        for (Product product : cartItems) {
                     double totalPrice = product.getTotalPrice();
            model.addRow(new Object[]{
                product.getProductID(),
                product.getProductName(),
                product.getPrice(),
                product.getQuantity(),
                totalPrice
              
            });
        }
    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txttenkh = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtemail = new javax.swing.JLabel();
        txtsdt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbhoadon = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rdtienmat = new javax.swing.JRadioButton();
        rdqr = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        lbnhanvien = new javax.swing.JLabel();
        btninhoadon = new javax.swing.JButton();
        btnhuy = new javax.swing.JButton();
        lbtongtien = new javax.swing.JLabel();
        lbthanhtien = new javax.swing.JLabel();
        lbchietkhau = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tên Khách Hàng");

        txttenkh.setText("jLabel1");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Email:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("SĐT");

        txtemail.setText("jLabel1");

        txtsdt.setText("jLabel1");

        tbhoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
             
            },
            new String [] {
                "Ma San Pham", "Ten San Pham", "Gia", "So Luong", "Thanh Tien"
            }
        ));
        jScrollPane1.setViewportView(tbhoadon);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Tổng Tiền");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Chiết Khấu");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Thành Tiền");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Phuong Thuc Thanh Toan");

        rdtienmat.setText("Thanh Toan Tien Mat");

        rdqr.setText("Thanh toan QR");
        rdqr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdqrActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Nhân Viên Bán Hàng");

        btninhoadon.setText("Lưu Đơn Hàng");
        btninhoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninhoadonActionPerformed(evt);
            }
        });


        
        btnhuy.setText("Hủy");

        lbtongtien.setText("jLabel1");

        lbthanhtien.setText("jLabel1");

        lbchietkhau.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txttenkh, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbtongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(59, 59, 59))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lbchietkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lbthanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(37, 37, 37)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(rdtienmat, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                                        .addComponent(rdqr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGap(30, 30, 30))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btninhoadon)
                            .addGap(26, 26, 26)
                            .addComponent(btnhuy)
                            .addGap(74, 74, 74)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txttenkh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtemail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtsdt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(lbtongtien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rdtienmat)
                    .addComponent(lbchietkhau))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(rdqr)
                    .addComponent(lbthanhtien))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(btninhoadon)
                    .addComponent(btnhuy))
                .addGap(18, 18, 18)
                .addComponent(lbnhanvien)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdqrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdqrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdqrActionPerformed

    private void btninhoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninhoadonActionPerformed
        // TODO add your handling code here:
        // Khởi tạo controller và xử lý khi thanh toán thành công
    InvoiceController invoiceController = new InvoiceController(this);  // Truyền đối tượng Payments (hiện tại)
    invoiceController.onPaymentSuccess();  // Gọi phương thức xử lý thanh toán thành công
    }//GEN-LAST:event_btninhoadonActionPerformed

    

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
            java.util.logging.Logger.getLogger(Payments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Payments dialog = new Payments(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
            
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnhuy;
    private javax.swing.JButton btninhoadon;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbchietkhau;
    private javax.swing.JLabel lbnhanvien;
    private javax.swing.JLabel lbthanhtien;
    private javax.swing.JLabel lbtongtien;
    private javax.swing.JRadioButton rdqr;
    private javax.swing.JRadioButton rdtienmat;
    private javax.swing.JTable tbhoadon;
    private javax.swing.JLabel txtemail;
    private javax.swing.JLabel txtsdt;
    private javax.swing.JLabel txttenkh;
    // End of variables declaration//GEN-END:variables
}
