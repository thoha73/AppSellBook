package com.example.appsellbook.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.BookAdapter;
import com.example.appsellbook.model.Book;

import java.util.ArrayList;
import java.util.List;

public class NewProduct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ImageView imageV_back;
    List<Book> books;

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
        LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
        llHome=findViewById(R.id.ll_home);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProfile=findViewById(R.id.ll_profile);
        recyclerView = findViewById(R.id.rcv_bookItem);
        Intent intent = getIntent();
        String newBookName = intent.getStringExtra("bookName");
        String newBookImageUri = intent.getStringExtra("bookImageUri");

        // Khởi tạo danh sách book và adapter
        books = getData(); // Lấy danh sách dữ liệu ban đầu

        if (newBookName != null && newBookImageUri != null) {
            // Nếu có dữ liệu mới, thêm vào danh sách
            books.add(new Book(Uri.parse(newBookImageUri), newBookName));
        }
        bookAdapter = new BookAdapter(this);
        bookAdapter.setData(books);

        imageV_back = findViewById(R.id.imageV_back);
        bookAdapter = new BookAdapter(this);
//        recyclerView.setAdapter(bookAdapter);
        GridLayoutManager grv= new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(grv);
        bookAdapter.setData(books);
        recyclerView.setAdapter(bookAdapter);
        imageV_back.setOnClickListener(v -> finish());

        llHome.setOnClickListener(view -> {
            startActivity(new Intent(NewProduct.this,Home.class));
        });
        llNotification.setOnClickListener(view -> {
            startActivity(new Intent(NewProduct.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(NewProduct.this,Home.class));

        });
        llSetting.setOnClickListener(view -> {
//           startActivity(new Intent(Home.this,Setting.class));
        });
        llProfile.setOnClickListener(view -> {
//           startActivity(new Intent(Home.this,Profile.class));
        });

    }
    private List<Book> getData(){
        List<Book> list = new ArrayList<>();
        list.add(new Book(R.drawable.book11,"English"));
        list.add(new Book(R.drawable.book13,"Nhật ký của tôi"));
        list.add(new Book(R.drawable.book6,"Mỗi lần vấp ngã là một lần trưởng thành"));
        list.add(new Book(R.drawable.book12,"Thành phố phép màu"));
        list.add(new Book(R.drawable.toikhongthichonao,"Tôi không thích ồn ào"));
        list.add(new Book(R.drawable.tamlyhoc,"Tâm lí học"));
        return list;
    }
}