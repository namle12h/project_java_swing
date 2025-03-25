/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.model;

import java.awt.Font;
import java.io.File;

public class TestFont {
    public static void main(String[] args) {
        try {
            // Cung cấp đường dẫn chính xác đến file font
            File fontFile = new File("D:\\doan\\project_java_swing\\src\\main\\java\\font\\Arial Unicode Font\\Arial-Unicode-Bold-Italic.ttf");
            if (fontFile.exists()) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
                System.out.println("Font loaded successfully: " + font.getName());
            } else {
                System.out.println("Font file not found at: " + fontFile.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
