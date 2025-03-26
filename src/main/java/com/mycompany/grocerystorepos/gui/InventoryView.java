/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.grocerystorepos.gui;

/**
 *
 * @author LENOVO
 */
import javax.swing.*;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.mycompany.grocerystorepos.controller.InventoryController;
import com.mycompany.grocerystorepos.model.Inventory;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author LENOVO
 */
public final class InventoryView extends javax.swing.JPanel {

    private InventoryController controller;

    public InventoryView() {
        initComponents();
        // Khởi tạo controller và load dữ liệu vào bảng khi mở form
        controller = new InventoryController(this);
        loadInventoryData();
    }

    public void loadInventoryData() {
        List<Inventory> list = controller.getAllInventories();
        // Sử dụng DefaultTableModel để cập nhật dữ liệu bảng
        DefaultTableModel model = (DefaultTableModel) tbinventory.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (Inventory inv : list) {
            Object[] row = new Object[]{
                inv.getInventoryId(),
                inv.getProductId(),
                inv.getQuantity(),
                inv.getLastUpdated()
            };
            model.addRow(row);
        }
    }

    public void searchInventoryByProductId() {
        String input = txttimproductid.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập ProductID để tìm kiếm!");
            return;
        }
        try {
            int productId = Integer.parseInt(input);
            // Sử dụng phương thức getInventoryByProductId trong DAO (có thể gọi trực tiếp nếu cần) hoặc thêm phương thức tương ứng trong controller
            Inventory inv = controller.dao.getInventoryByProductId(productId);  // Lưu ý: Có thể tạo thêm 1 phương thức trong controller để ẩn chi tiết DAO
            if (inv != null) {
                // Tìm và highlight dòng có ProductID tương ứng trong JTable
                DefaultTableModel model = (DefaultTableModel) tbinventory.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (productId == Integer.parseInt(model.getValueAt(i, 1).toString())) {
                        tbinventory.setRowSelectionInterval(i, i);
                        tbinventory.scrollRectToVisible(new Rectangle(tbinventory.getCellRect(i, 0, true)));
                        return;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy ProductID: " + productId);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Giá trị ProductID không hợp lệ!");
        }
    }

    // Phương thức cập nhật tồn kho: khi nhập hàng (cộng dồn) hoặc xuất hàng (trừ)
// quantityDelta > 0 cho nhập hàng, quantityDelta < 0 cho bán hàng
    public void updateInventory(int productId, int quantityDelta) {
        boolean result;
        if (quantityDelta > 0) {
            result = controller.addOrUpdateStockEntry(productId, quantityDelta);
        } else {
            result = controller.sellProduct(productId, -quantityDelta); // truyền giá trị dương cho phương thức sellProduct
        }
        if (result) {
            JOptionPane.showMessageDialog(null, "Cập nhật tồn kho thành công!");
            loadInventoryData();
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật tồn kho thất bại!");
        }
    }

    public static void exportToPDF(JTable tbinventory, String filePath) {
        try {
            // Khởi tạo PdfWriter và PdfDocument
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Thêm tiêu đề
            document.add(new Paragraph("Inventory Data")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(" "));

            // Lấy dữ liệu từ JTable
            TableModel tbinventoryModel = tbinventory.getModel();
            int numColumns = tbinventoryModel.getColumnCount();
            Table pdfTable = new Table(numColumns);

            // Thêm header
            for (int i = 0; i < numColumns; i++) {
                pdfTable.addHeaderCell(new Paragraph(tbinventoryModel.getColumnName(i)).setBold());
            }

            // Thêm dữ liệu từ JTable
            for (int row = 0; row < tbinventoryModel.getRowCount(); row++) {
                for (int col = 0; col < numColumns; col++) {
                    Object cellValue = tbinventoryModel.getValueAt(row, col);
                    pdfTable.addCell(new Paragraph(cellValue != null ? cellValue.toString() : ""));
                }
            }

            document.add(pdfTable);
            pdfDoc.close();

            JOptionPane.showMessageDialog(null, "Xuất file PDF thành công: " + filePath);
        } catch (HeadlessException | FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file PDF: " + e.getMessage());
            e.printStackTrace();
        }
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbinventory = new javax.swing.JTable();
        btnExportPDF = new javax.swing.JButton();
        txttimproductid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnok = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(51, 0, 255));

        jLabel1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Quản Lí Kho Hàng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        tbinventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "InventoryID", "ProductID", "Quantity", "LastUpdated"
            }
        ));
        jScrollPane1.setViewportView(tbinventory);

        btnExportPDF.setBackground(new java.awt.Color(204, 0, 51));
        btnExportPDF.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnExportPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnExportPDF.setText("EXPORT FILE");
        btnExportPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportPDFActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tìm productId:");

        btnok.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnok.setText("FIND");
        btnok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnokActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txttimproductid, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnok)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExportPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttimproductid, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnok, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportPDFActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Kiểm tra nếu người dùng chưa nhập phần mở rộng .pdf, tự động thêm
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            // Gọi phương thức exportToPDF với bảng và đường dẫn file
            exportToPDF(tbinventory, filePath);
        }
    }//GEN-LAST:event_btnExportPDFActionPerformed

    private void btnokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnokActionPerformed
        // TODO add your handling code here:
        searchInventoryByProductId();
    }//GEN-LAST:event_btnokActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportPDF;
    private javax.swing.JButton btnok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbinventory;
    private javax.swing.JTextField txttimproductid;
    // End of variables declaration//GEN-END:variables
}
