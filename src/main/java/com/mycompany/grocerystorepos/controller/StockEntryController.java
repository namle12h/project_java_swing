package com.mycompany.grocerystorepos.controller;

import com.mycompany.grocerystorepos.dao.StockEntryDAO;
import com.mycompany.grocerystorepos.gui.StockEntryView;
import com.mycompany.grocerystorepos.model.StockEntry;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

public final class StockEntryController {

    private StockEntryView view;
    private final StockEntryDAO model;

    public void loadStockEntries() {
        List<StockEntry> stockEntries = model.getAllStockEntries(); // Lấy dữ liệu từ DB
        view.loadStockEntries(stockEntries); // Cập nhật bảng
    }

    public StockEntryController(StockEntryView view) {
        if (view == null) {
            throw new IllegalArgumentException("StockEntryView không được null!");
        }
        this.view = view;
        this.model = new StockEntryDAO();

        // Gán sự kiện cho các nút
        setUpTableSelectionListener();
        loadStockEntries();
    }

// Thêm hàng nhập mới
    public void addStockEntry(int productId, int supplierId, int quantity) throws SQLException {
        // Kiểm tra
        if (!model.isProductExists(productId)) {
            throw new SQLException("Sản phẩm không tồn tại!");
        }
        if (!model.isSupplierExists(supplierId)) {
            throw new SQLException("Nhà cung cấp không tồn tại!");
        }
        if (quantity <= 0) {
            throw new SQLException("Số lượng nhập phải lớn hơn 0!");
        }

        // Tạo StockEntry (ID tự tăng)
        StockEntry entry = new StockEntry(productId, supplierId, quantity, new Date());
        model.addStockEntry(entry);
    }

    // Cập nhật hàng nhập
    public void updateStockEntry(int productId, int supplierId, int quantity) throws SQLException {
        // Kiểm tra dữ liệu hợp lệ
        if (!model.isProductExists(productId)) {
            throw new SQLException("Sản phẩm không tồn tại!");
        }
        if (!model.isSupplierExists(supplierId)) {
            throw new SQLException("Nhà cung cấp không tồn tại!");
        }
        if (quantity <= 0) {
            throw new SQLException("Số lượng nhập phải lớn hơn 0!");
        }

        // Tạo StockEntry mới
        StockEntry entry = new StockEntry();
        entry.setProductId(productId);
        entry.setSupplierId(supplierId);
        entry.setQuantity(quantity);
        entry.setEntryDate(new Timestamp(System.currentTimeMillis())); // ✅ Dùng Timestamp

        // Cập nhật vào DB
        model.updateStockEntry(entry);

        // Load lại bảng sau khi cập nhật
        loadStockEntries();
    }

    // Xóa hàng nhập
    public void deleteStockEntry(int entryId) throws SQLException {
        model.deleteStockEntry(entryId);
    }

    // Lấy danh sách hàng nhập
    public List<StockEntry> getAllStockEntries() {
        return model.getAllStockEntries();
    }

// ✅ Xử lý sự kiện chọn hàng trong bảng Stock Entry
    private void setUpTableSelectionListener() {
        view.getTable().getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    view.setProductID(view.getTable().getValueAt(selectedRow, 0).toString()); // ✅ Sửa lỗi
                    view.setSupplierID(view.getTable().getValueAt(selectedRow, 1).toString()); // ✅ Sửa lỗi
                    view.setQuantity(view.getTable().getValueAt(selectedRow, 2).toString()); // ✅ Đúng
                }
            }
        });
    }
}
