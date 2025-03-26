/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.grocerystorepos;

import com.mycompany.grocerystorepos.controller.EmployeeController;
import com.mycompany.grocerystorepos.controller.ProductController;
import com.mycompany.grocerystorepos.dao.Employee1DAO;
import com.mycompany.grocerystorepos.dao.EmployeeDAO;
import com.mycompany.grocerystorepos.dao.ProductDAO;

import com.mycompany.grocerystorepos.gui.EmployeePanel;

import com.mycompany.grocerystorepos.gui.Login;
import com.mycompany.grocerystorepos.gui.ProductView;
import com.mycompany.grocerystorepos.gui.ProductViewPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author ADMIN
 */
public class GroceryStorePOS {

    public static void main(String[] args) {
        // Sử dụng SwingUtilities để chạy trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Tạo ProductViewPanel và ProductDAO
            Login view = new Login();
            ProductDAO dao = new ProductDAO();  // Tạo một đối tượng ProductDAO

            // Tạo JFrame để hiển thị giao diện
            JFrame frame = new JFrame("Product Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Thêm ProductViewPanel vào JFrame (có thể là ProductController nếu bạn cần logic)
            frame.add(view);  // Hoặc frame.add(controller) nếu bạn muốn thêm controller vào frame

            // Hiển thị JFrame
            frame.setVisible(true);

//              EmployeePanel view = new EmployeePanel();
//        
//        // Khởi tạo model (Employee1DAO)
//        Employee1DAO dao = new Employee1DAO();
//        
//        // Khởi tạo controller và liên kết với view và model
//        new EmployeeController(view, dao);
//        
//        // Khởi tạo JFrame để hiển thị EmployeePanel
//        JFrame frame = new JFrame("Employee Management");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(view); // Thêm EmployeePanel vào JFrame
//        frame.setSize(800, 600); // Đặt kích thước cho JFrame
//        frame.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
//        frame.setVisible(true); // Hiển thị JFrame
        });
    }
}
