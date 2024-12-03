package com.example.appsellbook.DTOs;

import android.os.Parcel;
import android.os.Parcelable;
import java.sql.Date;

public class Author implements Parcelable {
    private int authorId;
    private String authorName;
    private Date dateOfBirth;
    private String gender;

    // Constructor đầy đủ
    public Author(int authorId, String authorName, Date dateOfBirth, String gender) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    // Constructor mặc định
    public Author() {
    }

    // Getter và Setter
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(authorId);
        dest.writeString(authorName);
        dest.writeLong(dateOfBirth != null ? dateOfBirth.getTime() : -1);
        dest.writeString(gender);
    }

    // Constructor để đọc từ Parcel
    protected Author(Parcel in) {
        authorId = in.readInt();
        authorName = in.readString();
        long dobMillis = in.readLong();
        dateOfBirth = dobMillis != -1 ? new Date(dobMillis) : null;
        gender = in.readString();
    }

    // Creator
    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
    @Override
    public String toString() {
        return authorName; // Chỉ trả về tên tác giả
    }
}