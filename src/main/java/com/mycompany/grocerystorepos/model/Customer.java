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
    private String point;

    public Customer(String id, String name, String phone, String point) {
        this.id = id;
        this.name = name;
        this.phone = phone;
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
        return point;
    }

    public void setEmail(String point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
