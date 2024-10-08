package com.example.appsellbook.model;

import java.util.List;

public class Book {
    private int bookId;
    private int image;
    private String bookName;
    private String author;
    private double price;
    private String description;
    private String isbn;
    private String category;
    private String publisher;
    private int saleNumber;
    private List<BookReview> bookReviewList;
    public Book(){

    }

    public Book(int image, String bookName, String author, double price, String description, String isbn, String category, String publisher, int saleNumber) {
        this.image = image;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
        this.description = description;
        this.isbn = isbn;
        this.category = category;
        this.publisher = publisher;
        this.saleNumber = saleNumber;
    }
    public Book(int bookId,int image, String bookName) {
        this.bookId=bookId;
        this.image = image;
        this.bookName = bookName;
    }

    public Book(int image, String bookName) {
        this.image = image;
        this.bookName = bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(int saleNumber) {
        this.saleNumber = saleNumber;
    }
}
