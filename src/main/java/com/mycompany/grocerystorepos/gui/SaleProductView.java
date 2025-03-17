/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.grocerystorepos.gui;

import com.mycompany.grocerystorepos.dao.ProductDAO;
import com.mycompany.grocerystorepos.model.Product;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class SaleProductView extends javax.swing.JFrame {

//    private JTextField txtSearchProduct;
    private JPopupMenu suggestionMenu;
    private List<String> productList;
    private ProductDAO productDAO;
    private DefaultTableModel cartTableModel;
   private javax.swing.JTable cartTable;

    /**
     * Creates new form SaleProductView
     */
    public SaleProductView() {

        initComponents();
        productDAO = new ProductDAO();
        setupSearchFeature();
    }

    // 🔹 Kết nối Database để lấy danh sách sản phẩm từ bảng SaleProduct
//    private void loadProductData() {
//        productList = new ArrayList<>();
//        String query = "SELECT ProductName FROM SaleProduct"; 
//
//        try (Connection conn = connectDB.connect();  
//             Statement stmt = conn.createStatement();  
//             ResultSet rs = stmt.executeQuery(query)) {  
//
//            while (rs.next()) {
//                productList.add(rs.getString("ProductName")); 
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu sản phẩm!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
    private void setupSearchFeature() {
        suggestionMenu = new JPopupMenu();

        txtsearchproduct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                showSuggestions();
            }
        });
        btnthemgiohang.addActionListener(e-> addToCart());
        
    }

    private void showSuggestions() {
        suggestionMenu.removeAll();
        String text = txtsearchproduct.getText().trim().toLowerCase();

        if (text.isEmpty()) {
            suggestionMenu.setVisible(false);
            return;
        }

        // 🔹 Lấy danh sách sản phẩm từ database dựa theo từ khóa tìm kiếm
        List<String> productList = productDAO.searchProducts(text);

        for (String product : productList) {
            JMenuItem item = new JMenuItem(product);
            item.addActionListener(e -> {
                txtsearchproduct.setText(product); // Chọn sản phẩm sẽ nhập vào ô tìm kiếm
                suggestionMenu.setVisible(false);
            });
            suggestionMenu.add(item);
        }

        if (suggestionMenu.getComponentCount() > 0) {
            suggestionMenu.show(txtsearchproduct, 0, txtsearchproduct.getHeight());
        }
    }

//  private void addToCart() {
//     String productName = txtsearchproduct.getText().trim();

//     if (productName.isEmpty()) {
//         JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//         return;
//     }

//     Product product = productDAO.getProductByName(productName);

//     if (product == null) {
//         JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//         return;
//     }

//     int quantityToAdd = (int) amount.getValue(); // Lấy số lượng từ JSpinner

//     if (quantityToAdd <= 0) {
//         JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//         return;
//     }

//     DefaultTableModel model = (DefaultTableModel) tblsanpham.getModel();
//     boolean found = false;

//     for (int i = 0; i < model.getRowCount(); i++) {
//         Object productNameInTable = model.getValueAt(i, 1);
//         Object quantityInTable = model.getValueAt(i, 4);

//         // Kiểm tra nếu sản phẩm đã có trong giỏ hàng
//         if (productNameInTable != null && productNameInTable.toString().equals(product.getProductName())) {
//             int currentQuantity = (quantityInTable != null && !quantityInTable.toString().isEmpty()) 
//                                   ? Integer.parseInt(quantityInTable.toString()) 
//                                   : 0;
//             model.setValueAt(currentQuantity + quantityToAdd, i, 4); // Cộng thêm số lượng mới
//             found = true;
//             break;
//         }
//     }

//     // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
//     if (!found) {
//         model.addRow(new Object[]{
//             product.getProductID(),
//             product.getProductName(),
//             "Hình ảnh",
//             product.getPrice(),
//             quantityToAdd, // Sử dụng số lượng từ JSpinner
//             "Xóa"
//         });
//     }

//     txtsearchproduct.setText("");
//     amount.setValue(1); // Reset JSpinner về 1 sau khi thêm
//     updateTotalPrice();
// }


