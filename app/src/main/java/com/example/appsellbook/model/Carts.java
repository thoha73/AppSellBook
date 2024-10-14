package com.example.appsellbook.model;

public class Carts {  // Class name should follow Java naming conventions: Capitalized first letter
    int img;
    private String name;
    private String price;

    // Constructor
    public Carts(int img, String name, String price) {  // Corrected "pirce" to "price"
        this.img = img;
        this.name = name;
        this.price = price;  // Corrected "pirce" to "price"
    }

    // Getter for image
    public int getImg() {
        return img;
    }

    // Setter for image
    public void setImg(int img) {
        this.img = img;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for price
    public String getPrice() {  // Corrected "pirce" to "price"
        return price;
    }

    // Setter for price
    public void setPrice(String price) {  // Corrected "pirce" to "price"
        this.price = price;
    }

    // toString method
    @Override
    public String toString() {
        return "Cart{" +  // Class name changed to "Cart"
                "img=" + img +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +  // Corrected formatting
                '}';
    }
}
