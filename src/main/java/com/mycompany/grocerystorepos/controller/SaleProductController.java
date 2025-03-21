/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.controller;

import com.mycompany.grocerystorepos.dao.InvoiceDAO;
import com.mycompany.grocerystorepos.dao.InvoiceDetailDAO;
import com.mycompany.grocerystorepos.dao.ProductDAO;
import com.mycompany.grocerystorepos.gui.SaleProductView;
import com.mycompany.grocerystorepos.model.Invoice;
import com.mycompany.grocerystorepos.model.InvoiceDetail;
import com.mycompany.grocerystorepos.model.Product;
import java.sql.Date;

public class SaleProductController {
    private SaleProductView view;
    private InvoiceDAO invoiceDAO;
    private ProductDAO productDAO;
    private InvoiceDetailDAO invoiceDetailDAO;

    public SaleProductController(SaleProductView view) {
        this.view = view;
        this.invoiceDAO = new InvoiceDAO();
        this.productDAO = new ProductDAO();
        this.invoiceDetailDAO = new InvoiceDetailDAO();
    }

    // Xử lý sự kiện thanh toán
//    public void handlePaymentAction() {
//        double totalAmount = calculateTotalAmount();
//        double discountAmount = calculateDiscountAmount();
//
//        // Cập nhật hóa đơn
//        Invoice invoice = createInvoice(totalAmount, discountAmount);
//        invoiceDAO.save(invoice);
//
//        // Cập nhật chi tiết hóa đơn
//        for (int i = 0; i < view.getCartItemCount(); i++) {
//            Product product = view.getProductAt(i);
//            int quantity = view.getQuantityAt(i);
//            createInvoiceDetail(invoice, product, quantity);
//        }
//
//        // Cập nhật kho hàng
//        updateInventory();
//
//        // Hiển thị thông báo thanh toán thành công
//        view.showPaymentSuccessMessage();
//    }

//    // Tính tổng tiền thanh toán
//    private double calculateTotalAmount() {
//        double totalAmount = 0;
//        for (int i = 0; i < view.getCartItemCount(); i++) {
//            double price = view.getPriceAt(i);
//            int quantity = view.getQuantityAt(i);
//            totalAmount += price * quantity;
//        }
//        return totalAmount;
//    }

//    // Tính chiết khấu
//    private double calculateDiscountAmount() {
//        double totalAmount = calculateTotalAmount();
//        double discountRate = view.getDiscountRate();
//        return totalAmount * discountRate;
//    }

    // Tạo hóa đơn
//    private Invoice createInvoice(double totalAmount, double discountAmount) {
//        Invoice invoice = new Invoice();
//        invoice.setCustomerId(view.getCustomerId());
//        invoice.setEmployeeId(view.getEmployeeId());
//        invoice.setTotalAmount(totalAmount);
//        invoice.setDiscountAmount(discountAmount);
//        invoice.setDate(new Date());
//        return invoice;
//    }

    // Tạo chi tiết hóa đơn
    private void createInvoiceDetail(Invoice invoice, Product product, int quantity) {
        InvoiceDetail detail = new InvoiceDetail();
        detail.setInvoiceId(invoice.getId());
        detail.setProductId(product.getProductID());
        detail.setQuantity(quantity);
        detail.setPrice(product.getPrice());
        detail.setTotalPrice(product.getPrice() * quantity);
        invoiceDetailDAO.save(detail);
    }

//    // Cập nhật kho hàng
//    private void updateInventory() {
//        for (int i = 0; i < view.getCartItemCount(); i++) {
//            Product product = view.getProductAt(i);
//            int quantitySold = view.getQuantityAt(i);
//            productDAO.updateStockQuantity(product, quantitySold);
//        }
//    }
}
