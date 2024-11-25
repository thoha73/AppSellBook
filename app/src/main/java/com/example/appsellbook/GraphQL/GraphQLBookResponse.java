package com.example.appsellbook.GraphQL;

import com.example.appsellbook.DTOs.Book;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public class GraphQLBookResponse {

    @SerializedName("bookById")
    private LinkedTreeMap<String, Object> bookById;

    private List<Book> books;

    public LinkedTreeMap<String, Object> getBookById() {
        return bookById;
    }

    public void setBookById(LinkedTreeMap<String, Object> bookById) {
        this.bookById = bookById;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
