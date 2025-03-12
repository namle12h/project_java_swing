/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.controller;

/**
 *
 * @author LENOVO
 */
import com.mycompany.grocerystorepos.dao.InventoryDAO;
import com.mycompany.grocerystorepos.model.Inventory;
import com.mycompany.grocerystorepos.gui.InventoryView;

import javax.swing.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class InventoryController {

    private InventoryView view;
    private InventoryDAO model;

    public InventoryController(InventoryView view, InventoryDAO model) {
        this.view = view;
        this.model = model;

        // Sử dụng các getter phù hợp để lấy JButton từ view
        view.getBtnAdd().addActionListener(e -> addInventory());
        view.getBtnUpdate().addActionListener(e -> updateInventory());

        setUpTableSelectionListener();
        loadInventories();
    }

    private void loadInventories() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel(); // Lấy model từ bảng
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        List<Inventory> inventories = model.getAllInventories();
        for (Inventory inv : inventories) {
            tableModel.addRow(new Object[]{
                inv.getProductCode(),
                inv.getProductName(),
                inv.getPrice(),
                inv.getQuantity(),
                inv.getUnit()
            });
        }
    }
    String productCode = view.getProductID().getText();
    String productName = view.getProductName().getText();
    double price = Double.parseDouble(view.getProductPrice().getText());
    int quantity = Integer.parseInt(view.getQuantityInStock().getText());
    String unit = view.getProductUnit().getText();

    Inventory inventory = new Inventory(productCode, productName, price, quantity, unit);

    private void addInventory() {

        try {

            if (model.addInventory(inventory)) {
                JOptionPane.showMessageDialog(view, "Thêm sản phẩm vào kho thành công!");
                loadInventories();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm sản phẩm thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập số hợp lệ cho giá và số lượng!");
        }
    }

    private void updateInventory() {
        try {

            if (model.updateInventory(inventory)) {
                JOptionPane.showMessageDialog(view, "Cập nhật kho hàng thành công!");
                loadInventories();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập số hợp lệ cho giá và số lượng!");
        }
    }

    private void setUpTableSelectionListener() {
        JTable table = view.getTable();
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Tránh xử lý sự kiện nhiều lần
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // Kiểm tra xem có hàng nào được chọn không
                    view.getProductID().setText(table.getValueAt(selectedRow, 0).toString());
                    view.getProductName().setText(table.getValueAt(selectedRow, 1).toString());
                    view.getProductPrice().setText(table.getValueAt(selectedRow, 2).toString());
                    view.getQuantityInStock().setText(table.getValueAt(selectedRow, 3).toString());
                    view.getProductUnit().setText(table.getValueAt(selectedRow, 4).toString());
                }
            }
        });
    }
}
