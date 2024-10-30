package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.OrderAdapter;
import com.example.appsellbook.model.Book;
import com.example.appsellbook.model.Order;
import com.example.appsellbook.model.OrderDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private ListView lv_OrderHistory;
    BottomNavigationView bottom_NavigationView;
    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());

        lv_OrderHistory = findViewById(R.id.lv_OrderHistory);
        Book book1= new Book(1,R.drawable.book1,"Sách động lực");
        Book book2= new Book(2,R.drawable.book3,"Sách động lực");
        Book book3= new Book(3,R.drawable.book4,"Sách động lực");
        Book book4= new Book(4,R.drawable.book5,"Sách động lực");
        OrderDetails orderDetails1= new OrderDetails(1,book1,1,260000,null);
        OrderDetails orderDetails2= new OrderDetails(2,book2,2,100000,null);
        OrderDetails orderDetails3= new OrderDetails(3,book3,1,210000,null);
        OrderDetails orderDetails4= new OrderDetails(4,book4,1,160000,null);
        List<OrderDetails> orderDetailsList1= new ArrayList<>();
        orderDetailsList1.add(orderDetails1);
        List<OrderDetails> orderDetailsList2= new ArrayList<>();
        orderDetailsList2.add(orderDetails2);
        List<OrderDetails> orderDetailsList3= new ArrayList<>();
        orderDetailsList3.add(orderDetails3);
        List<OrderDetails> orderDetailsList4= new ArrayList<>();
        orderDetailsList4.add(orderDetails4);
        Order order1= new Order(1,null,0,null,"Đã giao",null,orderDetailsList1);
        Order order2= new Order(2,null,0,null,"Đã giao",null,orderDetailsList2);
        Order order3= new Order(3,null,0,null,"Đã giao",null,orderDetailsList3);
        Order order4= new Order(4,null,0,null,"Đã giao",null,orderDetailsList4);
        ArrayList<Order> listOrder= new ArrayList<>();
        listOrder.add(order1);
        listOrder.add(order2);
        listOrder.add(order3);
        listOrder.add(order4);
        OrderAdapter orderAdapter= new OrderAdapter(this,R.layout.layout_item_history,listOrder);
        lv_OrderHistory.setAdapter(orderAdapter);


        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_home){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_notification){
                    startActivity(new Intent(getApplicationContext(), Notification.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
                    startActivity(new Intent(getApplicationContext(), Settings.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_profile){
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                return false;
            }
        });

//        lv_OrderHistory.setOnItemClickListener((adapterView, view, i, l) -> {
//            Order order= listOrder.get(i);
//            OrderDetails orderDetails=order.getOrderDetails().get(0);
//            Book book= orderDetails.getBook();
//            Intent intent= new Intent(History.this,Feedback.class);
//            intent.putExtra("bookId",book.getBookId());
//            intent.putExtra("image",book.getImage());
//            intent.putExtra("bookName",book.getBookName());
//            startActivity(intent);
//        });
    }
}