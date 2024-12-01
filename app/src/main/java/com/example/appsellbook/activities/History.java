package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.example.appsellbook.DTOs.Image;
import com.example.appsellbook.DTOs.OrderDetails;
import com.example.appsellbook.DTOs.User;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.adapter.OrderAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.example.appsellbook.model.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends AppCompatActivity {
    private ListView lv_OrderHistory;
    BottomNavigationView bottom_NavigationView;
    RecyclerView rcv_history;
    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SessionManager section = new SessionManager(this);
        int userID = section.getUserId();
        if (userID != 0) {
            HistoryById(userID);
        }
        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
        rcv_history = findViewById(R.id.rcv_history);
//        lv_OrderHistory = findViewById(R.id.lv_OrderHistory);
//        Book book1= new Book(1,R.drawable.book1,"Sách động lực");
//        Book book2= new Book(2,R.drawable.book3,"Sách động lực");
//        Book book3= new Book(3,R.drawable.book4,"Sách động lực");
//        Book book4= new Book(4,R.drawable.book5,"Sách động lực");
//        OrderDetails orderDetails1= new OrderDetails(1,book1,1,260000,null);
//        OrderDetails orderDetails2= new OrderDetails(2,book2,2,100000,null);
//        OrderDetails orderDetails3= new OrderDetails(3,book3,1,210000,null);
//        OrderDetails orderDetails4= new OrderDetails(4,book4,1,160000,null);
//        List<OrderDetails> orderDetailsList1= new ArrayList<>();
//        orderDetailsList1.add(orderDetails1);
//        List<OrderDetails> orderDetailsList2= new ArrayList<>();
//        orderDetailsList2.add(orderDetails2);
//        List<OrderDetails> orderDetailsList3= new ArrayList<>();
//        orderDetailsList3.add(orderDetails3);
//        List<OrderDetails> orderDetailsList4= new ArrayList<>();
//        orderDetailsList4.add(orderDetails4);
//        Order order1= new Order(1,null,0,null,"Đã giao",null,orderDetailsList1);
//        Order order2= new Order(2,null,0,null,"Đã giao",null,orderDetailsList2);
//        Order order3= new Order(3,null,0,null,"Đã giao",null,orderDetailsList3);
//        Order order4= new Order(4,null,0,null,"Đã giao",null,orderDetailsList4);
//        ArrayList<Order> listOrder= new ArrayList<>();
//        listOrder.add(order1);
//        listOrder.add(order2);
//        listOrder.add(order3);
//        listOrder.add(order4);
//        OrderAdapter orderAdapter= new OrderAdapter(this,R.layout.layout_item_history,listOrder);
//        lv_OrderHistory.setAdapter(orderAdapter);

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

//        lv_OrderHistory.setOnItemClickListener((adapterView, view, i, l) -> {
//            Order order= listOrder.get(i);
//            OrderDetails orderDetails=order.getOrderDetails().get(0);
//            Book book= orderDetails.getBook();
//            Intent intent= new Intent(History.this,Feedback.class);
//            intent.putExtra("bookId",book.getBookId());
//            intent.putExtra("image",book.getImage());
//            intent.putExtra("bookName",book.getBookName());
//            startActivity(intent);
//        });
    }

    private void HistoryById(int userID) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query{\n" +
                "  bookNotComment(userId: " + userID + "){\n" +
                "      orderDetailId\n" +
                "      quantity\n" +
                "      sellPrice\n" +
                "      book{\n" +
                "        bookId\n" +
                "        bookName\n" +
                "        images{\n" +
                "          imageName\n" +
                "          imageData\n" +
                "        }\n" +
                "      }\n" +
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
                        Object histories = dataMap.get("bookNotComment");

                        if (histories instanceof ArrayList) {
                            List<OrderDetails> orderDetailsList = new ArrayList<>();
                            ArrayList<?> orderDetailsArray = (ArrayList<?>) histories;

                            for (Object orderdetailsObj : orderDetailsArray) {
                                if (orderdetailsObj instanceof LinkedTreeMap) {
                                    LinkedTreeMap<String, Object> orderDetailsMap = (LinkedTreeMap<String, Object>) orderdetailsObj;

                                    OrderDetails orderDetail = new OrderDetails();
                                    orderDetail.setQuantity(((Double) orderDetailsMap.get("quantity")).intValue());
                                    orderDetail.setSellPrice((double) orderDetailsMap.get("sellPrice"));

                                    Object objectBook = orderDetailsMap.get("book");
                                    if (objectBook instanceof LinkedTreeMap) {
                                        LinkedTreeMap<String, Object> bookMap = (LinkedTreeMap<String, Object>) objectBook;
                                        Book book = new Book();
                                        if (bookMap.get("bookName") instanceof String) {
                                            book.setBookName((String) bookMap.get("bookName"));
                                        }
                                        if (bookMap.get("bookId") instanceof Number) {
                                            book.setBookId(((Double) bookMap.get("bookId")).intValue());
                                        }
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
                                        orderDetail.setBook(book);
                                    }
                                    orderDetailsList.add(orderDetail);
                                }
                            }
                            rcv_history.setLayoutManager(new LinearLayoutManager(History.this));
                            OrderAdapter adapter = new OrderAdapter(History.this, new ArrayList<>(orderDetailsList));
                            rcv_history.setAdapter(adapter);
                        } else {
                            Log.e("Error", "cartDetail không phải là danh sách");
                        }
                    } else {
                        Log.e("Error", "Dữ liệu phản hồi không đúng định dạng: " + data.getData());

                    }
                }else {
                    Log.e("Error", "Phản hồi thất bại: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {

            }
        });

    }
}