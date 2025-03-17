/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableModel;

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private JTable table;
    private boolean isClicked;
      private SaleProductView view; 

    public ButtonEditor(JCheckBox checkBox, JTable table, SaleProductView view) {
        super(checkBox);
        this.table = table;
          this.view = view;

        button = new JButton("Xóa");
        // button.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         fireEditingStopped();
        //     }
        // });
         button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    ((DefaultTableModel) table.getModel()).removeRow(row);

                    // Gọi updateTotalPrice từ SaleProductView sau khi xóa sản phẩm
                    view.updateTotalPrice();
                }
                 if (table.getRowCount() == 0) {
                ((DefaultTableModel) table.getModel()).fireTableDataChanged();
            }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        isClicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isClicked) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(selectedRow);
              
            }
        }
        isClicked = false;
        return "Xóa";
    }
    
}
