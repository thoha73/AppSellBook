package com.example.appsellbook.activities;

import android.os.Bundle;
import android.widget.ImageView;

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
        recyclerView = findViewById(R.id.rcv_bookItem);
        imageV_back = findViewById(R.id.imageV_back);
        bookAdapter = new BookAdapter(this);
//        recyclerView.setAdapter(bookAdapter);
        GridLayoutManager grv= new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(grv);
        bookAdapter.setData(getData());
        recyclerView.setAdapter(bookAdapter);
        imageV_back.setOnClickListener(v -> finish());


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