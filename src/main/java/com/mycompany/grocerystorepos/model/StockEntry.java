package com.mycompany.grocerystorepos.model;

import java.util.Date;

public class StockEntry {

    private int stockEntryId;  // Tự động tăng
    private int productId;
    private int supplierId;
    private int quantity;
    private Date entryDate;

    // Constructor mặc định
    public StockEntry() {
    }

    // Constructor đầy đủ (KHÔNG truyền ID, vì ID auto-increment)
    public StockEntry(int productId, int supplierId, int quantity, Date entryDate) {
        this.productId = productId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.entryDate = entryDate;
    }

    // Getter & Setter
    public int getStockEntryId() {
        return stockEntryId;
    }

    public void setStockEntryId(int stockEntryId) {
        this.stockEntryId = stockEntryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
