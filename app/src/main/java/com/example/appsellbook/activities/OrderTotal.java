package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.CartsAdapter;
import com.example.appsellbook.model.Carts;

import java.util.ArrayList;
import java.util.List;

public class OrderTotal extends AppCompatActivity {
    int image[]={R.drawable.book1,R.drawable.chitietsp,R.drawable.book3,R.drawable.book9};
    String name[] = {"Sách động lực","Sách 3 ","Chữa Lành Trái Tim","Đắc Nhân Tâm"};
    LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
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
        CartsAdapter adapter = new CartsAdapter(this,R.layout.cart_item,cartList);
        listView.setAdapter(adapter);
        llHome=findViewById(R.id.ll_home);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProfile=findViewById(R.id.ll_profile);
        Button btnPurchase=findViewById(R.id.btn_purchase);
        llHome.setOnClickListener(view -> {

        });
        llNotification.setOnClickListener(view -> {
            startActivity(new Intent(OrderTotal.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(OrderTotal.this,Home.class));

        });
        llSetting.setOnClickListener(view -> {
           startActivity(new Intent(OrderTotal.this,Settings.class));
        });
        llProfile.setOnClickListener(view -> {
           startActivity(new Intent(OrderTotal.this,Profile.class));
        });
        btnPurchase.setOnClickListener(view -> {
            startActivity(new Intent(OrderTotal.this,OrderProcessed.class));
        });
    }
}
