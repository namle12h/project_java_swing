package com.mycompany.grocerystorepos.controller;

import com.mycompany.grocerystorepos.dao.ProductDAO;
import com.mycompany.grocerystorepos.model.Product;
import com.mycompany.grocerystorepos.gui.ProductViewPanel;
import com.mycompany.grocerystorepos.gui.SaleProductView;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.util.List;

public class ProductController  extends JPanel {

    private ProductViewPanel pview;  // Sửa từ view -> pview
    private ProductDAO model;
    private SaleProductView sview;

//     // Constructor nhận vào ProductViewPanel và ProductDAO
//     public ProductController(ProductViewPanel pview, ProductDAO model) {
//         this.pview = pview;  // Sửa từ view -> pview
//         this.model = model;
//         pview.getBtnAdd().addActionListener(e -> addProduct());
//         pview.getBtnUpdate().addActionListener(e -> updateProduct());
//         pview.getBtnDelete().addActionListener(e -> deleteProduct());
// //        pview.getBtnReset().addActionListener(e -> resetFields());

//         setUpTableSelectionListener();
//         loadProducts();

//         List<String> categories = model.getAllCategories();
//         pview.updateCategoryComboBox(categories);
        
//         // Gọi phương thức để lấy dữ liệu sản phẩm từ DAO và cập nhật vào view
// //        loadProducts();
//     }


public ProductController(ProductViewPanel pview, ProductDAO model) {
        this.pview = pview;
        this.model = model;

        // Thêm các sự kiện cho nút
        pview.getBtnAdd().addActionListener(e -> addProduct());
        pview.getBtnUpdate().addActionListener(e -> updateProduct());
        pview.getBtnDelete().addActionListener(e -> deleteProduct());
        pview.getBtnSave().addActionListener(e -> resetFields());

        // Tải danh mục và các sản phẩm
        setUpTableSelectionListener();
//        loadCategories();
        List<String> categories = model.getAllCategories();
        pview.updateCategoryComboBox(categories);
        loadProducts();
    }
    
    public ProductController(SaleProductView sview, ProductDAO model) {
        this.sview = sview;
        this.model = model;
    }

    public List<String> getSuggestions(String keyword) {
        return model.searchProducts(keyword);
    }

    private void loadProducts() {
        pview.getTableModel().setRowCount(0);
        List<Product> products = model.getAllProducts();

        for (Product p : products) {
            String warning = (p.getQuantity() < p.getMinStock()) ? "⚠️ Thấp hơn mức tối thiểu!" : "✅ Đủ hàng";
            pview.getTableModel().addRow(new Object[]{
                p.getProductID(), p.getProductName(), p.getPrice(), p.getQuantity(), p.getUnit(), p.getSupplier(), p.getCategory(), warning
            });
        }
    }

  private void addProduct() {
    try {
        // Lấy dữ liệu từ ProductViewPanel
        String name = pview.getProductName();
        String priceStr = pview.getPrice();
        String quantityStr = pview.getQuantity();
        
        // Kiểm tra dữ liệu nhập vào
        if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(pview, "Vui lòng điền đầy đủ thông tin sản phẩm!");
            return; // Dừng lại nếu có trường hợp trống
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);
        
        // Kiểm tra giá trị hợp lệ của số lượng và giá
        if (price <= 0) {
            JOptionPane.showMessageDialog(pview, "Giá phải lớn hơn 0!");
            return;
        }
        
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(pview, "Số lượng phải lớn hơn 0!");
            return;
        }

        String unit = pview.getUnit();
        String supplier = pview.getSupplier();
        String category = pview.getCategory();
        int minStock = 5;

        // Tạo sản phẩm mới và thêm vào cơ sở dữ liệu
        Product newProduct = new Product(0, name, price, quantity, unit, supplier, category, minStock);
        model.addProduct(newProduct);  // Thêm sản phẩm vào cơ sở dữ liệu
        
