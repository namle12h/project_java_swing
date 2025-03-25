/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.grocerystorepos.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class SalesPage extends JFrame {
    private JPanel panelMain, panelCustomerInfo, panelProductInfo, panelInvoice;
    private JTextField txtCustomerID, txtCustomerName, txtProductID, txtProductName, txtQuantity, txtPrice;
    private JComboBox<String> cboxPaymentMethod, cboxProductCategory;
    private JButton btnAddProduct, btnPay, btnNewInvoice, btnPrintInvoice;
    private JTable tableProductList;
    private DefaultTableModel productTableModel;

    public SalesPage() {
        setTitle("Hệ Thống Bán Hàng");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo các panel chính
        panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());

        // Tạo panel cho thông tin khách hàng
        panelCustomerInfo = new JPanel();
        panelCustomerInfo.setLayout(new GridLayout(3, 2));
        panelCustomerInfo.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtCustomerID = new JTextField();
        txtCustomerName = new JTextField();

        panelCustomerInfo.add(new JLabel("Số điện thoại:"));
        panelCustomerInfo.add(txtCustomerID);
        panelCustomerInfo.add(new JLabel("Tên khách hàng:"));
        panelCustomerInfo.add(txtCustomerName);

        // Tạo panel cho thông tin sản phẩm
        panelProductInfo = new JPanel();
        panelProductInfo.setLayout(new GridLayout(4, 2));
        panelProductInfo.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));

        txtProductID = new JTextField();
        txtProductName = new JTextField();
        txtQuantity = new JTextField();
        txtPrice = new JTextField();

        String[] categories = {"Item 1", "Item 2", "Item 3"};
        cboxProductCategory = new JComboBox<>(categories);

        panelProductInfo.add(new JLabel("Mã sản phẩm:"));
        panelProductInfo.add(txtProductID);
        panelProductInfo.add(new JLabel("Tên sản phẩm:"));
        panelProductInfo.add(txtProductName);
        panelProductInfo.add(new JLabel("Loại hàng:"));
        panelProductInfo.add(cboxProductCategory);
        panelProductInfo.add(new JLabel("Số lượng:"));
        panelProductInfo.add(txtQuantity);

        // Tạo bảng sản phẩm
        productTableModel = new DefaultTableModel();
        productTableModel.addColumn("Mã SP");
        productTableModel.addColumn("Tên SP");
        productTableModel.addColumn("Giá");
        productTableModel.addColumn("Số Lượng");

        tableProductList = new JTable(productTableModel);
        JScrollPane scrollPane = new JScrollPane(tableProductList);
        
        // Tạo panel hóa đơn
        panelInvoice = new JPanel();
        panelInvoice.setLayout(new GridLayout(3, 2));
        panelInvoice.setBorder(BorderFactory.createTitledBorder("Hóa đơn"));

        cboxPaymentMethod = new JComboBox<>(new String[] {"Tiền mặt", "Thẻ ngân hàng", "Ví điện tử"});
        btnAddProduct = new JButton("Thêm vào hóa đơn");
        btnPay = new JButton("Thanh toán");
        btnNewInvoice = new JButton("Tạo mới HĐ");
        btnPrintInvoice = new JButton("In hóa đơn");

        panelInvoice.add(new JLabel("Phương thức thanh toán:"));
        panelInvoice.add(cboxPaymentMethod);
        panelInvoice.add(btnAddProduct);
        panelInvoice.add(btnNewInvoice);
        panelInvoice.add(btnPay);
        panelInvoice.add(btnPrintInvoice);

        // Thêm các panel vào panel chính
        panelMain.add(panelCustomerInfo, BorderLayout.NORTH);
        panelMain.add(panelProductInfo, BorderLayout.CENTER);
        panelMain.add(scrollPane, BorderLayout.SOUTH);
        panelMain.add(panelInvoice, BorderLayout.EAST);

        add(panelMain);

        setVisible(true);

        // Cài đặt sự kiện cho các nút
        btnAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductToInvoice();
            }
        });
        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });
        btnNewInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewInvoice();
            }
        });
        btnPrintInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printInvoice();
            }
        });
    }

    private void addProductToInvoice() {
        String productID = txtProductID.getText();
        String productName = txtProductName.getText();
        String quantity = txtQuantity.getText();
        String price = txtPrice.getText();
        productTableModel.addRow(new Object[] {productID, productName, price, quantity});
    }

    private void processPayment() {
        JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
    }

    private void createNewInvoice() {
        txtCustomerID.setText("");
        txtCustomerName.setText("");
        txtProductID.setText("");
        txtProductName.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
        productTableModel.setRowCount(0);
    }

    private void printInvoice() {
        JOptionPane.showMessageDialog(this, "In hóa đơn thành công!");
    }

    public static void main(String[] args) {
        new SalesPage();
    }
}
