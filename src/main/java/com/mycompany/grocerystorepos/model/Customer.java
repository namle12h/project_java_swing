/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grocerystorepos.model;

/**
 *
 * @author LENOVO
 */



public class Customer {

    private String id;
    private String name;
    private String phone;
    private String email;
    private String point;

    public Customer(String id, String name, String phone, String email, String point) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.point = point;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    
    @Override
    public String toString() {
        return id + " - " + name;
    }
}