private void addToCart() {
    String productName = txtsearchproduct.getText().trim();
    int quantity = (int) amount.getValue(); // Lấy số lượng từ JSpinner

    if (productName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Product product = productDAO.getProductByName(productName);

    if (product == null) {
        JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    DefaultTableModel model = (DefaultTableModel) tblsanpham.getModel();
    boolean found = false;

    for (int i = 0; i < model.getRowCount(); i++) {
        Object productNameInTable = model.getValueAt(i, 1);

        if (productNameInTable != null && productNameInTable.toString().equals(product.getProductName())) {
            int currentQuantity = Integer.parseInt(model.getValueAt(i, 4).toString());
            int newQuantity = currentQuantity + quantity;
            model.setValueAt(newQuantity, i, 4);

            // Cập nhật thành tiền (giá * số lượng)
            double totalPrice = product.getPrice() * newQuantity;
            model.setValueAt(totalPrice, i, 5);
            
            found = true;
            break;
        }
    }

    if (!found) {
        double totalPrice = product.getPrice() * quantity; // Thành tiền ban đầu
        model.addRow(new Object[]{
            product.getProductID(),
            product.getProductName(),
            "Hình ảnh",
            product.getPrice(),
            quantity,
            totalPrice,  // Cột "Thành tiền"
            "Xóa"
        });
    }

    updateTotalPrice();  // Cập nhật tổng tiền giỏ hàng
    txtsearchproduct.setText("");
    amount.setValue(0);
}


// tong tien
public void updateTotalPrice() {
    DefaultTableModel model = (DefaultTableModel) tblsanpham.getModel();
    double total = 0;

    for (int i = 0; i < model.getRowCount(); i++) {
        Object priceObj = model.getValueAt(i, 3);
        Object quantityObj = model.getValueAt(i, 4);

        if (priceObj != null && quantityObj != null) {
            double price = Double.parseDouble(priceObj.toString());
            int quantity = Integer.parseInt(quantityObj.toString());
            total += price * quantity;
        }
    }

    lbtongtien.setText(String.format(" %.0f", total));
}


private void removeFromCart(int rowIndex) {
    DefaultTableModel model = (DefaultTableModel) tblsanpham.getModel();
    model.removeRow(rowIndex);
    updateTotalPrice(); // Cập nhật tổng tiền sau khi xóa
}


    // Bảng giỏ hàng
//    private void addToCart() {
//     String productName = txtsearchproduct.getText().trim();

//     if (productName.isEmpty()) {
//         JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//         return;
//     }

//     Product product = productDAO.getProductByName(productName);

//     if (product == null) {
//         JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//         return;
//     }

//     DefaultTableModel model = (DefaultTableModel) tblsanpham.getModel();
//     boolean found = false;

//     for (int i = 0; i < model.getRowCount(); i++) {
//         Object productNameInTable = model.getValueAt(i, 1);

//         // Kiểm tra nếu productNameInTable là null, bỏ qua so sánh
//         if (productNameInTable != null && productNameInTable.toString().equals(product.getProductName())) {
//             int currentQuantity = Integer.parseInt(model.getValueAt(i, 4).toString());
//             model.setValueAt(currentQuantity + 1, i, 4);
//             found = true;
//             break;
//         }
//     }

//     // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
//     if (!found) {
//         model.addRow(new Object[]{
//             product.getProductID(),
//             product.getProductName(),
//             "Hình ảnh",
//             product.getPrice(),
//             1,
//             "Xóa"
//         });
//     }

//     txtsearchproduct.setText("");
// }


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
        txtsearchproduct = new javax.swing.JTextField();
        amount = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbtongtien = new javax.swing.JLabel();
        lbchietkhau = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbthanhtien = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnhuyphieu = new javax.swing.JButton();
        btnthanhtoan = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        txtkhachhang = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        lbnhanvien = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnthemgiohang = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsanpham = new javax.swing.JTable();
        cboxchietkhau = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("PRODUCT SALE");

        jLabel2.setText("Tìm sản phẩm");

        txtsearchproduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsearchproductActionPerformed(evt);
            }
        });

        jLabel3.setText("Số Lượng");

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));
        jPanel2.setForeground(new java.awt.Color(255, 51, 0));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tổng Cộng");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Chiết khấu hóa đơn");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("VNĐ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("VNĐ");

        lbtongtien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbtongtien.setForeground(new java.awt.Color(255, 255, 255));

        lbchietkhau.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbchietkhau.setForeground(new java.awt.Color(255, 255, 255));
        lbchietkhau.setText("1000000");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(lbchietkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbtongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(lbtongtien))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(lbchietkhau))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Thành Tiền");

        lbthanhtien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbthanhtien.setForeground(new java.awt.Color(255, 255, 255));
        lbthanhtien.setText("1000000");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("VNĐ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(lbthanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbthanhtien)
                    .addComponent(jLabel13))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        btnhuyphieu.setBackground(new java.awt.Color(204, 204, 255));
        btnhuyphieu.setText("Hủy Phiếu");
        btnhuyphieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhuyphieuActionPerformed(evt);
            }
        });

        btnthanhtoan.setBackground(new java.awt.Color(204, 204, 255));
        btnthanhtoan.setText("Thanh Toán");
        btnthanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthanhtoanActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        jLabel9.setText("Khách Hàng");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton5.setText("Tìm");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        txtkhachhang.setText("Nhập sdt khách hàng");
        txtkhachhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkhachhangActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));

        jLabel10.setText("EMAIL");

        jLabel11.setText("Tên Khách hàng");

        jLabel14.setText("SĐT");

        jLabel15.setText("Địa Chỉ");

        jLabel16.setText("Giới tính");

        jLabel17.setText("Điểm");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(224, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addContainerGap(75, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addComponent(jLabel11)
                    .addContainerGap(176, Short.MAX_VALUE)))
        );

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton6.setText("Tìm");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        lbnhanvien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbnhanvien.setText("Thu Ngân 1");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Nhân Viên");

        btnthemgiohang.setText("thêm giỏ hàng");

        tblsanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
              
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Hình ảnh", "Giá tiền", "Số lượng", "Thành Tiền", "Chức Năng"
            }
        ));

        // Gán Renderer và Editor cho cột "Chức Năng"
        tblsanpham.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        // tblsanpham.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), tblsanpham));

