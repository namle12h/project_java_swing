package com.mycompany.grocerystorepos.model;

import java.util.Date;

public class Inventory {

    private int inventoryId;
    private int productId;
    private int quantity;
    private Date lastUpdated;

    // Constructor không đối số
    public Inventory() {
    }

    // Constructor cho insert mới (InventoryID tự động, LastUpdated tự động)
    public Inventory(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Constructor đầy đủ
    public Inventory(int inventoryId, int productId, int quantity, Date lastUpdated) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
