package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
        ordersList.add(new Orders(R.drawable.img_orderer, "Orderer:Jenny Huynh", "Order content:How to Win Friends and Influence People (Total number of products: 1)"));
        ordersList.add(new Orders(R.drawable.img_order1, "Orderer:John Doe", "Order content:The Power of Habit (Total number of products: 2)"));
        ordersList.add(new Orders(R.drawable.img_order2, "Orderer:Alice Smith", "Order content:Clean Code (Total number of products: 3)"));
        ordersList.add(new Orders(R.drawable.img_order3, "Orderer:Bob Johnson", "Order content:Atomic Habits (Total number of products: 1)"));
        ordersList.add(new Orders(R.drawable.img_order4, "Orderer:Emma Brown", "Order content:The Pragmatic Programmer (Total number of products: 4)"));
        ordersList.add(new Orders(R.drawable.img_orderer, "Orderer:Lucas Green", "Order content:You Don't Know JS (Total number of products: 1)"));


        OrdersAdapter ordersAdapter = new OrdersAdapter(this, R.layout.order_item, ordersList);
        listView.setAdapter(ordersAdapter);

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

