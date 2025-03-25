/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.grocerystorepos;

import com.mycompany.grocerystorepos.controller.Employee1Controller;
import com.mycompany.grocerystorepos.controller.ProductController;
import com.mycompany.grocerystorepos.dao.Employee1DAO;
import com.mycompany.grocerystorepos.dao.ProductDAO;
import com.mycompany.grocerystorepos.gui.Employee1View;
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
            ProductViewPanel view = new ProductViewPanel();
            ProductDAO dao = new ProductDAO();  // Tạo một đối tượng ProductDAO

            // Tạo ProductController và truyền vào view và dao
            ProductController controller = new ProductController(view, dao);

            // Tạo JFrame để hiển thị giao diện
            JFrame frame = new JFrame("Product Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Thêm ProductViewPanel vào JFrame (có thể là ProductController nếu bạn cần logic)
            frame.add(view);  // Hoặc frame.add(controller) nếu bạn muốn thêm controller vào frame

            // Hiển thị JFrame
            frame.setVisible(true);
        });
    }
}
