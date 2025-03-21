/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.grocerystorepos;

import com.mycompany.grocerystorepos.controller.Employee1Controller;
import com.mycompany.grocerystorepos.dao.Employee1DAO;
import com.mycompany.grocerystorepos.gui.Employee1View;
import javax.swing.SwingUtilities;

/**
 *
 * @author ADMIN
 */
public class GroceryStorePOS {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            ProductView view = new ProductView();
//            ProductDAO dao = new ProductDAO(); // Tạo một đối tượng ProductDAO
//            new ProductController(view, dao); // Truyền cả hai tham số vào
               Employee1View view = new Employee1View();
               Employee1DAO dao = new Employee1DAO();
               new Employee1Controller(view, dao);
//            ProductSearchSuggestion view = new ProductSearchSuggestion();
            view.setVisible(true);
        });
    }
}
