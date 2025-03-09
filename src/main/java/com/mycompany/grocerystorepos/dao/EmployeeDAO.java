package com.mycompany.grocerystorepos.dao;

import com.mycompany.grocerystorepos.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    // 🔹 Lấy danh sách tất cả nhân viên
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("EmployeeID"),
                        rs.getString("Name"),
                        rs.getString("Phone"),
                        rs.getString("Role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // 🔹 Thêm nhân viên mới
    public void addEmployee(Employee employee) {
        String query = "INSERT INTO Employee (Name, Phone, Role) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getPhone());
            pstmt.setString(3, employee.getRole());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Cập nhật thông tin nhân viên
    public void updateEmployee(Employee employee) {
        String query = "UPDATE Employee SET Name=?, Phone=?, Role=? WHERE EmployeeID=?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getPhone());
            pstmt.setString(3, employee.getRole());
            pstmt.setInt(4, employee.getEmployeeId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Xóa nhân viên theo ID
    public void deleteEmployee(int employeeID) {
        String query = "DELETE FROM Employee WHERE EmployeeID=?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, employeeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Lấy thông tin nhân viên theo ID
    public Employee getEmployeeByID(int employeeID) {
        String query = "SELECT * FROM Employee WHERE EmployeeID=?";
        Employee employee = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, employeeID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("EmployeeID"),
                        rs.getString("Name"),
                        rs.getString("Phone"),
                        rs.getString("Role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    // 🔹 Lấy danh sách vai trò nhân viên
    public List<String> getAllRoles() {
        List<String> roles = new ArrayList<>();
        String query = "SELECT DISTINCT Role FROM Employee";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                roles.add(rs.getString("Role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    // 🔹 Xác thực đăng nhập nhân viên
    public Employee authenticateEmployee(String phone, String role) {
        String query = "SELECT * FROM Employee WHERE Phone=? AND Role=?";
        Employee employee = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, phone);
            pstmt.setString(2, role);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("EmployeeID"),
                        rs.getString("Name"),
                        rs.getString("Phone"),
                        rs.getString("Role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}
