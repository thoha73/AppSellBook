package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.appsellbook.DTOs.Category;
import com.example.appsellbook.DTOs.Image;
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

public class Home extends BaseActivity {

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
                "    books(order: [ {\n" +
                "       bookId: DESC\n" +
                "    }]){\n" +
                "        bookId\n" +
                "        bookName\n" +
                "        isbn\n" +
                "        listedPrice\n" +
                "        sellPrice\n" +
                "        quantity\n" +
                "        description\n" +
                "        categories{\n" +
                "          categoryId\n" +
                "          categoryName\n" +
                "        }\n" +
                "        author{\n" +
                "          authorId\n" +
                "          authorName\n" +
                "        }\n" +
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
                                if (bookMap.get("categories") instanceof List){
                                    List<Map<String,Object>> categoryList =(List<Map<String, Object>>) bookMap.get("categories");
                                    List<Category> categories = new ArrayList<>();
                                    for (Map<String,Object> categoryMap : categoryList){
                                        Category category = new Category();
                                        Object categoryId = categoryMap.get("categoryId");
                                        if (categoryId instanceof Number) {
                                            category.setCategoryId(((Number) categoryId).intValue());
                                        }
                                        category.setCategoryName((String) categoryMap.get("categoryName"));
                                        categories.add(category);
                                    }
                                    book.setCategories(categories);
                                }
                                books.add(book);
                            }
                            tv_popular.setOnClickListener(view -> {
                                startActivity(new Intent(Home.this,Popular.class));
                            });
                            tv_new.setOnClickListener(view -> {
                                startActivity(new Intent(Home.this,NewProduct.class));
                            });
                            List<Book> books1List = new ArrayList<>();

                            tv_category.setOnClickListener(view -> {
                                Intent intent = new Intent(Home.this, AllCategories.class);
                                startActivity(intent);
                            });
                            if (!books.isEmpty() && books!= null) {
                                initGridView1(books, gridview1,gridview2,gridview3);
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

    private void initGridView1(List<com.example.appsellbook.DTOs.Book> list, GridView gridview1, GridView gridview2,GridView gridview3) {
        // Lấy ba quyển sách cuối cùng cho gridview1
        List<com.example.appsellbook.DTOs.Book> lastThreeBooks = list.subList(0, Math.min(3, list.size())) ;
        // Lấy ba quyển sách đầu tiên cho gridview2
        List<com.example.appsellbook.DTOs.Book> firstThreeBooks = list.subList(Math.max(list.size() - 3, 0), list.size());

        // Khởi tạo gridview1 với ba quyển sách cuối cùng
        gridview1.setPadding(10, 10, 10, 20);
        ArrayList<com.example.appsellbook.DTOs.Book> booksForGridView1 = new ArrayList<>(lastThreeBooks);
        BookArrayAdapter adapter1 = new BookArrayAdapter(Home.this, R.layout.layout_item_book, booksForGridView1,false);
        gridview1.setAdapter(adapter1);

        // Khởi tạo gridview2 với ba quyển sách đầu tiên
        gridview2.setPadding(10, 10, 10, 20);
        ArrayList<com.example.appsellbook.DTOs.Book> booksForGridView2 = new ArrayList<>(firstThreeBooks);
        BookArrayAdapter adapter2 = new BookArrayAdapter(Home.this, R.layout.layout_item_book, booksForGridView2,false);
        gridview2.setAdapter(adapter2);

        int size = list.size();
        int startIndex = (size - 3) / 2; // Tính chỉ số bắt đầu
        int endIndex = startIndex + 3; // Tính chỉ số kết thúc

        // Nếu danh sách có ít hơn 3 sách, sử dụng toàn bộ danh sách
        List<com.example.appsellbook.DTOs.Book> middleThreeBooks;
        if (size < 3) {
            middleThreeBooks = new ArrayList<>(list);
        } else {
            middleThreeBooks = list.subList(startIndex, endIndex);
        }
        gridview3.setPadding(10, 10, 10, 20);
        ArrayList<com.example.appsellbook.DTOs.Book> booksForGridView3 = new ArrayList<>(middleThreeBooks);
        BookArrayAdapter adapter3 = new BookArrayAdapter(Home.this, R.layout.layout_item_book, booksForGridView3,true);
        gridview3.setAdapter(adapter3);



        // Thiết lập bộ xử lý sự kiện nhấp chuột cho gridview1
        gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("GridView1", "Item clicked: " + position);
                com.example.appsellbook.DTOs.Book selectedBook = booksForGridView1.get(position);
                if (selectedBook != null) {
                    Intent intent = new Intent(Home.this, ProductDetail.class);
                    intent.putExtra("BookId", selectedBook.getBookId());
                    startActivity(intent);
                }
            }
        });

        // Thiết lập bộ xử lý sự kiện nhấp chuột cho gridview2
        gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("GridView2", "Item clicked: " + position);
                com.example.appsellbook.DTOs.Book selectedBook = booksForGridView2.get(position);
                if (selectedBook != null) {
                    Intent intent = new Intent(Home.this, ProductDetail.class);
                    intent.putExtra("BookId", selectedBook.getBookId());
                    startActivity(intent);
                }
            }
        });
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        checkSessionTimeout();
//    }
//    private void updateSessionStartTime() {
//        long currentTimeMillis = System.currentTimeMillis();
//        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putLong("session_start_time", currentTimeMillis); // Cập nhật thời gian bắt đầu
//        editor.apply();
//    }
//    private void checkSessionTimeout() {
//        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
//        long sessionStartTime = preferences.getLong("session_start_time", -1);
//
//        if (sessionStartTime != -1) {
//            long currentTimeMillis = System.currentTimeMillis();
//            long sessionDuration = currentTimeMillis - sessionStartTime;
//            long maxSessionDuration = 5 * 60 * 1000; // 5 phút
//
//            if (sessionDuration < maxSessionDuration) {
//                // Nếu session còn hiệu lực, kéo dài thời gian session
//                updateSessionStartTime();
//            } else {
//                // Nếu session hết hạn, yêu cầu người dùng đăng nhập lại
//                new AlertDialog.Builder(this)
//                        .setTitle("Phiên làm việc hết hạn")
//                        .setMessage("Phiên làm việc của bạn đã hết hạn. Vui lòng đăng nhập lại.")
//                        .setPositiveButton("OK", (dialog, which) -> {
//                            dialog.dismiss();
//                            // Xử lý đăng nhập lại (chẳng hạn như mở lại màn hình đăng nhập)
//                            startActivity(new Intent(Home.this, Login.class));
//                            finish(); // Đóng activity hiện tại
//                        })
//                        .show();
//            }
//        }
//    }
    }



