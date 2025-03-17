package com.mycompany.grocerystorepos.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchSuggestion extends JFrame {
    private JTextField searchField;
    private JPopupMenu suggestionMenu;
    private List<String> productList;

    public ProductSearchSuggestion() {
        setTitle("Tìm kiếm sản phẩm có gợi ý");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        searchField = new JTextField(20);
        suggestionMenu = new JPopupMenu();

        // Danh sách sản phẩm mẫu (Có thể thay bằng dữ liệu từ database)
        productList = new ArrayList<>();
        productList.add("Bột giặt Aba");
        productList.add("Nước rửa chén Sunlight");
        productList.add("Dầu gội Clear");
        productList.add("Sữa tắm Lux");
        productList.add("Kem đánh răng Colgate");
        productList.add("Bột giặt Omo");
        productList.add("Dầu gội Pantene");

        // Thêm sự kiện gợi ý khi nhập
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                showSuggestions();
            }
        });

        add(searchField);
    }

    // Hiển thị danh sách gợi ý
    private void showSuggestions() {
        suggestionMenu.removeAll();
        String text = searchField.getText().toLowerCase();

        if (text.isEmpty()) {
            suggestionMenu.setVisible(false);
            return;
        }

        for (String product : productList) {
            if (product.toLowerCase().contains(text)) {
                JMenuItem item = new JMenuItem(product);
                item.addActionListener(e -> {
                    searchField.setText(product);
                    suggestionMenu.setVisible(false);
                });
                suggestionMenu.add(item);
            }
        }

        if (suggestionMenu.getComponentCount() > 0) {
            suggestionMenu.show(searchField, 0, searchField.getHeight());
        }
    }

    public static void main(String[] args) {
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductSearchSuggestion().setVisible(true);
            }
        });
    }
}
