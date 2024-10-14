package com.example.appsellbook.model;

public class Orders {
    private int img;
    private String orderer;
    private String content;

    public Orders(int img, String orderer, String content) {
        this.img = img;
        this.orderer = orderer;
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderer='" + orderer + '\'' +
                ", content='" + content + '\'' +
                ", img=" + img +
                '}';
    }
}
