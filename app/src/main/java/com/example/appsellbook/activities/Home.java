package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.BookArrayAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.example.appsellbook.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    GridView gridview1,gridview2,gridview3;
    TextView tv_new,tv_popular,tv_category;
    ArrayList<Book> listBook,listBook1,listBook2;
    BottomNavigationView bottom_NavigationView;
    BookArrayAdapter myAdapter;

    @SuppressLint("MissinginFlatedID")
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
        bottom_NavigationView = findViewById(R.id.bottom_navigation);

        tv_category.setOnClickListener(view -> {
            startActivity(new Intent(Home.this,AllCategories.class));
        });
        tv_popular.setOnClickListener(view -> {
            startActivity(new Intent(Home.this,Popular.class));
        });
        tv_new.setOnClickListener(view -> {
            startActivity(new Intent(Home.this,NewProduct.class));
        });
        bottom_NavigationView.setSelectedItemId(R.id.menu_home);
        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
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
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " query{\n" +
                "    books{\n" +
                "        bookId\n" +
                "        bookName\n" +
                "        isbn\n" +
                "        listedPrice\n" +
                "        sellPrice\n" +
                "        quantity\n" +
                "        description\n" +
                "        rank\n" +
                "        images{\n" +
                "            imageId\n" +
                "            imageName\n" +
                "            imageData\n" +
                "            icon\n" +
                "        }\n" +
                "        \n" +
                "    }\n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);
        Log.d("GraphQL", "Request: " + request.getQuery());
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    if (data != null && data.getData() instanceof Map) {
                        Map<String, Object> responseData = (Map<String, Object>) data.getData();
                        Object booksData = responseData.get("books");
                        if (booksData instanceof List) {
                            List<Map<String, Object>> booksList = (List<Map<String, Object>>) booksData;

                            List<com.example.appsellbook.DTOs.Book> books = new ArrayList<>();
                            for (Map<String, Object> bookMap : booksList) {
                                com.example.appsellbook.DTOs.Book book = new com.example.appsellbook.DTOs.Book();

                                Object bookId = bookMap.get("bookId");
                                if (bookId instanceof Number) {
                                    book.setBookId(((Number) bookId).intValue());
                                }

                                book.setBookName((String) bookMap.get("bookName"));
                                book.setDescription((String) bookMap.get("description"));
                                if (bookMap.get("images") instanceof List) {
                                    List<Map<String, Object>> imagesList = (List<Map<String, Object>>) bookMap.get("images");
                                    List<com.example.appsellbook.DTOs.Image> images = new ArrayList<>();
                                    for (Map<String, Object> imageMap : imagesList) {
                                        com.example.appsellbook.DTOs.Image image = new com.example.appsellbook.DTOs.Image();
                                        image.setImageData((String) imageMap.get("imageData"));
                                        images.add(image);
                                    }
                                    book.setImages(images);
                                }
                                books.add(book);
                            }

                            if (!books.isEmpty() && books!= null) {
                                initGridView1(books, gridview1);
                                gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        Log.d("GridView", "Item clicked: " + position);
                                        com.example.appsellbook.DTOs.Book selectedBook = books.get(position);
                                        if (selectedBook != null) {
                                            Intent intent = new Intent(Home.this, ProductDetail.class);
                                            intent.putExtra("BookId", selectedBook.getBookId());
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } else {
                                Log.d("GraphQL", "Danh sách sách không có dữ liệu.");
                            }
                        } else {
                            Log.d("GraphQL", "Không thể lấy danh sách sách từ dữ liệu trả về.");
                        }
                    } else {
                        Log.d("GraphQL", "Dữ liệu trả về không phải là Map.");
                    }
                } else {
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", t.getMessage());
            }
        });

    }
    private void initGridView1(List<com.example.appsellbook.DTOs.Book> list,GridView grv){
        grv.setPadding(10,10,10,20);
        ArrayList<com.example.appsellbook.DTOs.Book> books=new ArrayList<com.example.appsellbook.DTOs.Book>(list);
        myAdapter = new BookArrayAdapter(this,R.layout.layout_item_book,books);
        grv.setAdapter(myAdapter);
    }


}