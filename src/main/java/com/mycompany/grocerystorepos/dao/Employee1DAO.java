package com.mycompany.grocerystorepos.dao;

import com.mycompany.grocerystorepos.model.Employee1;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Employee1DAO {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QLBanHang1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    // Lấy danh sách tất cả nhân viên
    public List<Employee1> getAllEmployees() {
        List<Employee1> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee1";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                employees.add(new Employee1(
                        rs.getInt("EmployeeID"),
                        rs.getString("Name"),
                        rs.getString("Phone"),
                        rs.getString("Role"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("ShiftStart"),
                        rs.getString("ShiftEnd"),
                        rs.getInt("SalesPerformance")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // 🛠️ Thêm nhân viên mới
    public void addEmployee(Employee1 employee) {
        String query = "INSERT INTO Employee1 (Name, Phone, Role, Username, Password, ShiftStart, ShiftEnd, SalesPerformance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getPhone());
            pstmt.setString(3, employee.getRole());
            pstmt.setString(4, employee.getUsername());
            pstmt.setString(5, employee.getPassword());
            pstmt.setString(6, employee.getShiftStart());
            pstmt.setString(7, employee.getShiftEnd());
            pstmt.setInt(8, employee.getSalesPerformance());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🛠️ Cập nhật thông tin nhân viên
    public void updateEmployee(Employee1 employee) {
        String query = "UPDATE Employee1 SET Name=?, Phone=?, Role=?, Username=?, Password=?, ShiftStart=?, ShiftEnd=?, SalesPerformance=? WHERE EmployeeID=?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getPhone());
            pstmt.setString(3, employee.getRole());
            pstmt.setString(4, employee.getUsername());
            pstmt.setString(5, employee.getPassword());
            pstmt.setString(6, employee.getShiftStart());
            pstmt.setString(7, employee.getShiftEnd());
            pstmt.setInt(8, employee.getSalesPerformance());
            pstmt.setInt(9, employee.getEmployeeID());  // WHERE EmployeeID = ?

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🛠️ Xóa nhân viên
    public void deleteEmployee(int employeeID) {
        String query = "DELETE FROM Employee1 WHERE EmployeeID=?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, employeeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
