package com.mycompany.grocerystorepos.controller;

import com.mycompany.grocerystorepos.dao.InventoryDAO;
import com.mycompany.grocerystorepos.model.Inventory;
import com.mycompany.grocerystorepos.gui.InventoryView;
import java.util.List;

public class InventoryController {

    public InventoryView view;
    public InventoryDAO dao;

    public InventoryController(InventoryView view) {
        this.view = view;
        this.dao = new InventoryDAO();
    }

    // Lấy danh sách tồn kho từ DAO để cập nhật lên giao diện
    public List<Inventory> getAllInventories() {
        return dao.getAllInventories();
    }

    // Khi nhập hàng: cập nhật số lượng tồn kho
    public boolean addOrUpdateStockEntry(int productId, int quantityToAdd) {
        // Nếu ProductID đã tồn tại thì cộng dồn số lượng, nếu chưa có thì insert mới.
        return dao.updateInventoryQuantity(productId, quantityToAdd);
    }

    // Khi bán hàng: trừ số lượng tồn kho (chưa tích hợp giao diện bán hàng)
    public boolean sellProduct(int productId, int quantitySold) {
        // quantitySold là số lượng bán (sử dụng giá trị dương, bên trong hàm sẽ trừ)
        return dao.updateInventoryQuantity(productId, -quantitySold);
    }

    // Các phương thức khác liên quan đến việc xuất lịch sử nhập xuất hàng có thể được bổ sung sau.
}