        JOptionPane.showMessageDialog(pview, "Thêm sản phẩm thành công!");
        loadProducts();  // Tải lại danh sách sản phẩm
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(pview, "Vui lòng nhập đúng kiểu dữ liệu (giá và số lượng phải là số)");
    }
}


   private void updateProduct() {
    try {
        // Lấy chỉ số dòng được chọn trong bảng
        int selectedRow = pview.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(pview, "Chọn sản phẩm cần cập nhật!");
            return;
        }

        // Lấy dữ liệu từ các trường
        int productID = Integer.parseInt(pview.getProductID());
        String name = pview.getProductName();
        String priceStr = pview.getPrice();
        String quantityStr = pview.getQuantity();

        // Kiểm tra dữ liệu nhập vào
        if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(pview, "Vui lòng điền đầy đủ thông tin sản phẩm!");
            return;
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);

        // Kiểm tra giá trị hợp lệ của số lượng và giá
        if (price <= 0) {
            JOptionPane.showMessageDialog(pview, "Giá phải lớn hơn 0!");
            return;
        }

        if (quantity <= 0) {
            JOptionPane.showMessageDialog(pview, "Số lượng phải lớn hơn 0!");
            return;
        }

        String unit = pview.getUnit();
        String supplier = pview.getSupplier();
        String category = pview.getCategory();
        int minStock = 5;

        // Tạo đối tượng sản phẩm cập nhật
        Product updatedProduct = new Product(productID, name, price, quantity, unit, supplier, category, minStock);
        
        // Cập nhật sản phẩm vào cơ sở dữ liệu
        model.updateProduct(updatedProduct);

        // Kiểm tra nếu số lượng nhỏ hơn mức tối thiểu
        if (quantity < updatedProduct.getMinStock()) {
            JOptionPane.showMessageDialog(pview, "⚠️ Cảnh báo: Số lượng của " + name + " thấp hơn mức tối thiểu (" + updatedProduct.getMinStock() + ")!");
        }

        JOptionPane.showMessageDialog(pview, "Cập nhật sản phẩm thành công!");
        loadProducts();  // Tải lại danh sách sản phẩm
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(pview, "Vui lòng nhập đúng kiểu dữ liệu (giá và số lượng phải là số)");
    }
}


    private void deleteProduct() {
        int selectedRow = pview.getTable().getSelectedRow();  // Sửa từ view.getTable() -> pview.getTable()
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(pview, "Chọn sản phẩm cần xóa!");  // Sửa từ view -> pview
            return;
        }

        int productID = (int) pview.getTable().getValueAt(selectedRow, 0);  // Sửa từ view -> pview

        if (productID <= 0) {
            JOptionPane.showMessageDialog(pview, "Mã sản phẩm không hợp lệ!");  // Sửa từ view -> pview
            return;
        }

        model.deleteProduct(productID);
        JOptionPane.showMessageDialog(pview, "Xóa sản phẩm thành công!");  // Sửa từ view -> pview
        loadProducts();
    }

    public List<Product> getProductsByCategory(String category) {
        return ProductDAO.getProductsByCategory(category);
    }

    public void updateProductTable(String category) {
        pview.getTableModel().setRowCount(0);
        List<Product> products = model.getProductsByCategory(category);

        for (Product product : products) {
            pview.getTableModel().addRow(new Object[]{
                product.getProductID(),
                product.getProductName(),
                product.getPrice(),
                product.getQuantity(),
                product.getUnit(),
                product.getSupplier(),
                product.getCategory(),
                product.getMinStock()
            });
        }
    }

    private void resetFields() {
        pview.setProductID("");  // Sửa từ view -> pview
        pview.setProductName("");  // Sửa từ view -> pview
        pview.setPrice("");  // Sửa từ view -> pview
        pview.setQuantity("");  // Sửa từ view -> pview
        pview.setUnit("");  // Sửa từ view -> pview
        pview.setSupplier("");  // Sửa từ view -> pview
    }

    private void setUpTableSelectionListener() {
        pview.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {  // Sửa từ view -> pview
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = pview.getTable().getSelectedRow();  // Sửa từ view -> pview
                if (selectedRow != -1) {
                    pview.setProductID(String.valueOf(pview.getTableModel().getValueAt(selectedRow, 0)));  // Sửa từ view -> pview
                    pview.setProductName((String) pview.getTableModel().getValueAt(selectedRow, 1));  // Sửa từ view -> pview
                    pview.setPrice(String.valueOf(pview.getTableModel().getValueAt(selectedRow, 2)));  // Sửa từ view -> pview
                    pview.setQuantity(String.valueOf(pview.getTableModel().getValueAt(selectedRow, 3)));  // Sửa từ view -> pview
                    pview.setUnit((String) pview.getTableModel().getValueAt(selectedRow, 4));  // Sửa từ view -> pview
                    pview.setSupplier((String) pview.getTableModel().getValueAt(selectedRow, 5));  // Sửa từ view -> pview
                    pview.setCategory((String) pview.getTableModel().getValueAt(selectedRow, 6));  // Sửa từ view -> pview
                    pview.setMinStock((String) pview.getTableModel().getValueAt(selectedRow, 7));  // Sửa từ view -> pview
                }
            }
        });
    }
}
