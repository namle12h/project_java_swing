/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.grocerystorepos.gui;

import com.mycompany.grocerystorepos.dao.CustomerDAO;
import com.mycompany.grocerystorepos.dao.ProductDAO;
import com.mycompany.grocerystorepos.model.Customer;
import com.mycompany.grocerystorepos.model.Product;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class SaleProductView extends javax.swing.JFrame {

//    private JTextField txtSearchProduct;
    private JPopupMenu suggestionMenu;
    private List<String> productList;
    private ProductDAO productDAO;
    private CustomerDAO customerDAO;
    private DefaultTableModel cartTableModel;
    private javax.swing.JTable cartTable;
    private double discountRate = 0;

    /**
     * Creates new form SaleProductView
     */
    public SaleProductView() {

        initComponents();
        productDAO = new ProductDAO();
        customerDAO = new CustomerDAO();
        setupSearchFeature();
    }

    private void setupSearchFeature() {
        suggestionMenu = new JPopupMenu();

        txtsearchproduct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                showSuggestions();
            }
        });
        btnthemgiohang.addActionListener(e -> addToCart());

        btnkhachhang.addActionListener(e -> {
            // Khi nhấn nút, thực hiện tìm kiếm khách hàng
            searchCustomer();
        });

    }
     // Phương thức công khai để cập nhật tên nhân viên vào JLabel
    public void setEmployeeName(String employeeName) {
        lbnhanvien.setText("" + employeeName);
    }

    private void searchCustomer() {
        String phone = txtkhachhang.getText().trim();  // Lấy số điện thoại từ ô tìm kiếm

        // Kiểm tra nếu ô tìm kiếm trống
        if (phone.isEmpty()) {
            // Nếu ô tìm kiếm trống, hiển thị thông báo yêu cầu nhập số điện thoại
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;  // Dừng hàm nếu không có số điện thoại
        }

        // Gọi phương thức trong DAO để tìm khách hàng theo số điện thoại
        Customer customer = customerDAO.searchCustomerByPhone(phone);

        // Kiểm tra xem có khách hàng nào được tìm thấy không
        if (customer != null) {
            // Nếu tìm thấy khách hàng, hiển thị thông tin khách hàng lên giao diện
            txttenkh.setText("" + customer.getName());
            txtemail.setText("" + customer.getEmail());
            txtsdt.setText("" + customer.getPhone());
            txtdiem.setText("" + customer.getPoint());
        } else {
            // Nếu không tìm thấy khách hàng, hiển thị thông báo không có trong hệ thống
            JOptionPane.showMessageDialog(null, "Khách hàng chưa tồn tại trong hệ thống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Xóa thông tin hiện tại trong các JLabel
            txttenkh.setText("");
            txtemail.setText("");
            txtsdt.setText("");
            txtdiem.setText("");
        }
    }

    private void showSuggestions() {
        suggestionMenu.removeAll();
        String text = txtsearchproduct.getText().trim().toLowerCase();

        if (text.isEmpty()) {
            suggestionMenu.setVisible(false);
            return;
        }

        // 🔹 Lấy danh sách sản phẩm từ database dựa theo từ khóa tìm kiếm
        List<String> productList = productDAO.searchProducts(text);

        for (String product : productList) {
            JMenuItem item = new JMenuItem(product);
            item.addActionListener(e -> {
                txtsearchproduct.setText(product); // Chọn sản phẩm sẽ nhập vào ô tìm kiếm
                suggestionMenu.setVisible(false);
            });
            suggestionMenu.add(item);
        }

        if (suggestionMenu.getComponentCount() > 0) {
            suggestionMenu.show(txtsearchproduct, 0, txtsearchproduct.getHeight());
        }
    }

    private double totalAmount = 0;
    private double totalDiscount = 0;

    private void addToCart() {
        String productName = txtsearchproduct.getText().trim();
        int quantity = (int) amount.getValue(); // Lấy số lượng từ JSpinner

        if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product product = productDAO.getProductByName(productName);

        if (product == null) {
            JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblsanpham.getModel();
        boolean found = false;

        double discountRate = 0.1;

        for (int i = 0; i < model.getRowCount(); i++) {
            Object productNameInTable = model.getValueAt(i, 1);

            if (productNameInTable != null && productNameInTable.toString().equals(product.getProductName())) {
                int currentQuantity = Integer.parseInt(model.getValueAt(i, 4).toString());
                int newQuantity = currentQuantity + quantity;
                model.setValueAt(newQuantity, i, 4);

                // Cập nhật thành tiền (giá * số lượng)
                double totalPrice = product.getPrice() * newQuantity;
                double totalPriceWithDiscount = totalPrice * (1 - discountRate);
                model.setValueAt(totalPriceWithDiscount, i, 5);

                // Cập nhật tổng tiền và chiết khấu
                totalAmount += totalPriceWithDiscount;
                totalDiscount += (totalPrice - totalPriceWithDiscount);

                found = true;
                break;
            }
        }

        if (!found) {
            double totalPrice = product.getPrice() * quantity; // Thành tiền ban đầu
            double totalPriceWithDiscount = totalPrice * (1 - discountRate);
            model.addRow(new Object[]{
                product.getProductID(),
                product.getProductName(),
                "Hình ảnh",
                product.getPrice(),
                quantity,
                totalPrice, // Cột "Thành tiền"
                "Xóa"
            });
            // Cập nhật tổng tiền và chiết khấu
            totalAmount += totalPriceWithDiscount;
            totalDiscount += (totalPrice - totalPriceWithDiscount);
        }

        updateTotalPrice();  // Cập nhật tổng tiền giỏ hàng
        txtsearchproduct.setText("");
        amount.setValue(0);
    }



    public void updateTotalPrice() {
    DefaultTableModel model = (DefaultTableModel) tblsanpham.getModel();
    double total = 0; // Tổng tiền sau chiết khấu
    double totalDiscount = 0; // Tổng chiết khấu
    double originalTotalPrice = 0; // Tổng tiền ban đầu (không có chiết khấu)
    
    // Duyệt qua tất cả các sản phẩm trong giỏ hàng
    for (int i = 0; i < model.getRowCount(); i++) {
        Object priceObj = model.getValueAt(i, 3); // Lấy giá tiền sản phẩm
        Object quantityObj = model.getValueAt(i, 4); // Lấy số lượng sản phẩm

        if (priceObj != null && quantityObj != null) {
            double price = Double.parseDouble(priceObj.toString()); // Giá tiền sản phẩm
            int quantity = Integer.parseInt(quantityObj.toString()); // Số lượng sản phẩm
            double originalPrice = price * quantity; // Tính tổng tiền cho sản phẩm này (không có chiết khấu)

            // Cộng dồn tổng tiền ban đầu
            originalTotalPrice += originalPrice;

            // Tính chiết khấu cho sản phẩm này
            double discountAmount = originalPrice * discountRate; // Chiết khấu cho sản phẩm
            totalDiscount += discountAmount; // Cộng dồn chiết khấu cho tất cả sản phẩm

            // Tính thành tiền sau khi trừ chiết khấu cho sản phẩm
            double totalPriceAfterDiscount = originalPrice - discountAmount;
            total += totalPriceAfterDiscount; // Cộng dồn tổng tiền sau chiết khấu
        }
    }

    // Cập nhật thông tin trên giao diện
    lbtongtien.setText(String.format(" %.0f", originalTotalPrice)); // Tổng tiền ban đầu
    lbchietkhau.setText(String.format(" %.0f", totalDiscount)); // Tổng chiết khấu
    lbthanhtien.setText(String.format(" %.0f", total)); // Tổng tiền sau chiết khấu
}


public void showPaymentDialog() {
    // Lấy thông tin khách hàng từ các JTextField hoặc JLabel trong SaleProductView
    String customerName = txttenkh.getText();   // Tên khách hàng
    String email = txtemail.getText();          // Email khách hàng
    String phone = txtsdt.getText();            // Số điện thoại khách hàng
    String point = txtdiem.getText();           // Điểm khách hàng (nếu cần)
    String employeeName = lbnhanvien.getText();
    // Giả sử bạn đã tính toán tổng tiền, chiết khấu và thành tiền
    double totalAmount = Double.parseDouble(lbtongtien.getText().trim());  // Tổng tiền
    double discountAmount = Double.parseDouble(lbchietkhau.getText().trim()); // Chiết khấu
    double finalAmount = Double.parseDouble(lbthanhtien.getText().trim()); // Thành tiền

    // Tạo cửa sổ thanh toán và hiển thị
    Payments paymentDialog = new Payments(this, true);
    paymentDialog.setCustomerDetails(customerName, email, phone);  // Truyền thông tin khách hàng
    paymentDialog.setPaymentDetails(totalAmount, discountAmount, finalAmount);  // Truyền thông tin thanh toán
    paymentDialog.setProductList(getCartItems());  // Truyền giỏ hàng
    paymentDialog.SetEmployeeDetails(employeeName);
    
    
    paymentDialog.setVisible(true); // Hiển thị cửa sổ thanh toán
}

private List<Product> getCartItems() {
    List<Product> cartItems = new ArrayList<>();
    DefaultTableModel model = (DefaultTableModel) tblsanpham.getModel();

    // Lấy tất cả các sản phẩm trong giỏ hàng
    for (int i = 0; i < model.getRowCount(); i++) {
        
        String productName = model.getValueAt(i, 1).toString();
        int quantity = Integer.parseInt(model.getValueAt(i, 4).toString());
        double price = Double.parseDouble(model.getValueAt(i, 3).toString());
        double totalPrice =  price * quantity;

        Product product = new Product(productName,quantity, price); // Tạo sản phẩm
        cartItems.add(product); // Thêm vào giỏ hàng
    }

    return cartItems; // Trả về giỏ hàng
}





    private void removeFromCart(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) tblsanpham.getModel();
        model.removeRow(rowIndex);
        updateTotalPrice(); // Cập nhật tổng tiền sau khi xóa
    }

    private void openAddCustomerPage() {
        // Giả sử trang thêm khách hàng là một JFrame có tên là AddCustomerPage
        CustomerView addCustomerPage = new CustomerView();  // Tạo đối tượng của JFrame mới
        addCustomerPage.setVisible(true);  // Mở cửa sổ thêm khách hàng mới
    }

 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtsearchproduct = new javax.swing.JTextField();
        amount = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbtongtien = new javax.swing.JLabel();
        lbchietkhau = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbthanhtien = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnhuyphieu = new javax.swing.JButton();
        btnthanhtoan = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnkhachhang = new javax.swing.JButton();
        txtkhachhang = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txttenkh = new javax.swing.JLabel();
        txtemail = new javax.swing.JLabel();
        txtsdt = new javax.swing.JLabel();
        txtdiem = new javax.swing.JLabel();
        lbnhanvien = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnthemgiohang = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsanpham = new javax.swing.JTable();
        cboxchietkhau = new javax.swing.JComboBox<>();
        btnthemkh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("PRODUCT SALE");

        jLabel2.setText("Tìm sản phẩm");

        txtsearchproduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsearchproductActionPerformed(evt);
            }
        });

        jLabel3.setText("Số Lượng");

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));
        jPanel2.setForeground(new java.awt.Color(255, 51, 0));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tổng Cộng");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Chiết khấu hóa đơn");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("VNĐ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("VNĐ");

        lbtongtien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbtongtien.setForeground(new java.awt.Color(255, 255, 255));

        lbchietkhau.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbchietkhau.setForeground(new java.awt.Color(255, 255, 255));
        lbchietkhau.setText("1000000");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(lbchietkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbtongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(lbtongtien))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(lbchietkhau))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Thành Tiền");

        lbthanhtien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbthanhtien.setForeground(new java.awt.Color(255, 255, 255));
        lbthanhtien.setText("1000000");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("VNĐ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(lbthanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbthanhtien)
                    .addComponent(jLabel13))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        btnhuyphieu.setBackground(new java.awt.Color(204, 204, 255));
        btnhuyphieu.setText("Hủy Phiếu");
        btnhuyphieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhuyphieuActionPerformed(evt);
            }
        });

        btnthanhtoan.setBackground(new java.awt.Color(204, 204, 255));
        btnthanhtoan.setText("Thanh Toán");
        // btnthanhtoan.addActionListener(new java.awt.event.ActionListener() {
        //     public void actionPerformed(java.awt.event.ActionEvent evt) {
        //         btnthanhtoanActionPerformed(evt);
        //     }
        // });
        
        btnthanhtoan.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        // Xử lý khi nhấn nút thanh toán
        showPaymentDialog();
    }
});



        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        jLabel9.setText("Khách Hàng");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        btnkhachhang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnkhachhang.setText("Tìm");
        // btnkhachhang.addActionListener(new java.awt.event.ActionListener() {
        //     public void actionPerformed(java.awt.event.ActionEvent evt) {
        //         btnkhachhangActionPerformed(evt);
        //     }
        // });

        txtkhachhang.setText("Nhập sdt khách hàng");
        txtkhachhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkhachhangActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));

        jLabel10.setText("EMAIL");

        jLabel11.setText("Tên Khách hàng");

        jLabel14.setText("SĐT");

        jLabel17.setText("Điểm");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttenkh, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdiem, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(215, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(txttenkh)
                .addGap(18, 18, 18)
                .addComponent(txtemail)
                .addGap(12, 12, 12)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtsdt)
                .addGap(12, 12, 12)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addContainerGap(137, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addComponent(jLabel11)
                    .addContainerGap(176, Short.MAX_VALUE)))
        );

        lbnhanvien.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        lbnhanvien.setForeground(new java.awt.Color(153, 153, 255));
        lbnhanvien.setText("Thu Ngân 1");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Nhân Viên");

        btnthemgiohang.setText("thêm giỏ hàng");

        tblsanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
              
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Hình ảnh", "Giá tiền", "Số lượng", "Thành Tiền", "Chức Năng"
            }
        ));
        jScrollPane1.setViewportView(tblsanpham);
        tblsanpham.getAccessibleContext().setAccessibleParent(tblsanpham);

        cboxchietkhau.setBackground(new java.awt.Color(204, 204, 255));
        cboxchietkhau.setForeground(new java.awt.Color(51, 51, 255));
        cboxchietkhau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10%", "25%", "50%", "70%" }));
        cboxchietkhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxchietkhauActionPerformed(evt);
            }
        });

        btnthemkh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnthemkh.setText("them");
        btnthemkh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemkhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 863, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(txtsearchproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnthemgiohang)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbnhanvien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(117, 117, 117)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnthanhtoan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(cboxchietkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btnhuyphieu))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtkhachhang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnthemkh, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnthanhtoan, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(cboxchietkhau)
                    .addComponent(btnhuyphieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnthemkh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnkhachhang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(256, 256, 256))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtsearchproduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(btnthemgiohang))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbnhanvien)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtsearchproductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsearchproductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchproductActionPerformed

    private void btnhuyphieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhuyphieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnhuyphieuActionPerformed

    private void btnthanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthanhtoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthanhtoanActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtkhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkhachhangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkhachhangActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnthemgiohangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemgiohangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthemgiohangActionPerformed

    private void cboxchietkhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxchietkhauActionPerformed
          String selectedDiscount = cboxchietkhau.getSelectedItem().toString();

        // Chuyển đổi chuỗi chọn được thành tỷ lệ chiết khấu (0%, 5%, 10%, 15%)
        switch (selectedDiscount) {
            case "10%":
                discountRate = 0.01;
                break;
            case "25%":
                discountRate = 0.25;
                break;
            case "50%":
                discountRate = 0.5;
                break;
            case "70%":
               discountRate = 0.7;
                break;
            default:
                discountRate = 0; // Nếu chọn "0%" hoặc không có lựa chọn
                break;
        }

        // Cập nhật lại giỏ hàng với tỷ lệ chiết khấu mới
        updateTotalPrice(); // Cập nhật lại tổng tiền sau khi thay đổi chiết khấu
    }//GEN-LAST:event_cboxchietkhauActionPerformed

    private void btnthemkhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemkhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthemkhActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SaleProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SaleProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SaleProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SaleProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SaleProductView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner amount;
    private javax.swing.JButton btnhuyphieu;
    private javax.swing.JButton btnkhachhang;
    private javax.swing.JButton btnthanhtoan;
    private javax.swing.JButton btnthemgiohang;
    private javax.swing.JButton btnthemkh;
    private javax.swing.JComboBox<String> cboxchietkhau;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbchietkhau;
    private javax.swing.JLabel lbnhanvien;
    private javax.swing.JLabel lbthanhtien;
    private javax.swing.JLabel lbtongtien;
    private javax.swing.JTable tblsanpham;
    private javax.swing.JLabel txtdiem;
    private javax.swing.JLabel txtemail;
    private javax.swing.JTextField txtkhachhang;
    private javax.swing.JLabel txtsdt;
    private javax.swing.JTextField txtsearchproduct;
    private javax.swing.JLabel txttenkh;
    // End of variables declaration//GEN-END:variables
}
