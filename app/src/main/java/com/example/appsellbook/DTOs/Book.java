//package com.example.appsellbook.DTOs;
//
//import java.io.Serializable;
//import java.util.List;
//
//public class Book implements Serializable {
//    private int bookId;
//    private String bookName;
//    private String description;
//    private String isbn;
//    private double listedPrice;
//    private double sellPrice;
//    private int quantity;
//    private Author author;
//    private double rank;
//    private List<Image> images;
//
//    public Book(int bookId, String bookName, String description, String isbn, double listedPrice, double sellPrice, int quantity, Author author, double rank, List<Image> images) {
//        this.bookId = bookId;
//        this.bookName = bookName;
//        this.description = description;
//        this.isbn = isbn;
//        this.listedPrice = listedPrice;
//        this.sellPrice = sellPrice;
//        this.quantity = quantity;
//        this.author = author;
//        this.rank = rank;
//        this.images = images;
//    }
//
//    public Book() {
//    }
//
//    public int getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(int bookId) {
//        this.bookId = bookId;
//    }
//
//    public String getBookName() {
//        return bookName;
//    }
//
//    public void setBookName(String bookName) {
//        this.bookName = bookName;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getIsbn() {
//        return isbn;
//    }
//
//    public void setIsbn(String isbn) {
//        this.isbn = isbn;
//    }
//
//    public double getListedPrice() {
//        return listedPrice;
//    }
//
//    public void setListedPrice(double listedPrice) {
//        this.listedPrice = listedPrice;
//    }
//
//    public double getSellPrice() {
//        return sellPrice;
//    }
//
//    public void setSellPrice(double sellPrice) {
//        this.sellPrice = sellPrice;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public Author getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(Author author) {
//        this.author = author;
//    }
//
//    public double getRank() {
//        return rank;
//    }
//
//    public void setRank(double rank) {
//        this.rank = rank;
//    }
//
//    public List<Image> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }
//}
package com.example.appsellbook.DTOs;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Book implements Parcelable {
    private int bookId;
    private String bookName;
    private String description;
    private String isbn;
    private double listedPrice;
    private double sellPrice;
    private String publisher;
    private int quantity;
    private double rank;
    private Author author;
    private List<Image> images;
    private List<Category> categories;

    public Book(int bookId, String bookName, String description, String isbn,String publisher, double listedPrice, double sellPrice, int quantity, double rank, Author author,List<Image> images,List<Category> categories) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.description = description;
        this.isbn = isbn;
        this.listedPrice = listedPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.author = author;
        this.rank = rank;
        this.images = images;
        this.categories=categories;
        this.publisher=publisher;
    }

    public Book() {}

    // Getter and Setter methods

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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

    public double getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(double listedPrice) {
        this.listedPrice = listedPrice;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    // Parcelable methods

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
        dest.writeString(description);
        dest.writeString(publisher);
        dest.writeString(isbn);
        dest.writeDouble(listedPrice);
        dest.writeDouble(sellPrice);
        dest.writeInt(quantity);
        dest.writeParcelable(author,flags);
        dest.writeDouble(rank);
        dest.writeTypedList(images);
        dest.writeTypedList(categories);// Assumes Image implements Parcelable
    }

    // Creator
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    // Constructor to read from Parcel
    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
        description = in.readString();
        publisher = in.readString();
        isbn = in.readString();
        listedPrice = in.readDouble();
        sellPrice = in.readDouble();
        quantity = in.readInt();
        author = in.readParcelable(Author.class.getClassLoader());
        rank = in.readDouble();
        images = in.createTypedArrayList(Image.CREATOR);
        categories = in.createTypedArrayList(Category.CREATOR);
    }
}
