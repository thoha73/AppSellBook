package com.example.appsellbook.model;

import java.util.List;

public class User {
    private int userId;
    private String name;
    private String username;
    private String passwword;
    private List<Notification> listNotification;

    public User(String name, String username, String passwword) {
        this.name = name;
        this.username = username;
        this.passwword = passwword;
    }

    public User(int userId, String name, String username, String passwword, List<Notification> listNotification) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.passwword = passwword;
        this.listNotification = listNotification;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswword() {
        return passwword;
    }

    public void setPasswword(String passwword) {
        this.passwword = passwword;
    }

    public List<Notification> getListNotification() {
        return listNotification;
    }

    public void setListNotification(List<Notification> listNotification) {
        this.listNotification = listNotification;
    }
}
