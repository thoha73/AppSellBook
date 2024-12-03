package com.example.appsellbook.DTOs;

public class OrderDetail {
    private int orderDetailId;
    private double sellPrice;
    private int quantity;
    private Book book;
    private Order order;

    public OrderDetail() {
    }

    public OrderDetail(int orderDetailId, double sellPrice, int quantity, Book book, Order order) {
        this.orderDetailId = orderDetailId;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.book = book;
        this.order = order;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}