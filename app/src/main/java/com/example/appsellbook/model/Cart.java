package com.example.appsellbook.model;

public class Cart {
    int image ;
    String name;
    double price;
    int quantity;
    public Cart(){

    }
    public Cart(int image, String name, double price, int quantity) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
