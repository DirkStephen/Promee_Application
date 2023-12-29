package com.mobprog.promee.model;

public class UserDataClass {
    String username, email;

    public UserDataClass(){}

    public UserDataClass(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
