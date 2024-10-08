package com.example.appsellbook.model;

public class OrderDetails {
    private int orderDetaisId;
    private Book book;
    private int quantity;
    private double price;
    private Order order;

    public OrderDetails(int orderDetaisId, Book book, int quantity, double price, Order order) {
        this.orderDetaisId = orderDetaisId;
        this.book = book;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public OrderDetails() {
    }

    public int getOrderDetaisId() {
        return orderDetaisId;
    }

    public void setOrderDetaisId(int orderDetaisId) {
        this.orderDetaisId = orderDetaisId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
