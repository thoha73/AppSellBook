package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.DTOs.Category;
import com.example.appsellbook.R;
import com.example.appsellbook.adapter.BookAdapter;
import com.example.appsellbook.adapter.CategoryAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategories extends AppCompatActivity {
    private ImageView imageV_back;
    private RecyclerView recyclerViewBooks;
    private CategoryAdapter categoryAdapter;
    private List<com.example.appsellbook.DTOs.Book> books = new ArrayList<>();

    @SuppressLint("MissinginFlatedID")
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

        imageV_back = findViewById(R.id.imageV_back);
        imageV_back.setOnClickListener(view -> finish());
        recyclerViewBooks = findViewById(R.id.rcv_bookItem);
        recyclerViewBooks.setLayoutManager(new GridLayoutManager(this, 3)); // Set to 3 columns
        categoryAdapter = new CategoryAdapter(this, books);
        recyclerViewBooks.setAdapter(categoryAdapter);

        fetchBooks();
//
//        BottomNavigationView bottom_NavigationView;
//        bottom_NavigationView = findViewById(R.id.bottom_navigation);
//        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                if (id == R.id.menu_home) {
//                    startActivity(new Intent(getApplicationContext(), Home.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                }
//                if (id == R.id.menu_notification) {
//                    startActivity(new Intent(getApplicationContext(), Notification.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                }
//                if (id == R.id.menu_search) {
//                    startActivity(new Intent(getApplicationContext(), Home.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                }
//                if (id == R.id.menu_setting) {
//                    startActivity(new Intent(getApplicationContext(), Settings.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                }
//                if (id == R.id.menu_profile) {
//                    startActivity(new Intent(getApplicationContext(), Profile.class));
//                    overridePendingTransition(0, 0);
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    private void fetchBooks() {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query{\n" +
                "  books{\n" +
                "    bookName\n" +
                "    categories{\n" +
                "      categoryName\n" +
                "    }\n" +
                "    images{\n" +
                "      imageData\n" +
                "      imageName\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);
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

                            Map<String, com.example.appsellbook.DTOs.Book> categoryBookMap = new HashMap<>();
                            for (Map<String, Object> bookMap : booksList) {
                                com.example.appsellbook.DTOs.Book book = new com.example.appsellbook.DTOs.Book();
                                book.setBookName((String) bookMap.get("bookName"));
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
                                if (bookMap.get("categories") instanceof List) {
                                    List<Map<String, Object>> categoryList = (List<Map<String, Object>>) bookMap.get("categories");
                                    for (Map<String, Object> categoryMap : categoryList) {
                                        String categoryName = (String) categoryMap.get("categoryName");
                                        if (!categoryBookMap.containsKey(categoryName)) {
                                            Category category = new Category();
                                            category.setCategoryName(categoryName);
                                            book.setCategories(Collections.singletonList(category));
                                            categoryBookMap.put(categoryName, book);
                                        }
                                    }
                                }
                            }
                            books.clear();
                            books.addAll(categoryBookMap.values());
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("GraphQL", "Danh sách sách không có dữ liệu.");
                        }
                    } else {
                        Log.d("GraphQL", "Không thể lấy danh sách sách từ dữ liệu trả về.");
                    }
                } else {
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}