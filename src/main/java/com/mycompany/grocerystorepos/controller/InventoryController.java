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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class InventoryController {

    private InventoryView view;
    private InventoryDAO model;

    public InventoryController(InventoryView view, InventoryDAO model) {
        this.view = view;
        this.model = model;

        view.btnaddActionPerformed().addActionListener(e -> addInventory());
        view.btnupdateActionPerformed().addActionListener(e -> updateInventory());

        setUpTableSelectionListener();
        loadInventories();
    }

    private void loadInventories() {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);

        List<Inventory> inventories = (List<Inventory>) model.getAllInventories();
        for (Inventory inv : inventories) {
            tableModel.addRow(new Object[]{
                inv.getProductCode(), inv.getProductName(),
                inv.getPrice(), inv.getQuantity(), inv.getUnit()
            });
        }
    }

    private void addInventory() {
        try {
            String productCode = view.getProductID();
            String productName = view.getProductName();
            double price = Double.parseDouble(view.getProductPrice());
            int quantity = Integer.parseInt(view.getQuantityInStock());
            String unit = view.getProductUnit();

            Inventory inventory = new Inventory(productCode, productName, price, quantity, unit);

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
            String productCode = view.getProductID();
            String productName = view.getProductName();
            double price = Double.parseDouble(view.getProductPrice());
            int quantity = Integer.parseInt(view.getQuantityInStock());
            String unit = view.getProductUnit();

            Inventory inventory = new Inventory(productCode, productName, price, quantity, unit);

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
        view.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    view.setProductID(view.getTableModel().getValueAt(selectedRow, 0).toString());
                    view.setProductName(view.getTableModel().getValueAt(selectedRow, 1).toString());
                    view.setProductPrice(view.getTableModel().getValueAt(selectedRow, 2).toString());
                    view.setQuantityInStock(view.getTableModel().getValueAt(selectedRow, 3).toString());
                    view.setProductUnit(view.getTableModel().getValueAt(selectedRow, 4).toString());
                }
            }
        });
    }
}
