
package com.mycompany.grocerystorepos.model;


public class Product {
    private int productID;
    private String productName;
    private double price;
    private int quantity;
    private String unit;
    private String supplier;
    private int minStock;

    // Constructor
    public Product(int productID, String productName, double price, int quantity, String unit, String supplier, int minStock) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.supplier = supplier;
        this.minStock = minStock;
    }

    // Getters và Setters
    public int getProductID() { return productID; }
    public void setProductID(int productID) { this.productID = productID; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public int getMinStock() { return minStock; }
    public void setMinStock(int minStock) { this.minStock = minStock; }
}
