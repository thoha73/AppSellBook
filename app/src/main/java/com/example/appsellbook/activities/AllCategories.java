package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.BookAdapter;
import com.example.appsellbook.adapter.BookArrayAdapter;
import com.example.appsellbook.model.Book;

import java.util.ArrayList;
import java.util.List;

public class AllCategories extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ImageView imageV_back;
    int image[]={R.drawable.book1,R.drawable.book3,R.drawable.book4,
                 R.drawable.book11,R.drawable.book6,R.drawable.book7,
                 R.drawable.book14,R.drawable.book15,R.drawable.book16,
                 R.drawable.book17,R.drawable.book18,R.drawable.book19};
    String nameBook[]={"Sách động lực","Sách hư cấu","Sách tiểu thuyết",
                      "Sách tiếng anh","Sách tự lực","Sách văn học",
                      "Sách truyện tranh","Sách tham khảo, sách khoa học",
                      "Sách giáo khoa","Sách truyện cổ tích","Sách lập trình"};
   // int image[]={R.drawable.book11,R.drawable.book13,R.drawable.book6,R.drawable.book12};
  //  String nameBook[]={"English","Nhật ký của tôi","Mỗi lần vấp ngã là mội lần trưởng thành","Thành phố phép màu"};
    GridView gridview1;
    ArrayList<Book> listBook;
    BookArrayAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_categories);
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
        imageV_back = findViewById(R.id.imageV_back);
        bookAdapter = new BookAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        bookAdapter.setData(getData());
        recyclerView.setAdapter(bookAdapter);
        imageV_back.setOnClickListener(view -> finish());
        llHome.setOnClickListener(view -> {
            startActivity(new Intent(AllCategories.this,Home.class));
        });
        llNotification.setOnClickListener(view -> {
            startActivity(new Intent(AllCategories.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(AllCategories.this,Home.class));

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
        list.add(new Book(R.drawable.book1,"Sách động lực"));
        list.add(new Book(R.drawable.book3,"Sách hư cấu"));
        list.add(new Book(R.drawable.book4,"Sách tiểu thuyết"));
        list.add(new Book(R.drawable.book11,"Sách tiếng anh"));
        list.add(new Book(R.drawable.book6,"Sách tự lực"));
        list.add(new Book(R.drawable.book7,"Sách văn học"));
        list.add(new Book(R.drawable.book14,"Sách truyện tranh"));
        list.add(new Book(R.drawable.book15,"Sách tham khảo"));
        list.add(new Book(R.drawable.book16,"Sách khoa học"));
        list.add(new Book(R.drawable.book17,"Sách giáo khoa"));
        list.add(new Book(R.drawable.book18,"Sách truyện cổ tích"));
        list.add(new Book(R.drawable.book19,"Sách lập trình"));
        return list;
    }

}

