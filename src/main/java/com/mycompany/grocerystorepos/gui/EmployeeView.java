package com.mycompany.grocerystorepos.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeView extends JFrame {
    private JTextField txtEmployeeID, txtName, txtPhone;
    private JComboBox<String> cbRole;
    private JButton btnAdd, btnUpdate, btnDelete;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public EmployeeView() {
        setTitle("Quản lý nhân viên");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("ID Nhân viên:"));
        txtEmployeeID = new JTextField();
        txtEmployeeID.setEnabled(false); // Không cho phép nhập ID
        panel.add(txtEmployeeID);

        panel.add(new JLabel("Tên nhân viên:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Số điện thoại:"));
        txtPhone = new JTextField();
        panel.add(txtPhone);

        panel.add(new JLabel("Chức vụ:"));
        cbRole = new JComboBox<>();
        panel.add(cbRole);

        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa");
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);

        tableModel = new DefaultTableModel(new String[]{"ID", "Tên", "SĐT", "Chức vụ", "Ngày tạo"}, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getEmployeeID() {
        return txtEmployeeID.getText();
    }

    public void setEmployeeID(String id) {
        txtEmployeeID.setText(id);
    }

    public String getEmployeeName() {
        return txtName.getText();
    }

    public void setEmployeeName(String name) {
        txtName.setText(name);
    }

    public String getPhone() {
        return txtPhone.getText();
    }

    public void setPhone(String phone) {
        txtPhone.setText(phone);
    }

    public String getRole() {
        return (String) cbRole.getSelectedItem();
    }

    public void setRole(String role) {
        cbRole.setSelectedItem(role);
    }

    public JTable getTable() {
        return employeeTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JComboBox<String> getRoleComboBox() {
        return cbRole;
    }

    public void updateRoleComboBox(List<String> roles) {
        cbRole.removeAllItems();
        for (String role : roles) {
            cbRole.addItem(role);
        }
    }
    
      public static void main(String[] args) {
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Employee1View().setVisible(true);
            }
        });
    }
}
