package com.mycompany.grocerystorepos.controller;

import com.mycompany.grocerystorepos.dao.InvoiceDAO;
import com.mycompany.grocerystorepos.gui.Payments;
//import com.mycompany.grocerystorepos.model.InvoicePrinter;
import com.mycompany.grocerystorepos.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class InvoiceController {

    private Payments paymentsView;

    public InvoiceController(Payments paymentsView) {
        this.paymentsView = paymentsView;
    }

    public void onPaymentSuccess() {
        // Lấy thông tin khách hàng từ view Payments
        String customerName = paymentsView.getCustomerName();
        String email = paymentsView.getEmail();
        String phone = paymentsView.getPhone();

        // Lấy thông tin thanh toán từ view Payments
        double totalAmount = paymentsView.getTotalAmount();
        double discountAmount = paymentsView.getDiscountAmount();
        double finalAmount = paymentsView.getFinalAmount();

        // Lấy giỏ hàng từ view Payments (sản phẩm trong bảng)
        List<Product> cartItems = getCartItems();

        // Lưu thông tin hóa đơn vào database
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        int customerId = 1;  // Giả sử lấy được customerId từ số điện thoại khách hàng
        invoiceDAO.saveInvoice(customerId, totalAmount, discountAmount, finalAmount, cartItems);

        // Sau khi lưu, bạn có thể tạo hóa đơn PDF và gửi cho khách hàng
        int invoiceId = 1;  // Giả sử lấy invoiceId từ database sau khi insert vào DB
//        InvoicePrinter.generateInvoicePDF(invoiceId, customerName, email, phone, cartItems, totalAmount, discountAmount, finalAmount);

        // In thông báo thành công
        System.out.println("Thanh toán thành công! Hóa đơn đã được tạo.");
    }

    // Phương thức lấy giỏ hàng từ bảng (tạo một đối tượng Product từ bảng trên giao diện)
    private List<Product> getCartItems() {
        DefaultTableModel model = (DefaultTableModel) paymentsView.getTbhoadon().getModel();
        List<Product> cartItems = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            String productName = model.getValueAt(i, 1).toString();
            int quantity = Integer.parseInt(model.getValueAt(i, 3).toString());
            double price = Double.parseDouble(model.getValueAt(i, 2).toString());
            Product product = new Product(productName, quantity, price);  // Tạo đối tượng Product từ thông tin giỏ hàng
            cartItems.add(product);
        }

        return cartItems;
    }
}
