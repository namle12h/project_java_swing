/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.controller;

/**
 *
 * @author LENOVO
 */
import com.mycompany.grocerystorepos.dao.CustomerDAO;
import com.mycompany.grocerystorepos.model.Customer;
import com.mycompany.grocerystorepos.gui.CustomerView;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public final class CustomerController {

    private CustomerView view;
    private CustomerDAO model;

    public CustomerController(CustomerView view, CustomerDAO model) {
        if (view == null) {
            throw new IllegalArgumentException("CustomerView không được null!");
        }
        if (model == null) {
            throw new IllegalArgumentException("CustomerDAO không được null!");
        }
        this.view = view;
        this.model = model;

        view.getBtnUpdate().addActionListener(e -> updateCustomer());

        setUpTableSelectionListener();
        loadCustomers();
    }

    public void loadCustomers() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();

        // 🔥 Xóa toàn bộ dữ liệu trong bảng trước khi thêm dữ liệu mới
        tableModel.setRowCount(0);

        // 🔥 Lấy danh sách khách hàng mới từ Database
        List<Customer> customers = model.getAllCustomers();

        // 🔥 Kiểm tra danh sách không bị null
        if (customers != null) {
            for (Customer c : customers) {
                tableModel.addRow(new Object[]{
                    c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getPoint()
                });
            }
        }

        // 🔥 Đảm bảo bảng cập nhật dữ liệu mới
        tableModel.fireTableDataChanged();
    }

    public void addCustomer() {
        String id = view.getCusID();
        String name = view.getCusName();
        String phone = view.getCusPhone();
        String email = view.getCusEmail();
        String point = view.getCusPoint();

        Customer newCustomer = new Customer(id, name, phone, email, point);

        // 🛑 Kiểm tra nếu thêm khách hàng thất bại trước khi load dữ liệu
        boolean success = model.addCustomer(newCustomer);

        if (success) {
            JOptionPane.showMessageDialog(view, "Thêm khách hàng thành công!");
            loadCustomers();  // 🔥 Chỉ load lại nếu thêm thành công
        } else {
            JOptionPane.showMessageDialog(view, "Thêm khách hàng thất bại!");
        }
    }

    private void updateCustomer() {
        String id = view.getCusID();
        String name = view.getCusName();
        String phone = view.getCusPhone();
        String email = view.getCusEmail();
        String point = view.getCusPoint();

        Customer updatedCustomer = new Customer(id, name, phone, email, point);
        if (model.updateCustomer(updatedCustomer)) {
            JOptionPane.showMessageDialog(view, "Cập nhật khách hàng thành công!");
            loadCustomers();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật khách hàng thất bại!");
        }
    }

    public void deleteCustomer() {
        String id = view.getCusID();
        if (model.deleteCustomer(id)) {
            JOptionPane.showMessageDialog(view, "Xóa khách hàng thành công!");
            loadCustomers();
        } else {
            JOptionPane.showMessageDialog(view, "Xóa khách hàng thất bại!");
        }
    }

    private void setUpTableSelectionListener() {
        view.getTable().getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) { // Tránh sự kiện bị gọi nhiều lần
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    // Kiểm tra số lượng cột khớp với bảng dữ liệu
                    if (view.getTable().getColumnCount() >= 5) {
                        view.setCusID(String.valueOf(view.getTable().getValueAt(selectedRow, 0))); // ID
                        view.setCusName((String) view.getTable().getValueAt(selectedRow, 1)); // Name
                        view.setCusPhone(String.valueOf(view.getTable().getValueAt(selectedRow, 2))); // Phone
                        view.setCusEmail((String) view.getTable().getValueAt(selectedRow, 3)); // Email
                        view.setCusPoint(String.valueOf(view.getTable().getValueAt(selectedRow, 4))); // Points
                    }
                }
            }
        });
    }
}
