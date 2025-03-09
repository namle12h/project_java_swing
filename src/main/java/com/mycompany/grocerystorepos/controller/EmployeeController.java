package com.mycompany.grocerystorepos.controller;

import com.mycompany.grocerystorepos.dao.EmployeeDAO;
import com.mycompany.grocerystorepos.model.Employee;
import com.mycompany.grocerystorepos.gui.EmployeeView;
import java.sql.SQLException;
import javax.swing.*;
import java.util.List;

public class EmployeeController {
    private EmployeeView view;
    private EmployeeDAO model;

    public EmployeeController(EmployeeView view, EmployeeDAO model) {
        this.view = view;
        this.model = model;

        view.getBtnAdd().addActionListener(e -> {
            try {
                addEmployee();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi thêm nhân viên: " + ex.getMessage());
            }
        });

        view.getBtnUpdate().addActionListener(e -> {
            try {
                updateEmployee();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật nhân viên: " + ex.getMessage());
            }
        });

        view.getBtnDelete().addActionListener(e -> {
            try {
                deleteEmployee();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xóa nhân viên: " + ex.getMessage());
            }
        });

        setUpTableSelectionListener();
        loadEmployees();

        List<String> roles = model.getAllRoles(); // Kiểm tra nếu lỗi ở đây
        if (roles != null) {
            view.updateRoleComboBox(roles);
        }
    }

    private void loadEmployees() {
        view.getTableModel().setRowCount(0);
        List<Employee> employees = model.getAllEmployees();
        for (Employee e : employees) {
            view.getTableModel().addRow(new Object[]{
                e.getEmployeeId(), e.getName(), e.getPhone(), e.getRole()
            });
        }
    }

    private void addEmployee() throws SQLException {
        String name = view.getEmployeeName();
        String phone = view.getPhone();
        String role = view.getRole();

        if (name.isEmpty() || phone.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        Employee newEmployee = new Employee(0, name, phone, role);
        model.addEmployee(newEmployee);
        JOptionPane.showMessageDialog(view, "Thêm nhân viên thành công!");
        loadEmployees();
    }

    private void updateEmployee() throws SQLException {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Chọn nhân viên cần cập nhật!");
            return;
        }

        int employeeID = Integer.parseInt(view.getEmployeeID());
        String name = view.getEmployeeName();
        String phone = view.getPhone();
        String role = view.getRole();

        Employee updatedEmployee = new Employee(employeeID, name, phone, role);
//        model.updateEmployee(updatedEmployee);
        JOptionPane.showMessageDialog(view, "Cập nhật nhân viên thành công!");
        loadEmployees();
    }

    private void deleteEmployee() throws SQLException {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Chọn nhân viên cần xóa!");
            return;
        }

        int employeeID = Integer.parseInt(view.getEmployeeID());
        model.deleteEmployee(employeeID);
        JOptionPane.showMessageDialog(view, "Xóa nhân viên thành công!");
        loadEmployees();
    }

    private void setUpTableSelectionListener() {
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow != -1) {
                view.setEmployeeID(String.valueOf(view.getTableModel().getValueAt(selectedRow, 0)));
                view.setEmployeeName((String) view.getTableModel().getValueAt(selectedRow, 1));
                view.setPhone((String) view.getTableModel().getValueAt(selectedRow, 2));
                view.setRole((String) view.getTableModel().getValueAt(selectedRow, 3));
            }
        });
    }
}
