package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.model.Book;

import java.util.ArrayList;

public class ProductDetail  extends AppCompatActivity {
    private TextView tv_nameBook,tv_price,tv_author,tv_isbn,tv_description;
    private ImageView img,img_Home,img_Notification,img_Search,img_Setting,img_Profile;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
        img = findViewById(R.id.imageView1);
        tv_nameBook =findViewById(R.id.textViewNameProduct);
        tv_price = findViewById(R.id.textViewPriceProduct);
        tv_author= findViewById(R.id.textViewContentAuthor);
        tv_description = findViewById(R.id.textViewContentDescription);
        tv_isbn = findViewById(R.id.textViewContentISBN);
        img_Home = findViewById(R.id.imageView_Home);
        img_Notification = findViewById(R.id.imageView_Notification);

        Intent  intent = getIntent();
        int image = intent.getIntExtra("image",0);
        double price = intent.getDoubleExtra("price",0);
        String name = intent.getStringExtra("name");
        String author = intent.getStringExtra("author");
        String description = intent.getStringExtra("description");
        String ISBN = intent.getStringExtra("ISBN");
        img.setImageResource(image);
        tv_nameBook.setText(name);
        tv_price.setText(price+"");
        tv_author.setText(author);
        tv_description.setText(description);
        tv_isbn.setText(ISBN);

        Intent  intent1 = getIntent();
        int image1= intent.getIntExtra("image1",0);
        double price1 = intent.getDoubleExtra("price1",0);
        String name1 = intent.getStringExtra("name1");
        String author1 = intent.getStringExtra("author1");
        String description1 = intent.getStringExtra("description1");
        String ISBN1 = intent.getStringExtra("ISBN1");
        img.setImageResource(image1);
        tv_nameBook.setText(name1);
        tv_price.setText(price1+"");
        tv_author.setText(author1);
        tv_description.setText(description1);
        tv_isbn.setText(ISBN1);

        Intent  intent2 = getIntent();
        int image2= intent.getIntExtra("image2",0);
        double price2 = intent.getDoubleExtra("price2",0);
        String name2 = intent.getStringExtra("name2");
        String author2 = intent.getStringExtra("author2");
        String description2 = intent.getStringExtra("description2");
        String ISBN2 = intent.getStringExtra("ISBN2");
        img.setImageResource(image2);
        tv_nameBook.setText(name2);
        tv_price.setText(price2+"");
        tv_author.setText(author2);
        tv_description.setText(description2);
        tv_isbn.setText(ISBN2);

        img_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this,Home.class);
                
            }
        });
    }
}
