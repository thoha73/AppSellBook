package com.example.appsellbook.DTOs;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private int categoryId;
    private String categoryName;

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(categoryId);
        dest.writeString(categoryName);
    }

    // Phương thức mô tả các nội dung của đối tượng
    @Override
    public int describeContents() {
        return 0;
    }

    // Constructor tạo đối tượng từ Parcel
    protected Category(Parcel in) {
        categoryId = in.readInt();
        categoryName = in.readString();
    }

    // Tạo đối tượng Parcelable
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
