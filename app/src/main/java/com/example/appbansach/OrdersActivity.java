package com.example.appbansach;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}

