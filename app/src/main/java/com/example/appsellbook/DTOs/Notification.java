package com.example.appsellbook.DTOs;


import java.util.Date;

public class Notification {
    private int notificationId;
    private String context;
    private Date createdDate;
    private boolean isRead;
    private com.example.appsellbook.DTOs.User user;

    public Notification(int notificationId, String context, Date createdDate, boolean isRead, User user) {
        this.notificationId = notificationId;
        this.context = context;
        this.createdDate = createdDate;
        this.isRead = isRead;
        this.user = user;
    }

    public Notification() {
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}