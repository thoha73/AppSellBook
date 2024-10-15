package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.CartsAdapter;
import com.example.appsellbook.model.Carts;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
        Button buttonOrder=findViewById(R.id.btn_order);
        llHome=findViewById(R.id.ll_home);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProfile=findViewById(R.id.ll_profile);
        ListView listView = findViewById(R.id.cart_list);
        List<Carts> cartList = new ArrayList<>();
        cartList.add(new Carts(R.drawable.img_chualanhtraitim, "Chữa lành trái tim", "100.000 đ"));
        cartList.add(new Carts(R.drawable.img_dacnhantam, "Đắc nhân tâm", "100.000 đ"));
        cartList.add(new Carts(R.drawable.img_thirdpronuncitionofhistory, "Third pronuncition of history", "200.000 đ"));
        cartList.add(new Carts(R.drawable.img_sucmanhtiemthuc, "Sức mạnh tiềm thức (Tái bản năm 2021)", "100.000 đ"));
        CartsAdapter adapter = new CartsAdapter(this, R.layout.cart_item, cartList);
        listView.setAdapter(adapter);
        llHome.setOnClickListener(view -> {
            startActivity(new Intent(Cart.this,Home.class));
        });
        llNotification.setOnClickListener(view -> {
            startActivity(new Intent(Cart.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(Cart.this,Home.class));

        });
        llSetting.setOnClickListener(view -> {
            startActivity(new Intent(Cart.this,Settings.class));
        });
        llProfile.setOnClickListener(view -> {
            startActivity(new Intent(Cart.this,Profile.class));
        });
        buttonOrder.setOnClickListener(view -> {
            startActivity(new Intent(Cart.this,OrderTotal.class));
        });

    }
}