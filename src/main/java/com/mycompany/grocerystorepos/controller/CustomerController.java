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

public class CustomerController {

    private CustomerView view;
    private CustomerDAO model;

    public CustomerController(CustomerView view, CustomerDAO model) {
        this.view = view;
        this.model = model;

        view.getBtnAdd().addActionListener(e -> addCustomer());
        view.getBtnUpdate().addActionListener(e -> updateCustomer());
        view.getBtnDelete().addActionListener(e -> deleteCustomer());

        setUpTableSelectionListener();
        loadCustomers();
    }

    private void loadCustomers() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
        tableModel.setRowCount(0);
        List<Customer> customers = model.getAllCustomers();
        for (Customer c : customers) {
            tableModel.addRow(new Object[]{
                c.getId(), c.getName(), c.getPhone(), c.getEmail()
            });
        }
    }

    String id = view.getCusID();
    String name = view.getCusName();
    String phone = view.getCusPhone();
    String email = view.getCusEmail();
    String point = view.getCusPoints();

    private void addCustomer() {

        Customer newCustomer = new Customer(id, name, phone, email, point);
        if (model.addCustomer(newCustomer)) {
            JOptionPane.showMessageDialog(view, "Thêm khách hàng thành công!");
            loadCustomers();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm khách hàng thất bại!");
        }
    }

    private void updateCustomer() {

        Customer updatedCustomer = new Customer(id, name, phone, email, point);
        if (model.updateCustomer(updatedCustomer)) {
            JOptionPane.showMessageDialog(view, "Cập nhật khách hàng thành công!");
            loadCustomers();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật khách hàng thất bại!");
        }
    }

    private void deleteCustomer() {

        if (model.deleteCustomer(id)) {
            JOptionPane.showMessageDialog(view, "Xóa khách hàng thành công!");
            loadCustomers();
        } else {
            JOptionPane.showMessageDialog(view, "Xóa khách hàng thất bại!");
        }
    }

    private void setUpTableSelectionListener() {
        view.getTable().getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow != -1) {
                view.setCusID(String.valueOf(view.getTable().getValueAt(selectedRow, 0)));
                view.setCusName((String) view.getTable().getValueAt(selectedRow, 1));
                view.setCusPhone(String.valueOf(view.getTable().getValueAt(selectedRow, 2)));
                view.setCusPoint((String) view.getTable().getValueAt(selectedRow, 3));
            }
        });
    }
}
