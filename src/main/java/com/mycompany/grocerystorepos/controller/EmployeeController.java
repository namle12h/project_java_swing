package com.mycompany.grocerystorepos.controller;

import com.mycompany.grocerystorepos.dao.Employee1DAO;
import com.mycompany.grocerystorepos.model.Employee1;
import com.mycompany.grocerystorepos.gui.EmployeePanel;
import javax.swing.*;
import java.util.List;

public class EmployeeController {
    private EmployeePanel view;
    private Employee1DAO model;

    public EmployeeController(EmployeePanel view, Employee1DAO model) {
        this.view = view;
        this.model = model;

        // Các action listener cho các nút
        view.getBtnAdd().addActionListener(e -> addEmployee());
        view.getBtnUpdate().addActionListener(e -> updateEmployee());
        view.getBtnDelete().addActionListener(e -> deleteEmployee());

        // Thiết lập hành động khi chọn một dòng trong bảng
        setUpTableSelectionListener();
        
        // Tải dữ liệu nhân viên
        loadEmployees();
    }

    // Phương thức tải tất cả nhân viên từ model và hiển thị trên bảng
    private void loadEmployees() {
        view.getTableModel().setRowCount(0);  // Xóa dữ liệu hiện tại trong bảng
        List<Employee1> employees = model.getAllEmployees();  // Lấy danh sách nhân viên từ DAO
        for (Employee1 e : employees) {
            view.getTableModel().addRow(new Object[] {
                e.getEmployeeID(), e.getName(), e.getPhone(), e.getRole(), e.getUsername(), e.getPassword(), e.getShiftStart(), e.getShiftEnd(), e.getSalesPerformance()
            });
        }
    }

    // Phương thức thêm nhân viên
    private void addEmployee() {
      
        String name = view.getEmployeeName();
        String phone = view.getPhone();
        String username = view.getUsername();
        String password = view.getPassword();
        String role = view.getRole();
        String shiftStart = view.getShiftStart();
        String shiftEnd = view.getShiftEnd();
        int salesPerformance = 0;  // Mặc định salesPerformance là 0

        // Kiểm tra thông tin đầu vào
        if (name.isEmpty() || phone.isEmpty() || role.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        // Tạo đối tượng Employee1 mới và thêm vào cơ sở dữ liệu
        Employee1 newEmployee = new Employee1(0, name, phone, role, username, password, shiftStart, shiftEnd, salesPerformance);
        model.addEmployee(newEmployee);
        JOptionPane.showMessageDialog(view, "Thêm nhân viên thành công!");
        loadEmployees();  // Cập nhật lại bảng sau khi thêm nhân viên mới
    }

    // Phương thức cập nhật thông tin nhân viên
    private void updateEmployee() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Chọn nhân viên cần cập nhật!");
            return;
        }

        // Lấy thông tin nhân viên từ bảng và các trường nhập liệu
        int employeeID = Integer.parseInt(view.getEmployeeID()); // Lấy mã nhân viên
        String name = view.getEmployeeName();
        String phone = view.getPhone();
        String username = view.getUsername();
        String password = view.getPassword();
        String role = view.getRole();
        String shiftStart = view.getShiftStart();
        String shiftEnd = view.getShiftEnd();
        int salesPerformance = (int) view.getTableModel().getValueAt(selectedRow, 8);  // Lấy hiệu suất bán hàng từ bảng

        // Tạo đối tượng Employee1 với thông tin đã thay đổi
        Employee1 updatedEmployee = new Employee1(employeeID, name, phone, role, username, password, shiftStart, shiftEnd, salesPerformance);
        model.updateEmployee(updatedEmployee);  // Cập nhật nhân viên vào cơ sở dữ liệu
        JOptionPane.showMessageDialog(view, "Cập nhật nhân viên thành công!");
        loadEmployees();  // Cập nhật lại bảng sau khi cập nhật nhân viên
    }

    // Phương thức xóa nhân viên
    private void deleteEmployee() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Chọn nhân viên cần xóa!");
            return;
        }

        // Lấy ID của nhân viên cần xóa từ bảng
        int employeeID = (int) view.getTableModel().getValueAt(selectedRow, 0);
        model.deleteEmployee(employeeID);  // Xóa nhân viên trong cơ sở dữ liệu
        JOptionPane.showMessageDialog(view, "Xóa nhân viên thành công!");
        loadEmployees();  // Cập nhật lại bảng sau khi xóa nhân viên
    }

    // Phương thức để thiết lập hành động khi chọn một dòng trong bảng
    private void setUpTableSelectionListener() {
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow != -1) {
                // Cập nhật thông tin vào các trường nhập liệu khi chọn nhân viên
                view.setEmployeeID(String.valueOf(view.getTableModel().getValueAt(selectedRow, 0)));
                view.setEmployeeName((String) view.getTableModel().getValueAt(selectedRow, 1));
                view.setPhone((String) view.getTableModel().getValueAt(selectedRow, 2));
                view.setRole((String) view.getTableModel().getValueAt(selectedRow, 3));
                view.setUsername((String) view.getTableModel().getValueAt(selectedRow, 4));
                view.setPassword((String) view.getTableModel().getValueAt(selectedRow, 5));
                view.setShiftStart((String) view.getTableModel().getValueAt(selectedRow, 6));
                view.setShiftEnd((String) view.getTableModel().getValueAt(selectedRow, 7));
            }
        });
    }
    
}
