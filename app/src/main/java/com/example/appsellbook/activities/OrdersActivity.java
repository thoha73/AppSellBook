package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.OrdersAdapter;
import com.example.appsellbook.model.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

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
        LinearLayout llHome,llNotification,llSetting,llSearch,llProduct;

        llHome=findViewById(R.id.ll_home);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProduct=findViewById(R.id.ll_product);
        llHome.setOnClickListener(view -> {
            startActivity(new Intent(OrdersActivity.this,Home1.class));
        });
        llNotification.setOnClickListener(view -> {
            startActivity(new Intent(OrdersActivity.this,OwnerNotification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(OrdersActivity.this,Home1.class));

        });
        llSetting.setOnClickListener(view -> {
            startActivity(new Intent(OrdersActivity.this, ShopOwner.class));
        });
        llProduct.setOnClickListener(view -> {
            startActivity(new Intent(OrdersActivity.this,PostNewProduct.class));
        });
    }

}

