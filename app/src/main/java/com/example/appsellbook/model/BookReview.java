package com.example.appsellbook.model;

import java.time.LocalDateTime;

public class BookReview {
    private int bookReviewId;
    private Book book;
    private String review;
    private LocalDateTime date;
//    private User user;
    private int rating;

    public BookReview(int bookReviewId, Book book, String review, LocalDateTime date, int rating) {
        this.bookReviewId = bookReviewId;
        this.book = book;
        this.review = review;
        this.date = date;
        this.rating = rating;
    }

    public BookReview() {
    }

    public int getBookReviewId() {
        return bookReviewId;
    }

    public void setBookReviewId(int bookReviewId) {
        this.bookReviewId = bookReviewId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
