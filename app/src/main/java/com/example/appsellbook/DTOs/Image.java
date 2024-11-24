package com.example.appsellbook.DTOs;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private int imageId;
    private String imageName;
    private String imageData;
    private boolean icon;

    // Constructor
    public Image(int imageId, String imageName, String imageData, boolean icon) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageData = imageData;
        this.icon = icon;
    }

    // Constructor mặc định (cần thiết cho Parcelable)
    public Image() {
    }

    // Getter và Setter
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public boolean isIcon() {
        return icon;
    }

    public void setIcon(boolean icon) {
        this.icon = icon;
    }

    // Phương thức để ghi đối tượng vào Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageId);
        dest.writeString(imageName);
        dest.writeString(imageData);
        dest.writeByte((byte) (icon ? 1 : 0));
    }

    // Phương thức mô tả các nội dung của đối tượng
    @Override
    public int describeContents() {
        return 0;
    }

    // Constructor tạo đối tượng từ Parcel
    protected Image(Parcel in) {
        imageId = in.readInt();
        imageName = in.readString();
        imageData = in.readString();
        icon = in.readByte() != 0;
    }

    // Tạo đối tượng Parcelable
    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}