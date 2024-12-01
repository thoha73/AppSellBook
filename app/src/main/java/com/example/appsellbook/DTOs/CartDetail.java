package com.example.appsellbook.DTOs;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

@SuppressLint("ParcelCreator")
public class CartDetail implements Parcelable {
    private int cartDetailId;
    private Carts cartId;
    private Book book; // Lưu đối tượng Book thay vì lưu bookId, bookName, và images riêng biệt
    private int quantity;
    private double sellPrice;
    private boolean isSelected;

    public CartDetail() {
    }

    // Getter và Setter cho các trường
    public int getCartDetailId() {
        return cartDetailId;
    }

    public void setCartDetailId(int cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public int getCartId() {
        return cartId != null ? cartId.getCartId(): 0;
    }

    public void setCartId(Carts cartId) {
        this.cartId = cartId;
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

    // Trả về bookName từ đối tượng Book
    public String getBookName() {
        return book != null ? book.getBookName() : null;
    }

    // Trả về images từ đối tượng Book
    public List<Image> getImages() {
        return book != null ? book.getImages() : null;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public String getdate(){
        return cartId != null ? cartId.getPurchaseAddress() : null;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(cartDetailId);
        dest.writeParcelable(cartId, flags);
        dest.writeParcelable(book, flags); // Ghi đối tượng Book
        dest.writeInt(quantity);
        dest.writeDouble(sellPrice);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    public static final Parcelable.Creator<CartDetail> CREATOR = new Parcelable.Creator<CartDetail>() {
        @Override
        public CartDetail createFromParcel(Parcel in) {
            return new CartDetail(in);
        }

        @Override
        public CartDetail[] newArray(int size) {
            return new CartDetail[size];
        }
    };

    // Constructor để đọc từ Parcel
    protected CartDetail(Parcel in) {
        cartDetailId = in.readInt();
        cartId = in.readParcelable(Carts.class.getClassLoader());
        book = in.readParcelable(Book.class.getClassLoader()); // Đọc đối tượng Book
        quantity = in.readInt();
        sellPrice = in.readDouble();
        isSelected = in.readByte() != 0;
    }

}