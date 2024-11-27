package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.BookAdapter;
import com.example.appsellbook.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NewProduct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ImageView imageV_back;
    BottomNavigationView bottom_NavigationView;
    List<Book> books;

    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageV_back = findViewById(R.id.imageV_back);
        recyclerView = findViewById(R.id.rcv_bookItem);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String json = sharedPreferences.getString("newbooks", null);
        Type type = new TypeToken<ArrayList<com.example.appsellbook.DTOs.Book>>() {}.getType();
        List<com.example.appsellbook.DTOs.Book> books = new Gson().fromJson(json, type);

// Truyền true để chỉ định đây là sản phẩm mới
        BookAdapter adapter = new BookAdapter(books, true);
        recyclerView.setAdapter(adapter);
//        recyclerView = findViewById(R.id.rcv_bookItem);
//        Intent intent = getIntent();
//        String newBookName = intent.getStringExtra("bookName");
//        String newBookImageUri = intent.getStringExtra("bookImageUri");
//
//        // Khởi tạo danh sách book và adapter
//        books = getData(); // Lấy danh sách dữ liệu ban đầu
//
//        if (newBookName != null && newBookImageUri != null) {
//            // Nếu có dữ liệu mới, thêm vào danh sách
//            books.add(new Book(Uri.parse(newBookImageUri), newBookName));
//        }
//        bookAdapter = new BookAdapter(this);
//        bookAdapter.setData(books);
//
//
//        bookAdapter = new BookAdapter(this);
////        recyclerView.setAdapter(bookAdapter);
//        GridLayoutManager grv= new GridLayoutManager(this,3);
//        recyclerView.setLayoutManager(grv);
//        bookAdapter.setData(books);
//        recyclerView.setAdapter(bookAdapter);
        imageV_back.setOnClickListener(v -> finish());


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
    }
//    private List<Book> getData(){
//        List<Book> list = new ArrayList<>();
//        list.add(new Book(R.drawable.book11,"English"));
//        list.add(new Book(R.drawable.book13,"Nhật ký của tôi"));
//        list.add(new Book(R.drawable.book6,"Mỗi lần vấp ngã là một lần trưởng thành"));
//        list.add(new Book(R.drawable.book12,"Thành phố phép màu"));
//        list.add(new Book(R.drawable.toikhongthichonao,"Tôi không thích ồn ào"));
//        list.add(new Book(R.drawable.tamlyhoc,"Tâm lí học"));
//        return list;
//    }
}