/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.model;

public class CurrentUser {
    private static int employeeId;  // Lưu EmployeeID

    // Getter để lấy EmployeeID
    public static int getEmployeeId() {
        return employeeId;
    }

    // Setter để lưu EmployeeID
    public static void setEmployeeId(int employeeId) {
        CurrentUser.employeeId = employeeId;
    }

    // Bạn có thể thêm các thuộc tính khác nếu cần (như tên nhân viên)
    private static String employeeName;

    public static String getEmployeeName() {
        return employeeName;
    }

    public static void setEmployeeName(String name) {
        employeeName = name;
    }
}
