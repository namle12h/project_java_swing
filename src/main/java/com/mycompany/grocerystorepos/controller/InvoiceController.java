package com.mycompany.grocerystorepos.controller;

import com.mycompany.grocerystorepos.dao.InvoiceDAO;
import com.mycompany.grocerystorepos.gui.Payments;
//import com.mycompany.grocerystorepos.model.InvoicePrinter;
import com.mycompany.grocerystorepos.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class InvoiceController {

    private Payments paymentsView;
    private String employeeName;

    public InvoiceController(Payments paymentsView) {
        this.paymentsView = paymentsView;
    }


    // public void onPaymentSuccess() {
    //     // Lấy thông tin khách hàng từ view Payments
    //     String customerName = paymentsView.getCustomerName();
    //     String email = paymentsView.getEmail();
    //     String phone = paymentsView.getPhone();

    //     // Lấy thông tin thanh toán từ view Payments
    //     double totalAmount = paymentsView.getTotalAmount();
    //     double discountAmount = paymentsView.getDiscountAmount();
    //     double finalAmount = paymentsView.getFinalAmount();

    //     String employeeName = paymentsView.getEmployeeName();
    //     int employeeId = getEmployeeId(employeeName);  // Tạo phương thức này để lấy employee_id từ tên nhân viên
    //     // Lấy giỏ hàng từ view Payments (sản phẩm trong bảng)
    //     List<Product> cartItems = getCartItems();

    //     String paymentMethod = getPaymentMethod();
    //     // Lưu thông tin hóa đơn vào database
    //     InvoiceDAO invoiceDAO = new InvoiceDAO();
    //     int customerId = 1;  // Giả sử lấy được customerId từ số điện thoại khách hàng

    //     // Gọi phương thức saveInvoice và kiểm tra kết quả
    //     boolean isSuccess = invoiceDAO.saveInvoice(customerId, totalAmount, discountAmount, finalAmount, cartItems, employeeId, paymentMethod);

    //     if (isSuccess) {
    //         // Nếu lưu thành công, hiển thị thông báo thành công
    //         JOptionPane.showMessageDialog(paymentsView, "Thanh toán thành công! Hóa đơn đã được tạo.", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
    //          paymentsView.dispose();
    //     } else {
    //         // Nếu có lỗi, hiển thị thông báo lỗi
    //         JOptionPane.showMessageDialog(paymentsView, "Có lỗi xảy ra khi tạo hóa đơn. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
    //     }

    // }


    public void onPaymentSuccess() {
    // Lấy thông tin khách hàng từ view Payments
    String customerName = paymentsView.getCustomerName();
    String email = paymentsView.getEmail();
    String phone = paymentsView.getPhone();

    // Kiểm tra xem các thông tin khách hàng có đầy đủ không
    if (customerName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
        JOptionPane.showMessageDialog(paymentsView, "Vui lòng điền đầy đủ thông tin khách hàng.", "Thông Báo", JOptionPane.WARNING_MESSAGE);
        return;  // Dừng lại nếu thông tin khách hàng không đầy đủ
    }

    // Lấy thông tin thanh toán từ view Payments
    double totalAmount = paymentsView.getTotalAmount();
    double discountAmount = paymentsView.getDiscountAmount();
    double finalAmount = paymentsView.getFinalAmount();

    // Kiểm tra xem các thông tin thanh toán có hợp lệ không (ví dụ: tổng tiền phải > 0)
    if (totalAmount <= 0 || finalAmount <= 0) {
        JOptionPane.showMessageDialog(paymentsView, "Tổng tiền hoặc thành tiền không hợp lệ.", "Thông Báo", JOptionPane.WARNING_MESSAGE);
        return;  // Dừng lại nếu thông tin thanh toán không hợp lệ
    }

    // Kiểm tra xem người dùng đã chọn phương thức thanh toán chưa
    String paymentMethod = getPaymentMethod();
    if (paymentMethod.equals("Chưa chọn")) {
        JOptionPane.showMessageDialog(paymentsView, "Vui lòng chọn phương thức thanh toán.", "Thông Báo", JOptionPane.WARNING_MESSAGE);
        return;  // Dừng lại nếu không chọn phương thức thanh toán
    }

    // Lấy tên nhân viên và ID nhân viên
    String employeeName = paymentsView.getEmployeeName();
    int employeeId = getEmployeeId(employeeName);  // Lấy employeeId từ tên nhân viên

    // Lấy giỏ hàng từ view Payments (sản phẩm trong bảng)
    List<Product> cartItems = getCartItems();

    // Lưu thông tin hóa đơn vào database
    InvoiceDAO invoiceDAO = new InvoiceDAO();
    int customerId = 1;  // Giả sử lấy được customerId từ số điện thoại khách hàng

    // Gọi phương thức saveInvoice và kiểm tra kết quả
    boolean isSuccess = invoiceDAO.saveInvoice(customerId, totalAmount, discountAmount, finalAmount, cartItems, employeeId, paymentMethod);

    if (isSuccess) {
        // Nếu lưu thành công, hiển thị thông báo thành công
        JOptionPane.showMessageDialog(paymentsView, "Thanh toán thành công! Hóa đơn đã được tạo.", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        paymentsView.dispose();  // Đóng cửa sổ thanh toán sau khi thành công
    } else {
        // Nếu có lỗi, hiển thị thông báo lỗi
        JOptionPane.showMessageDialog(paymentsView, "Có lỗi xảy ra khi tạo hóa đơn. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
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

    // Giả sử bạn có phương thức này để lấy employee_id từ tên nhân viên
    private int getEmployeeId(String employeeName) {
        // Gọi DAO để lấy employee_id từ tên nhân viên
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        return invoiceDAO.getEmployeeIdByName(employeeName);  // Gọi phương thức trong DAO
    }

// Phương thức lấy phương thức thanh toán
    private String getPaymentMethod() {
        // Kiểm tra xem phương thức thanh toán là tiền mặt hay QR
        if (paymentsView.isCashSelected()) {
            return "Tiền mặt";  // Nếu "Tiền mặt" được chọn
        } else if (paymentsView.isQrSelected()) {
            return "QR";  // Nếu "QR" được chọn
        } else {
            return "Chưa chọn";  // Nếu không có phương thức thanh toán nào được chọn
        }
    }

}
