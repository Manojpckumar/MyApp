package com.example.theerthamovers.Pojo;

public class Adminpref {
    int id;

    String username,email,phone;

    public Adminpref() {
    }

    public Adminpref(int id, String username, String email, String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
