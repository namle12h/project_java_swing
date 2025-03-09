package com.mycompany.grocerystorepos.controller;

import com.mycompany.grocerystorepos.dao.ProductDAO;
import com.mycompany.grocerystorepos.model.Product;
import com.mycompany.grocerystorepos.gui.ProductView;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.util.List;

public class ProductController {
    private ProductView view;
    private ProductDAO model;

    public ProductController(ProductView view, ProductDAO model) {
        this.view = view;
        this.model = model;

        view.getBtnAdd().addActionListener(e -> addProduct());
        view.getBtnUpdate().addActionListener(e -> updateProduct());
        view.getBtnDelete().addActionListener(e -> deleteProduct());
//        view.getBtnReset().addActionListener(e -> resetFields());

        setUpTableSelectionListener();
        loadProducts();
        
        List<String> categories = model.getAllCategories();
        view.updateCategoryComboBox(categories);
        
//        view.getCategoryComboBox().addActionListener(e -> {
//            String selectedCategory = (String) view.getCategoryComboBox().getSelectedItem();
//            updateProductTable(selectedCategory);
//        });
    }

    private void loadProducts() {
        view.getTableModel().setRowCount(0);
        List<Product> products = model.getAllProducts();
        
        for (Product p : products) {
            String warning = (p.getQuantity() < p.getMinStock()) ? "⚠️ Thấp hơn mức tối thiểu!" : "✅ Đủ hàng";
            view.getTableModel().addRow(new Object[]{
                p.getProductID(), p.getProductName(), p.getPrice(), p.getQuantity(), p.getUnit(), p.getSupplier(), p.getCategory(), warning
            });
        }
    }

    private void addProduct() {
        String name = view.getProductName();
        double price = Double.parseDouble(view.getPrice());
        int quantity = Integer.parseInt(view.getQuantity());
        String unit = view.getUnit();
        String supplier = view.getSupplier();
        String category = view.getCategory();
        int minStock = 5;

        Product newProduct = new Product(0, name, price, quantity, unit, supplier, category, minStock);
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

        int productID = Integer.parseInt(view.getProductID());
        String name = view.getProductName();
        double price = Double.parseDouble(view.getPrice());
        int quantity = Integer.parseInt(view.getQuantity());
        String unit = view.getUnit();
        String supplier = view.getSupplier();
        String category = view.getCategory();

        Product updatedProduct = new Product(productID, name, price, quantity, unit, supplier, category, 5);
        model.updateProduct(updatedProduct);

        if (quantity < updatedProduct.getMinStock()) {
            JOptionPane.showMessageDialog(view, "⚠️ Cảnh báo: Số lượng của " + name + " thấp hơn mức tối thiểu (" + updatedProduct.getMinStock() + ")!");
        }

        JOptionPane.showMessageDialog(view, "Cập nhật sản phẩm thành công!");
        loadProducts();
    }

    private void deleteProduct() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Chọn sản phẩm cần xóa!");
            return;
        }

        int productID = Integer.parseInt(view.getProductID());
        model.deleteProduct(productID);
        JOptionPane.showMessageDialog(view, "Xóa sản phẩm thành công!");
        loadProducts();
    }

 // Phương thức lấy sản phẩm theo loại
    public List<Product> getProductsByCategory(String category) {
        return ProductDAO.getProductsByCategory(category);
    }

    // 🛠️ Thêm phương thức cập nhật bảng sản phẩm
    public void updateProductTable(String category) {
        view.getTableModel().setRowCount(0);
        List<Product> products = model.getProductsByCategory(category);  

        for (Product product : products) {
            view.getTableModel().addRow(new Object[]{
                product.getProductID(),
                product.getProductName(),
                product.getPrice(),
                product.getQuantity(),
                product.getUnit(),
                product.getSupplier(),
                product.getCategory(),
                product.getMinStock()
            });
        }}
    
    private void resetFields() {
        view.setProductID("");
        view.setProductName("");
        view.setPrice("");
        view.setQuantity("");
        view.setUnit("");
        view.setSupplier("");
    }

    private void setUpTableSelectionListener() {
        view.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    view.setProductID(String.valueOf(view.getTableModel().getValueAt(selectedRow, 0)));
                    view.setProductName((String) view.getTableModel().getValueAt(selectedRow, 1));
                    view.setPrice(String.valueOf(view.getTableModel().getValueAt(selectedRow, 2)));
                    view.setQuantity(String.valueOf(view.getTableModel().getValueAt(selectedRow, 3)));
                    view.setUnit((String) view.getTableModel().getValueAt(selectedRow, 4));
                    view.setSupplier((String) view.getTableModel().getValueAt(selectedRow, 5));
                    view.setCategory((String) view.getTableModel().getValueAt(selectedRow, 6));
                     view.setMinStock((String) view.getTableModel().getValueAt(selectedRow, 7));

                    
                }
            }
        });
    }
}
