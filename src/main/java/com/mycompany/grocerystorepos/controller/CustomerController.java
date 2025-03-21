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

    private final CustomerView view;
    private final CustomerDAO model;

    public CustomerController(CustomerView view, CustomerDAO model) {
        if (view == null) {
            throw new IllegalArgumentException("CustomerView không được null!");
        }
        if (model == null) {
            throw new IllegalArgumentException("CustomerDAO không được null!");
        }
        this.view = view;
        this.model = model;

        setUpTableSelectionListener();
        loadCustomers();
    }

    // ✅ Load danh sách khách hàng
    public void loadCustomers() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
        tableModel.setRowCount(0); // 🔥 Xóa dữ liệu cũ

        List<Customer> customers = model.getAllCustomers();
        if (customers != null) {
            for (Customer c : customers) {
                tableModel.addRow(new Object[]{
                    c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getPoint()
                });
            }
        }
        tableModel.fireTableDataChanged();
    }

    // ✅ Thêm khách hàng mới
    public void addCustomer() {
        String name = view.getCusName();
        String phone = view.getCusPhone();
        String email = view.getCusEmail();
        String point = view.getCusPoint();

        // Tạo khách hàng mới (KHÔNG có ID)
        Customer newCustomer = new Customer(name, phone, email, point);
        boolean success = model.addCustomer(newCustomer);

        if (success) {
            JOptionPane.showMessageDialog(view, "Thêm khách hàng thành công! ID: " + newCustomer.getId());
            loadCustomers(); // 🔥 Cập nhật danh sách
        } else {
            JOptionPane.showMessageDialog(view, "Thêm khách hàng thất bại!");
        }
    }

    // ✅ Cập nhật thông tin khách hàng
    private void updateCustomer() {
        try {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn khách hàng cần cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = (int) view.getTable().getValueAt(selectedRow, 0);
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Có lỗi xảy ra khi cập nhật khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ Xóa khách hàng theo ID
    public void deleteCustomer() {
        try {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn khách hàng cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = (int) view.getTable().getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa khách hàng này?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (model.deleteCustomer(id)) {
                    JOptionPane.showMessageDialog(view, "Xóa khách hàng thành công!");
                    loadCustomers();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa khách hàng thất bại!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Có lỗi xảy ra khi xóa khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ Xử lý sự kiện chọn hàng trong bảng
    private void setUpTableSelectionListener() {
        view.getTable().getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    view.setCusName((String) view.getTable().getValueAt(selectedRow, 1)); // Name
                    view.setCusPhone(String.valueOf(view.getTable().getValueAt(selectedRow, 2))); // Phone
                    view.setCusEmail((String) view.getTable().getValueAt(selectedRow, 3)); // Email
                    view.setCusPoint(String.valueOf(view.getTable().getValueAt(selectedRow, 4))); // Points
                }
            }
        });
    }
}
