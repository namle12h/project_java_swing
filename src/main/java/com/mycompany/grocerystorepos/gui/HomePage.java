//package com.mycompany.grocerystorepos.gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class Homepage1 extends javax.swing.JPanel {
//
//    private CardLayout cardLayout;  // Để điều hướng giữa các trang
//    private JPanel cardPanel;       // Panel chứa các trang
//
//    public Homepage1() {
//        initComponents();
//        // Khởi tạo CardLayout
//        cardLayout = new CardLayout();
//        cardPanel = new JPanel(cardLayout);
//
//        // Tạo các panel cho các trang khác nhau
//        JPanel homePanel = createHomePanel();
//        JPanel designPanel = createDesignPanel();
//        JPanel statsPanel = createStatisticsPanel();
//
//        // Thêm các panel vào cardPanel
//        cardPanel.add(homePanel, "Home");
//        cardPanel.add(designPanel, "Design");
//        cardPanel.add(statsPanel, "Statistics");
//
//        // Thêm cardPanel vào Homepage1
//        setLayout(new BorderLayout());
//        add(cardPanel, BorderLayout.CENTER);
//    }
//
//    // Tạo trang chủ
//    private JPanel createHomePanel() {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BorderLayout());
//        JLabel label = new JLabel("Quản Lý Cửa Hàng", SwingConstants.CENTER);
//        panel.add(label, BorderLayout.CENTER);
//
//        // Các nút điều hướng
//        JPanel buttonPanel = new JPanel();
//        JButton productButton = new JButton("Home");
//        JButton designButton = new JButton("Trang design");
//        JButton statsButton = new JButton("Trang Thống kê");
//
//        // Cài đặt hành động cho các nút
//        productButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cardPanel, "Home");
//            }
//        });
//
//        designButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cardPanel, "Design");
//            }
//        });
//
//        statsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cardPanel, "Statistics");
//            }
//        });
//
//        buttonPanel.add(productButton);
//        buttonPanel.add(designButton);
//        buttonPanel.add(statsButton);
//
//        panel.add(buttonPanel, BorderLayout.SOUTH);
//        return panel;
//    }
//
//    // Tạo trang Design
//    private JPanel createDesignPanel() {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BorderLayout());
//        JLabel label = new JLabel("Trang Design", SwingConstants.CENTER);
//        panel.add(label, BorderLayout.CENTER);
//
//        // Nút quay lại
//        JButton backButton = new JButton("Quay lại");
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cardPanel, "Home");
//            }
//        });
//        panel.add(backButton, BorderLayout.SOUTH);
//
//        return panel;
//    }
//
//    // Tạo trang Thống kê
//    private JPanel createStatisticsPanel() {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BorderLayout());
//        JLabel label = new JLabel("Trang Thống kê", SwingConstants.CENTER);
//        panel.add(label, BorderLayout.CENTER);
//
//        // Nút quay lại
//        JButton backButton = new JButton("Quay lại");
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cardPanel, "Home");
//            }
//        });
//        panel.add(backButton, BorderLayout.SOUTH);
//
//        return panel;
//    }
//
//    // Main method để chạy ứng dụng
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Homepage1");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//        frame.add(new Homepage1()); // Thêm Homepage1 vào JFrame
//        frame.setVisible(true);
//    }
//}
