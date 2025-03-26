package com.mycompany.grocerystorepos.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Employee1View extends JFrame {
    private JTextField txtName, txtPhone, txtUsername, txtPassword, txtShiftStart, txtShiftEnd;
    private JComboBox<String> cbRole;
    private JButton btnAdd, btnUpdate, btnDelete;
    private JTable table;
    private DefaultTableModel tableModel;

    public Employee1View() {
        setTitle("Quản Lý Nhân Viên");
        setSize(800, 500);
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Tên", "SĐT", "Vai trò", "Username", "Ca bắt đầu", "Ca kết thúc", "Hiệu suất"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(10, 2));
        panel.add(new JLabel("Tên:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("SĐT:"));
        txtPhone = new JTextField();
        panel.add(txtPhone);

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel("Vai trò:"));
        cbRole = new JComboBox<>(new String[]{"Thu ngân", "Quản lý kho", "Chủ cửa hàng"});
        panel.add(cbRole);

        panel.add(new JLabel("Ca bắt đầu:"));
        txtShiftStart = new JTextField();
        panel.add(txtShiftStart);

        panel.add(new JLabel("Ca kết thúc:"));
        txtShiftEnd = new JTextField();
        panel.add(txtShiftEnd);

        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa");

        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);

        add(panel, BorderLayout.NORTH);
    }

    // 🔹 Getter để lấy dữ liệu từ view
    public String getEmployeeName() { return txtName.getText(); }
    public String getPhone() { return txtPhone.getText(); }
    public String getUsername() { return txtUsername.getText(); }
    public String getPassword() { return new String(txtPassword.getText()); }
    public String getRole() { return (String) cbRole.getSelectedItem(); }
    public String getShiftStart() { return txtShiftStart.getText(); }
    public String getShiftEnd() { return txtShiftEnd.getText(); }

    // 🔹 Setter để cập nhật dữ liệu vào view
    public void setEmployeeName(String name) { txtName.setText(name); }
    public void setPhone(String phone) { txtPhone.setText(phone); }
    public void setUsername(String username) { txtUsername.setText(username); }
    public void setPassword(String password) { txtPassword.setText(password); }
    public void setRole(String role) { cbRole.setSelectedItem(role); }
    public void setShiftStart(String shiftStart) { txtShiftStart.setText(shiftStart); }
    public void setShiftEnd(String shiftEnd) { txtShiftEnd.setText(shiftEnd); }

    // 🔹 Getter các nút bấm
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }

    // 🔹 Getter bảng
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
    
    
    public static void main(String[] args) {
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Employee1View().setVisible(true);
            }
        });
    }
}
