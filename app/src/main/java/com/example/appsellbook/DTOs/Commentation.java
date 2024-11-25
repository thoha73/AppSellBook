package com.example.appsellbook.DTOs;

public class Commentation {
    private int commentationId;
    private String commentationContent;
    private double rank;
    private int bookId;
    private int userId;
    private Book book;
    private User user;

    public Commentation() {
    }

    public int getCommentationId() {
        return commentationId;
    }

    public void setCommentationId(int commentationId) {
        this.commentationId = commentationId;
    }

    public String getCommentationContent() {
        return commentationContent;
    }

    public void setCommentationContent(String commentationContent) {
        this.commentationContent = commentationContent;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
