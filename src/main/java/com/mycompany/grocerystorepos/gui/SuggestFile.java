package com.mycompany.grocerystorepos.gui;

import com.mycompany.grocerystorepos.dao.ProductDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class SuggestFile extends javax.swing.JFrame {

    private javax.swing.JTextField searchFile;
    private javax.swing.JButton btnthemgiohang;
    private javax.swing.JTable cartTable;
    private javax.swing.JScrollPane jScrollPane1;
    
    private JPopupMenu suggestionMenu;
    private ProductDAO productDAO;
    private DefaultTableModel cartTableModel;

    public SuggestFile() {
        initComponents();
        productDAO = new ProductDAO();
        setupSearchFeature();
    }

    private void setupSearchFeature() {
        suggestionMenu = new JPopupMenu();

        searchFile.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                showSuggestions();
            }
        });

        btnthemgiohang.addActionListener(e -> addToCart());
    }

    private void showSuggestions() {
        suggestionMenu.removeAll();
        String text = searchFile.getText().trim().toLowerCase();

        if (text.isEmpty()) {
            suggestionMenu.setVisible(false);
            return;
        }

        List<String> productList = productDAO.searchProducts(text);

        for (String product : productList) {
            JMenuItem item = new JMenuItem(product);
            item.addActionListener(e -> {
                searchFile.setText(product);
                suggestionMenu.setVisible(false);
            });
            suggestionMenu.add(item);
        }

        if (suggestionMenu.getComponentCount() > 0) {
            suggestionMenu.show(searchFile, 0, searchFile.getHeight());
        }
    }

    private void addToCart() {
        String productName = searchFile.getText().trim();
        if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Thêm sản phẩm vào bảng giỏ hàng
        cartTableModel.addRow(new Object[]{productName, "1", "Giá tiền"}); // Giá tiền có thể lấy từ database

        searchFile.setText(""); // Xóa ô tìm kiếm sau khi thêm vào giỏ hàng
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        searchFile = new javax.swing.JTextField();
        btnthemgiohang = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        cartTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        searchFile.setText("Tìm kiếm sản phẩm...");

        btnthemgiohang.setText("Thêm vào giỏ hàng");

        // Bảng giỏ hàng
        cartTableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Tên sản phẩm", "Số lượng", "Giá tiền","chức năng"}
        );
        cartTable.setModel(cartTableModel);
        jScrollPane1.setViewportView(cartTable);

        // Layout giao diện
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(searchFile, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnthemgiohang)
                                .addContainerGap(50, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(searchFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnthemgiohang))
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new SuggestFile().setVisible(true));
    }
}
