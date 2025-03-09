/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.conDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class connectDB {
    private static final String hostName = "localhost:1433";
    private static final String dbName = "dbs";
    private static final String username = "sa";
    private static final String password = "12345";
    private static final String conURL = "jdbc:sqlserver://" + hostName + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";

    // Thêm static vào phương thức connect()
    public static Connection connect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(conURL, username, password);
            System.out.println("Connected to database successfully!");
        } catch (SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, "Database connection failed!", ex);
        }
        return con;
    }
}

