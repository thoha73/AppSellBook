package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.DTOs.Book;
import com.example.appsellbook.DTOs.CartDetail;
import com.example.appsellbook.DTOs.Image;
import com.example.appsellbook.DTOs.User;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.adapter.CartAdapter;
import com.example.appsellbook.adapter.CartsAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.example.appsellbook.model.Carts;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {
    private ListView listViewCart;
    private CartAdapter cartAdapter;
    private ArrayList<CartDetail> listCart;
    private RecyclerView recyclerView;
    CheckBox selectall;
    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        Button btnOrder = findViewById(R.id.btn_order);
        recyclerView = findViewById(R.id.cart_list);
        selectall = findViewById(R.id.selectAllCheckBox);
        selectall.setOnClickListener(view -> {
        });
        btnOrder.setOnClickListener(v -> {
            Intent intent = new Intent(Cart.this,OrderTotal.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
        BottomNavigationView bottom_NavigationView;
        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                if (id == R.id.menu_notification) {
                    startActivity(new Intent(getApplicationContext(), Notification.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                if (id == R.id.menu_search) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                if (id == R.id.menu_setting) {
                    startActivity(new Intent(getApplicationContext(), Settings.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                if (id == R.id.menu_profile) {
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
        SessionManager section = new SessionManager(Cart.this);
        int userID = section.getUserId();
        if (userID != 0) {
            CartDetail(userID);
        }
    }
    private void CartDetail(int userId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query {\n" +
                "  cartDetail(userId: " + userId + ") {\n" +
                "    cartDetailId\n" +
                "    sellPrice\n" +
                "    quantity\n" +
                "    book {\n" +
                "      bookName\n" +
                "      images {\n" +
                "        imageName\n" +
                "        imageData\n" +
                "        icon\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    if (data.getData() instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                        Object cartDetails = dataMap.get("cartDetail");
                        if (cartDetails instanceof ArrayList) {
                            List<CartDetail> cartDetailList = new ArrayList<>();
                            ArrayList<?> cartDetailArray = (ArrayList<?>) cartDetails;
                            for (Object cartObj : cartDetailArray) {
                                if (cartObj instanceof LinkedTreeMap) {
                                    LinkedTreeMap<String, Object> cartMap = (LinkedTreeMap<String, Object>) cartObj;
                                    CartDetail cartDetail = new CartDetail();

                                    // Kiểm tra và gán giá trị sellPrice
                                    if (cartMap.get("sellPrice") instanceof Number) {
                                        cartDetail.setSellPrice(((Number) cartMap.get("sellPrice")).doubleValue());
                                    } else {
                                        Log.d("CartDebug", "sellPrice không hợp lệ hoặc bị null");
                                    }

                                    // Kiểm tra và gán giá trị quantity
                                    if (cartMap.get("quantity") instanceof Number) {
                                        cartDetail.setQuantity(((Number) cartMap.get("quantity")).intValue());
                                    } else {
                                        Log.d("CartDebug", "quantity không hợp lệ hoặc bị null");
                                    }

                                    // Lấy đối tượng book từ cartMap
                                    Object bookObj = cartMap.get("book");
                                    if (bookObj instanceof LinkedTreeMap) {
                                        LinkedTreeMap<String, Object> bookMap = (LinkedTreeMap<String, Object>) bookObj;
                                        Book book = new Book();

                                        // Gán bookName cho book
                                        if (bookMap.get("bookName") instanceof String) {
                                            book.setBookName((String) bookMap.get("bookName"));
                                        }

                                        // Lấy và gán images cho book
                                        if (bookMap.get("images") instanceof List) {
                                            List<Map<String, Object>> imagesList = (List<Map<String, Object>>) bookMap.get("images");
                                            List<Image> images = new ArrayList<>();
                                            for (Map<String, Object> imageMap : imagesList) {
                                                Image image = new Image();
                                                if (imageMap.get("imageData") instanceof String) {
                                                    image.setImageData((String) imageMap.get("imageData"));
                                                }
                                                images.add(image);
                                            }
                                            book.setImages(images);
                                        }

                                        // Gán book cho cartDetail
                                        cartDetail.setBook(book);
                                    }

                                    // Thêm cartDetail vào danh sách
                                    cartDetailList.add(cartDetail);
                                }
                            }
                            // Cập nhật RecyclerView
                            recyclerView.setLayoutManager(new LinearLayoutManager(Cart.this));
                            CartAdapter adapter = new CartAdapter(Cart.this, new ArrayList<>(cartDetailList));
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("Error", "cartDetail không phải là danh sách");
                        }
                    } else {
                        Log.e("Error", "Dữ liệu phản hồi không đúng định dạng: " + data.getData());
                    }
                } else {
                    Log.e("Error", "Phản hồi thất bại: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Lỗi khi tải dữ liệu", t);
            }
        });
    }

}