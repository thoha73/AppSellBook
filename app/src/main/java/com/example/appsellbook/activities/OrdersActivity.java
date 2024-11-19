package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.OrdersAdapter;
import com.example.appsellbook.model.Orders;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);

        ListView listView = findViewById(R.id.orders_list);


        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(new Orders(R.drawable.img_orderer, "Jenny Huynh", "How to Win Friends and Influence People (Tổng sản phẩm: 1)"));
        ordersList.add(new Orders(R.drawable.img_order1, "John Doe", " The Power of Habit (Tổng sản phẩm: 2)"));
        ordersList.add(new Orders(R.drawable.img_order2, "Alice Smith", "Clean Code (Tổng sản phẩm: 3)"));
        ordersList.add(new Orders(R.drawable.img_order3, "Bob Johnson", "Atomic Habits (Tổng sản phẩm: 1)"));
        ordersList.add(new Orders(R.drawable.img_order4, "Emma Brown", "The Pragmatic Programmer (Tổng sản phẩm: 4)"));
        ordersList.add(new Orders(R.drawable.img_orderer, "Lucas Green", "You Don't Know JS (Tổng sản phẩm: 1)"));


        OrdersAdapter ordersAdapter = new OrdersAdapter(this, R.layout.order_item, ordersList);
        listView.setAdapter(ordersAdapter);

        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
        BottomNavigationView bottom_NavigationView2;
        bottom_NavigationView2 = findViewById(R.id.bottom_navigation2);
        bottom_NavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_home){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_notification){
                    startActivity(new Intent(getApplicationContext(), OwnerNotification.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
                    startActivity(new Intent(getApplicationContext(), ShopOwner.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_product){
                    startActivity(new Intent(getApplicationContext(), PostNewProduct.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                return false;
            }
        });
    }

}

