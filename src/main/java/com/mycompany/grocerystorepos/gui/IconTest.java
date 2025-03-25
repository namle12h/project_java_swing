/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IconTest {
    public static void main(String[] args) {
        // Tạo JFrame
        JFrame frame = new JFrame("Icon Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        
        // Tạo một nút và đặt icon cho nó
        JButton button = new JButton("Click me");
        
        // Tải icon từ tài nguyên
        URL location = IconTest.class.getResource("/image/add.png"); // Đảm bảo đường dẫn đúng

        // Kiểm tra xem tài nguyên có được tìm thấy không
        if (location == null) {
            System.err.println("Icon không tìm thấy tại: /image/add.png");
        } else {
            ImageIcon icon = new ImageIcon(location);
            button.setIcon(icon); // Đặt icon cho nút
        }

        // Thêm nút vào frame
        frame.add(button, BorderLayout.CENTER);
        
        // Hiển thị frame
        frame.setVisible(true);
    }
}
