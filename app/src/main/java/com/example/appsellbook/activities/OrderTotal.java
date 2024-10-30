package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.CartsAdapter;
import com.example.appsellbook.model.Carts;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OrderTotal extends AppCompatActivity {
    int image[]={R.drawable.book1,R.drawable.chitietsp,R.drawable.book3,R.drawable.book9};
    String name[] = {"Sách động lực","Sách 3 ","Chữa Lành Trái Tim","Đắc Nhân Tâm"};
    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_total);
        ListView listView = findViewById(R.id.listview_ordertotal);
        List<Carts> cartList = new ArrayList<>();
        cartList.add(new Carts(R.drawable.img_chualanhtraitim, "Chữa lành trái tim", "100.000 đ"));
        cartList.add(new Carts(R.drawable.img_dacnhantam, "Đắc nhân tâm", "100.000 đ"));
        cartList.add(new Carts(R.drawable.img_thirdpronuncitionofhistory, "Third pronuncition of history", "200.000 đ"));
        cartList.add(new Carts(R.drawable.img_sucmanhtiemthuc, "Sức mạnh tiềm thức (Tái bản năm 2021)", "100.000 đ"));
        CartsAdapter adapter = new CartsAdapter(this, R.layout.cart_item, cartList);
        listView.setAdapter(adapter);
        Button btnPurchase = findViewById(R.id.btn_purchase);
        btnPurchase.setOnClickListener(v -> {
            Intent intent = new Intent(OrderTotal.this,ChangeSuccess.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
    }
}
