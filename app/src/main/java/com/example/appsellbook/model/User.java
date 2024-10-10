package com.example.appsellbook.model;

public class User {
    String name;
    String username;
    String passwword;

    public User(String name, String username, String passwword) {
        this.name = name;
        this.username = username;
        this.passwword = passwword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswword() {
        return passwword;
    }

    public void setPasswword(String passwword) {
        this.passwword = passwword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
