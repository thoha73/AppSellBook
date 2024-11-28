package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.DTOs.Author;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.adapter.WishlistAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.example.appsellbook.model.Book;
import com.example.appsellbook.model.Notification;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishList extends AppCompatActivity {
    private ListView listViewWishlist;
    private ArrayList<Book> wishlistItems;
    private WishlistAdapter wishlistAdapter;
    private ImageView img_back;
    private BottomNavigationView bottom_NavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        listViewWishlist = findViewById(R.id.listView_WishList);
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
        wishlistItems = new ArrayList<>();

        Intent intent = getIntent();
        String anh = intent.getStringExtra("anh");
        int image = intent.getIntExtra("image", 0);

        int image1 = intent.getIntExtra("image1", 0);
        String name = intent.getStringExtra("name3");
        String author = intent.getStringExtra("author3");
        if (name != null && author != null) {
                Book book = new Book(image, name, author);
                wishlistItems.add(book);


        }
        SessionManager sessionManager= new SessionManager(this);
        int userId = sessionManager.getUserId();
        if(userId!=-1){
            GetBook(userId);
        }

//        wishlistAdapter = new WishlistAdapter(this, wishlistItems);
//        listViewWishlist.setAdapter(wishlistAdapter);

        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
    private void GetBook(int userId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);

        String query = "query{\n" +
                "wishListForUser(userId: "+userId+"){\n" +
                "  bookName\n" +
                "  author{\n" +
                "    authorName\n" +
                "  }\n" +
                "  images{\n" +
                "    imageName\n" +
                "    imageData\n" +
                "  }\n" +
                "}\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        Log.d("GraphQL", "Request: " + request.getQuery());

        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        GraphQLResponse<Object> data = response.body();
                        Map<String, Object> responseData = (Map<String, Object>) data.getData();

                        Object wishListData = responseData.get("wishListForUser");
                        if (wishListData instanceof List) {
                            List<Map<String, Object>> wishList = (List<Map<String, Object>>) wishListData;
                            ArrayList<com.example.appsellbook.DTOs.Book> books = parseWishListData(wishList);

                            if (!books.isEmpty()) {
                                // Hiển thị danh sách sách
                                WishlistAdapter wishlistAdapter1= new WishlistAdapter(WishList.this,books);
                                listViewWishlist.setAdapter(wishlistAdapter1);
                            } else {
                                Log.d("GraphQL", "Danh sách sách trống.");
                            }
                        } else {
                            Log.e("GraphQL", "Cấu trúc dữ liệu trả về không đúng.");
                        }
                    } catch (ClassCastException e) {
                        Log.e("GraphQL Error", "Lỗi khi parse dữ liệu trả về: " + e.getMessage(), e);
                    }
                } else {
                    Log.e("GraphQL", "Lỗi truy vấn: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Lỗi kết nối hoặc xử lý: " + t.getMessage(), t);
            }
        });
    }

    // Hàm parse dữ liệu từ wishList thành danh sách Book
    private ArrayList<com.example.appsellbook.DTOs.Book> parseWishListData(List<Map<String, Object>> wishList) {
        ArrayList<com.example.appsellbook.DTOs.Book> books = new ArrayList<>();

        for (Map<String, Object> bookMap : wishList) {
            com.example.appsellbook.DTOs.Book book = new com.example.appsellbook.DTOs.Book();
            book.setBookName((String) bookMap.get("bookName"));
            if (bookMap.get("author") instanceof Map) {
                Map<String, Object> authorMap = (Map<String, Object>) bookMap.get("author");
                com.example.appsellbook.DTOs.Author author = new com.example.appsellbook.DTOs.Author();
                author.setAuthorName((String) authorMap.get("authorName"));
                book.setAuthor(author); // Gắn đối tượng Author vào Book
            } else {
                book.setAuthor(null); // Đặt null nếu không có thông tin author
            }

            if (bookMap.get("images") instanceof List) {
                List<Map<String, Object>> imagesList = (List<Map<String, Object>>) bookMap.get("images");
                List<com.example.appsellbook.DTOs.Image> images = new ArrayList<>();

                for (Map<String, Object> imageMap : imagesList) {
                    com.example.appsellbook.DTOs.Image image = new com.example.appsellbook.DTOs.Image();
                    image.setImageName((String) imageMap.get("imageName"));
                    image.setImageData((String) imageMap.get("imageData"));
                    images.add(image);
                }

                book.setImages(images);
            }
            books.add(book);
        }
        return books;
    }



}


