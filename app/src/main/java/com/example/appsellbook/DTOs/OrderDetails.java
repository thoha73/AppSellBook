package com.example.appsellbook.DTOs;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class OrderDetails implements Parcelable {
    private  int orderDetailsId;
    private Orders orders;
    private Book book ;
    private int quantity;
    private double sellPrice;

    public OrderDetails() {
    }

    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
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

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
    public String getBookName(){
        return book != null ? book.getBookName() : null ;
    }
    public int getBookId(){
        return book != null ? book.getBookId() : -1;
    }
    public List<Image> getImages(){
        return book != null ? book.getImages() : null;
    }
    protected OrderDetails(Parcel in) {
        orderDetailsId = in.readInt();
        orders = in.readParcelable(Orders.class.getClassLoader());
        book = in.readParcelable(Book.class.getClassLoader());
        quantity = in.readInt();
        sellPrice = in.readDouble();
    }

    public static final Creator<OrderDetails> CREATOR = new Creator<OrderDetails>() {
        @Override
        public OrderDetails createFromParcel(Parcel in) {
            return new OrderDetails(in);
        }

        @Override
        public OrderDetails[] newArray(int size) {
            return new OrderDetails[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeDouble(sellPrice);
        dest.writeParcelable(book,flags);
        dest.writeInt(orderDetailsId);
        dest.writeParcelable(orders,flags);

    }
}

