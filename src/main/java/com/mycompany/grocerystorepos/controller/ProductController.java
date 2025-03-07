package com.mycompany.grocerystorepos.controller;

import com.mycompany.grocerystorepos.dao.ProductDAO;
import com.mycompany.grocerystorepos.model.Product;
import com.mycompany.grocerystorepos.gui.ProductView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductController {
    private ProductView view;
    private ProductDAO model;

    public ProductController(ProductView view, ProductDAO model) {
        this.view = view;
        this.model = model;

        // Nút thêm sản phẩm
        view.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        // Nút cập nhật sản phẩm
        view.getBtnUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });

        // Nút xóa sản phẩm
        view.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        // Nút lưu sản phẩm
        view.getBtnSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProducts();
            }
        });

        loadProducts();
    }

    private void loadProducts() {
        view.getTableModel().setRowCount(0);
        List<Product> products = model.getAllProducts();
        for (Product p : products) {
            view.getTableModel().addRow(new Object[]{
                    p.getProductID(), p.getProductName(), p.getPrice(), p.getQuantity(), p.getUnit(), p.getSupplier(), p.getMinStock()
            });
        }
    }

    private void addProduct() {
        String name = view.getProductName();
//        double price = Double.parseDouble(JOptionPane.showInputDialog("Nhập giá:"));
//        int quantity = Integer.parseInt(JOptionPane.showInputDialog("Nhập số lượng:"));
        double price = Double.parseDouble(view.getPrice());
        int quantity = Integer.parseInt(view.getQuantity());
        String unit = view.getUnit();
        String supplier = view.getSupplier();
        int minStock = 5;

        Product newProduct = new Product(0, name, price, quantity, unit, supplier, minStock);
        model.addProduct(newProduct);
        JOptionPane.showMessageDialog(view, "Thêm sản phẩm thành công!");
        loadProducts();
    }
  
    
    private void updateProduct() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Chọn sản phẩm cần cập nhật!");
            return;
        }

        int productID = (int) view.getTableModel().getValueAt(selectedRow, 0);
        String name = JOptionPane.showInputDialog("Tên mới:", view.getTableModel().getValueAt(selectedRow, 1));
        double price = Double.parseDouble(JOptionPane.showInputDialog("Giá mới:", view.getTableModel().getValueAt(selectedRow, 2)));
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("Số lượng mới:", view.getTableModel().getValueAt(selectedRow, 3)));
        String unit = JOptionPane.showInputDialog("Đơn vị mới:", view.getTableModel().getValueAt(selectedRow, 4));
        String supplier = JOptionPane.showInputDialog("Nhà cung cấp mới:", view.getTableModel().getValueAt(selectedRow, 5));

        Product updatedProduct = new Product(productID, name, price, quantity, unit, supplier, 5);
        model.updateProduct(updatedProduct);
        JOptionPane.showMessageDialog(view, "Cập nhật sản phẩm thành công!");
        loadProducts();
    }

    private void deleteProduct() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Chọn sản phẩm cần xóa!");
            return;
        }

        int productID = (int) view.getTableModel().getValueAt(selectedRow, 0);
        model.deleteProduct(productID);
        JOptionPane.showMessageDialog(view, "Xóa sản phẩm thành công!");
        loadProducts();
    }
}
