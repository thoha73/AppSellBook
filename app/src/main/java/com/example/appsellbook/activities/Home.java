package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.BookArrayAdapter;
import com.example.appsellbook.model.Book;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    int image[]={R.drawable.book11,R.drawable.book13,R.drawable.book6,R.drawable.book12};
    String nameBook[]={"English","Nhật ký của tôi","Mỗi lần vấp ngã là mội lần trưởng thành","Thành phố phép màu"};
    int image1[]={R.drawable.book9,R.drawable.book7,R.drawable.book8,R.drawable.book10};
    String nameBook1[]={"Đắc nhân tâm","Chí phèo","Tài chính","Think & grow rich"};
    int image2[]={R.drawable.book1,R.drawable.book3,R.drawable.book4,R.drawable.book5};
    String nameBook2[]={"Sách động lực","Sách hư cấu","Sách tiểu thuyết","Sách kinh tế"};
    GridView gridview1,gridview2,gridview3;
    TextView tv_new,tv_popular,tv_category;
    ArrayList<Book> listBook,listBook1,listBook2;
    BookArrayAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tv_new=findViewById(R.id.tv_showallNew);
        tv_popular=findViewById(R.id.tv_showallPopular);
        tv_category=findViewById(R.id.tv_showallCategory);
        gridview1=findViewById(R.id.gridview1);
        gridview2=findViewById(R.id.gridview2);
        gridview3=findViewById(R.id.gridview3);
        initGridView(listBook,image,nameBook,gridview1);
        initGridView(listBook1,image1,nameBook1,gridview2);
        initGridView(listBook2,image2,nameBook2,gridview3);
        tv_category.setOnClickListener(view -> {
            startActivity(new Intent(Home.this,AllCategories.class));
        });
        tv_popular.setOnClickListener(view -> {
            startActivity(new Intent(Home.this,Popular.class));
        });
        tv_new.setOnClickListener(view -> {
            startActivity(new Intent(Home.this,NewProduct.class));
        });



    }
    private void initGridView(ArrayList<Book> list,int img[],String nameBook[],GridView grv){
        grv.setPadding(10,10,10,20);
        list=new ArrayList<>();
        for(int i=0;i<img.length;i++){
            list.add(new Book(img[i],nameBook[i]));
        }
        myAdapter = new BookArrayAdapter(this,R.layout.layout_item_book,list);
        grv.setAdapter(myAdapter);
    }
}