tblsanpham.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), tblsanpham, this));

        jScrollPane1.setViewportView(tblsanpham);
        tblsanpham.getAccessibleContext().setAccessibleParent(tblsanpham);

        cboxchietkhau.setBackground(new java.awt.Color(204, 204, 255));
        cboxchietkhau.setForeground(new java.awt.Color(51, 51, 255));
        cboxchietkhau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboxchietkhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxchietkhauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(txtsearchproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnthemgiohang)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbnhanvien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(202, 202, 202))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 863, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 103, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(btnthanhtoan)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                    .addComponent(cboxchietkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(38, 38, 38)
                                    .addComponent(btnhuyphieu))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtkhachhang))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtsearchproduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(btnthemgiohang))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbnhanvien)))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnthanhtoan, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                            .addComponent(cboxchietkhau)
                            .addComponent(btnhuyphieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(25, 25, 25)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(256, 256, 256))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtsearchproductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsearchproductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchproductActionPerformed

    private void btnhuyphieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhuyphieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnhuyphieuActionPerformed

    private void btnthanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthanhtoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthanhtoanActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtkhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkhachhangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkhachhangActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnthemgiohangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemgiohangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthemgiohangActionPerformed

    private void cboxchietkhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxchietkhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboxchietkhauActionPerformed

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
            java.util.logging.Logger.getLogger(SaleProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SaleProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SaleProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SaleProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SaleProductView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner amount;
    private javax.swing.JButton btnhuyphieu;
    private javax.swing.JButton btnthanhtoan;
    private javax.swing.JButton btnthemgiohang;
    private javax.swing.JComboBox<String> cboxchietkhau;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbchietkhau;
    private javax.swing.JLabel lbnhanvien;
    private javax.swing.JLabel lbthanhtien;
    private javax.swing.JLabel lbtongtien;
    private javax.swing.JTable tblsanpham;
    private javax.swing.JTextField txtkhachhang;
    private javax.swing.JTextField txtsearchproduct;
    // End of variables declaration//GEN-END:variables
}
