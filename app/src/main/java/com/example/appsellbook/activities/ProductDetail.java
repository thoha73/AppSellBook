package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductDetail  extends AppCompatActivity {
    private TextView tv_nameBook,tv_price,tv_author,tv_isbn,tv_description;
    private ImageView img,img_heart;
    private boolean isRedHeart = false;
    private Button btn_review;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
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
        btn_review = findViewById(R.id.btn_review);
        ImageView img_cart;
        img_cart = findViewById(R.id.imageView3);
        img_cart.setOnClickListener(view -> {
            startActivity(new Intent(ProductDetail.this, Cart.class));
        });
        Intent intent = getIntent();
        ImageView img_heart = findViewById(R.id.imageView2);
        img_heart.setOnClickListener(v -> {
            if (isRedHeart) {
                img_heart.setImageResource(R.drawable.heart);
            } else {
                img_heart.setImageResource(R.drawable.heart_red);
                Intent wishListIntent = new Intent(ProductDetail.this, WishList.class);
                wishListIntent.putExtra("image", getIntent().getIntExtra("image", 0)) ;

                wishListIntent.putExtra("image1", getIntent().getIntExtra("image1", 0));
                wishListIntent.putExtra("name3", tv_nameBook.getText().toString());
                wishListIntent.putExtra("author3", tv_author.getText().toString());
                startActivity(wishListIntent);
            }
            isRedHeart = !isRedHeart;
        });

        BottomNavigationView bottom_NavigationView;
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
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this, Review.class);
                int[] imageIds = {R.drawable.dacnhantam1, R.drawable.dacnhantam2, R.drawable.dacnhantam3};
                intent.putExtra("imageIds", imageIds);
                startActivity(intent);
            }
        });
        String source = intent.getStringExtra("source");
        if("grv1".equals(source)) {
            int image = intent.getIntExtra("image", 0);
            double price = intent.getDoubleExtra("price", 0);
            String name = intent.getStringExtra("name");
            String author = intent.getStringExtra("author");
            String description = intent.getStringExtra("description");
            String ISBN = intent.getStringExtra("ISBN");
            intent.putExtra("source1", "grv11");
            img.setImageResource(image);
            tv_nameBook.setText(name);
            tv_price.setText(price + "");
            tv_author.setText(author);
            tv_description.setText(description);
            tv_isbn.setText(ISBN);
        }else if("grv2".equals(source)){
            intent.putExtra("source1", "grv2");
            int image = intent.getIntExtra("image1", 0);
            double price = intent.getDoubleExtra("price1", 0);
            String name = intent.getStringExtra("name1");
            String author = intent.getStringExtra("author1");
            String description = intent.getStringExtra("description1");
            String ISBN = intent.getStringExtra("ISBN1");
            img.setImageResource(image);
            tv_nameBook.setText(name);
            tv_price.setText(price + "");
            tv_author.setText(author);
            tv_description.setText(description);
            tv_isbn.setText(ISBN);
        }else {
            int image = intent.getIntExtra("image2", 0);
            double price = intent.getDoubleExtra("price2", 0);
            String name = intent.getStringExtra("name2");
            String author = intent.getStringExtra("author2");
            String description = intent.getStringExtra("description2");
            String ISBN = intent.getStringExtra("ISBN2");
            img.setImageResource(image);
            tv_nameBook.setText(name);
            tv_price.setText(price + "");
            tv_author.setText(author);
            tv_description.setText(description);
            tv_isbn.setText(ISBN);
        }

//        Intent  intent1 = getIntent();
//        int image1= intent1.getIntExtra("image1",0);
//        double price1 = intent1.getDoubleExtra("price1",0);
//        String name1 = intent1.getStringExtra("name1");
//        String author1 = intent1.getStringExtra("author1");
//        String description1 = intent1.getStringExtra("description1");
//        String ISBN1 = intent1.getStringExtra("ISBN1");
//        img.setImageResource(image1);
//        tv_nameBook.setText(name1);
//        tv_price.setText(price1+"");
//        tv_author.setText(author1);
//        tv_description.setText(description1);
//        tv_isbn.setText(ISBN1);
//
//        Intent  intent2 = getIntent();
//        int image2= intent.getIntExtra("image2",0);
//        double price2 = intent.getDoubleExtra("price2",0);
//        String name2 = intent.getStringExtra("name2");
//        String author2 = intent.getStringExtra("author2");
//        String description2 = intent.getStringExtra("description2");
//        String ISBN2 = intent.getStringExtra("ISBN2");
//        img.setImageResource(image2);
//        tv_nameBook.setText(name2);
//        tv_price.setText(price2+"");
//        tv_author.setText(author2);
//        tv_description.setText(description2);
//        tv_isbn.setText(ISBN2);

    }
}
