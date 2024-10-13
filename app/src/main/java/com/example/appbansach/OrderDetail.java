package com.example.appbansach;

public class OrderDetail {

    private String ordererName;
    private String order;
    private int iconResourceId;      // ID tài nguyên của icon

    // Constructor
    public OrderDetail(int iconResourceId, String order, String ordererName) {
        this.iconResourceId = iconResourceId;
        this.order = order;
        this.ordererName = ordererName;
    }

    // Getter cho ID đơn hàng
    public String getOrder() {
        return order;
    }

    // Getter cho tên người đặt hàng
    public String getOrdererName() {
        return ordererName;
    }

    // Getter cho icon
    public int getIconResourceId() {
        return iconResourceId;
    }

    // Setter cho tên người đặt hàng
    public void setOrdererName(String ordererName) {
        this.ordererName = ordererName;
    }

    // Setter cho ID đơn hàng
    public void setOrder(String order) {
        this.order = order;
    }

    // Setter cho icon
    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    // Phương thức toString
    @Override
    public String toString() {
        return "OrderDetail{" +
                "ordererName='" + ordererName + '\'' +
                ", order='" + order + '\'' +
                ", iconResourceId=" + iconResourceId +
                '}';
    }
}
