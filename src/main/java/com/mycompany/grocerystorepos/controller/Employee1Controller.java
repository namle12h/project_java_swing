//package com.mycompany.grocerystorepos.controller;
//
//import com.mycompany.grocerystorepos.dao.Employee1DAO;
//import com.mycompany.grocerystorepos.model.Employee1;
//import com.mycompany.grocerystorepos.gui.Employee1View;
//import javax.swing.*;
//import java.util.List;
//
//public class Employee1Controller {
//    private Employee1View view;
//    private Employee1DAO model;
//
//    public Employee1Controller(Employee1View view, Employee1DAO model) {
//        this.view = view;
//        this.model = model;
//
//        view.getBtnAdd().addActionListener(e -> addEmployee());
//        view.getBtnUpdate().addActionListener(e -> updateEmployee());
//        view.getBtnDelete().addActionListener(e -> deleteEmployee());
//
//        setUpTableSelectionListener();
//        loadEmployees();
//    }
//
//    private void loadEmployees() {
//        view.getTableModel().setRowCount(0);
//        List<Employee1> employees = model.getAllEmployees();
//        for (Employee1 e : employees) {
//            view.getTableModel().addRow(new Object[]{
//                e.getEmployeeID(), e.getName(), e.getPhone(), e.getRole(), e.getUsername(), e.getShiftStart(), e.getShiftEnd(), e.getSalesPerformance()
//            });
//        }
//    }
//
//    private void addEmployee() {
//        String name = view.getEmployeeName();
//        String phone = view.getPhone();
//        String username = view.getUsername();
//        String password = view.getPassword();
//        String role = view.getRole();
//        String shiftStart = view.getShiftStart();
//        String shiftEnd = view.getShiftEnd();
//        int salesPerformance = 0;
//
//        if (name.isEmpty() || phone.isEmpty() || role.isEmpty() || username.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin!");
//            return;
//        }
//
//        Employee1 newEmployee = new Employee1(0, name, phone, role, username, password, shiftStart, shiftEnd, salesPerformance);
//        model.addEmployee(newEmployee);
//        JOptionPane.showMessageDialog(view, "Thêm nhân viên thành công!");
//        loadEmployees();
//    }
//
//    private void updateEmployee() {
//        int selectedRow = view.getTable().getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(view, "Chọn nhân viên cần cập nhật!");
//            return;
//        }
//
//        int employeeID = (int) view.getTableModel().getValueAt(selectedRow, 0);
//        String name = view.getEmployeeName();
//        String phone = view.getPhone();
//        String username = view.getUsername();
//        String password = view.getPassword();
//        String role = view.getRole();
//        String shiftStart = view.getShiftStart();
//        String shiftEnd = view.getShiftEnd();
//        int salesPerformance = (int) view.getTableModel().getValueAt(selectedRow, 7);
//
//        Employee1 updatedEmployee = new Employee1(employeeID, name, phone, role, username, password, shiftStart, shiftEnd, salesPerformance);
//        model.updateEmployee(updatedEmployee);
//        JOptionPane.showMessageDialog(view, "Cập nhật nhân viên thành công!");
//        loadEmployees();
//    }
//
//    private void deleteEmployee() {
//        int selectedRow = view.getTable().getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(view, "Chọn nhân viên cần xóa!");
//            return;
//        }
//
//        int employeeID = (int) view.getTableModel().getValueAt(selectedRow, 0);
//        model.deleteEmployee(employeeID);
//        JOptionPane.showMessageDialog(view, "Xóa nhân viên thành công!");
//        loadEmployees();
//    }
//
//    private void setUpTableSelectionListener() {
//        view.getTable().getSelectionModel().addListSelectionListener(e -> {
//            int selectedRow = view.getTable().getSelectedRow();
//            if (selectedRow != -1) {
//                view.setEmployeeName((String) view.getTableModel().getValueAt(selectedRow, 1));
//                view.setPhone((String) view.getTableModel().getValueAt(selectedRow, 2));
//                view.setRole((String) view.getTableModel().getValueAt(selectedRow, 3));
//                view.setUsername((String) view.getTableModel().getValueAt(selectedRow, 4));
//            }
//        });
//    }
//}
