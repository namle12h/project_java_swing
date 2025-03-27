/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.grocerystorepos.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InvoicePanel extends JPanel {

    private JLabel lblCustomerName, lblPhone, lblEmail;
    private JTable tblProducts;
    private JLabel lblTotal, lblDiscount, lblFinalAmount;
    private JRadioButton rbtnCash, rbtnQR;
    private JLabel lblEmployeeName;
    private JButton btnSave, btnCancel;

    public InvoicePanel() {
        setLayout(new BorderLayout());

        // Top Panel: Customer info
        JPanel customerInfoPanel = new JPanel(new GridLayout(3, 2));
        lblCustomerName = new JLabel("Customer Name");
        lblPhone = new JLabel("Phone");
        lblEmail = new JLabel("Email");

        customerInfoPanel.add(lblCustomerName);
        customerInfoPanel.add(new JTextField(20));
        customerInfoPanel.add(lblPhone);
        customerInfoPanel.add(new JTextField(20));
        customerInfoPanel.add(lblEmail);
        customerInfoPanel.add(new JTextField(20));

        // Center Panel: Product table
        String[] columnNames = {"Product Code", "Product Name", "Price", "Quantity", "Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        tblProducts = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(tblProducts);

        // Bottom Panel: Payment info and buttons
        JPanel paymentPanel = new JPanel(new GridLayout(3, 2));
        lblTotal = new JLabel("Total: ");
        lblDiscount = new JLabel("Discount: ");
        lblFinalAmount = new JLabel("Final Amount: ");
        rbtnCash = new JRadioButton("Cash");
        rbtnQR = new JRadioButton("QR");
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(rbtnCash);
        paymentGroup.add(rbtnQR);
        
        paymentPanel.add(lblTotal);
        paymentPanel.add(new JTextField(10));
        paymentPanel.add(lblDiscount);
        paymentPanel.add(new JTextField(10));
        paymentPanel.add(lblFinalAmount);
        paymentPanel.add(new JTextField(10));

        // Bottom: Employee info and buttons
        JPanel employeePanel = new JPanel();
        lblEmployeeName = new JLabel("Employee: ");
        employeePanel.add(lblEmployeeName);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Add panels to main panel
        add(customerInfoPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(paymentPanel, BorderLayout.SOUTH);
        add(employeePanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
