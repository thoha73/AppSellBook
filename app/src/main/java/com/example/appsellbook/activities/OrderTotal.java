package com.example.appsellbook.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;

public class OrderTotal extends AppCompatActivity {
    int image[]={R.drawable.book1,R.drawable.chitietsp,R.drawable.book3,R.drawable.book9};
    String name[] = {"Sách động lực","Sách 3 ","Chữa Lành Trái Tim","Đắc Nhân Tâm"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_total);

    }
}
