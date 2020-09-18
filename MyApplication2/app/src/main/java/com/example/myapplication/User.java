package com.example.myapplication;

public class User {
    private String password = "", email = "";

    public User() {
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Hai buddy, \nUser Name: " + password+ ",Password : " + email +"\n will be safe with us";
    }
}
