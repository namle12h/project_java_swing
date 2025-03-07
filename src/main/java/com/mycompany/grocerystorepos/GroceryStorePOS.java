/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.grocerystorepos;

import com.mycompany.grocerystorepos.controller.ProductController;
import com.mycompany.grocerystorepos.dao.ProductDAO;
import com.mycompany.grocerystorepos.gui.ProductView;
import javax.swing.SwingUtilities;

/**
 *
 * @author ADMIN
 */
public class GroceryStorePOS {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductView view = new ProductView();
            ProductDAO dao = new ProductDAO(); // Tạo một đối tượng ProductDAO
            new ProductController(view, dao); // Truyền cả hai tham số vào
            view.setVisible(true);
        });
    }
}
