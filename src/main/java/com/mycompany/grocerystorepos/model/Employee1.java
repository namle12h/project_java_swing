package com.mycompany.grocerystorepos.model;

public class Employee1 {
    private int employeeID;
    private String name;
    private String phone;
    private String role;
    private String username;
    private String password;
    private String shiftStart;
    private String shiftEnd;
    private int salesPerformance;

    public Employee1(int employeeID, String name, String phone, String role, String username, String password, String shiftStart, String shiftEnd, int salesPerformance) {
        this.employeeID = employeeID;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.username = username;
        this.password = password;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.salesPerformance = salesPerformance;
    }

    public int getEmployeeID() { return employeeID; }
    public void setEmployeeID(int employeeID) { this.employeeID = employeeID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getShiftStart() { return shiftStart; }
    public void setShiftStart(String shiftStart) { this.shiftStart = shiftStart; }

    public String getShiftEnd() { return shiftEnd; }
    public void setShiftEnd(String shiftEnd) { this.shiftEnd = shiftEnd; }

    public int getSalesPerformance() { return salesPerformance; }
    public void setSalesPerformance(int salesPerformance) { this.salesPerformance = salesPerformance; }
}
