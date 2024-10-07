package com.example.appsellbook.activities;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.GridView;
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

public class Popular extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ImageView imageV_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_popular);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.rcv_bookItem);
        imageV_back= findViewById(R.id.imageV_back);
        bookAdapter = new BookAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        bookAdapter.setData(getData());
        recyclerView.setAdapter(bookAdapter);
        imageV_back.setOnClickListener(view -> finish());

    }
    private List<Book> getData(){
        List<Book> list = new ArrayList<>();
        list.add(new Book(R.drawable.book9_2,"Đắc nhân tâm"));
        list.add(new Book(R.drawable.book8,"Tài chính căn bản"));
        list.add(new Book(R.drawable.book4,"Hai đứa trẻ"));
        list.add(new Book(R.drawable.book10,"Think & Grow Rich"));
        list.add(new Book(R.drawable.caycamngot,"Cây cam ngọt của tôi"));
        list.add(new Book(R.drawable.book7,"Chí phèo"));
        list.add(new Book(R.drawable.book14,"Đóa hoa mùa xuân"));
        list.add(new Book(R.drawable.nenkinhtetudo,"Nền kinh tế tự do"));
        list.add(new Book(R.drawable.kiuctuoithanhxuan,"Kí ức tuổi thanh xuân"));
        list.add(new Book(R.drawable.tamlihocdamdong,"Tâm lý học đám đông"));
        list.add(new Book(R.drawable.book18,"Sự tích chú cuội"));
        list.add(new Book(R.drawable.tuoitredanggiabn,"Tuổi trẻ đáng giá bao nhiêu"));
        list.add(new Book(R.drawable.book6,"Mỗi lần vấp ngã là một lần trưởng thành"));
        return list;
    }

}