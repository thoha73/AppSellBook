package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.adapter.NotificationUserAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.example.appsellbook.model.Book;
import com.example.appsellbook.model.User;
import com.example.appsellbook.adapter.NotificationShopAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerNotification extends AppCompatActivity {
    ImageView img_back;
    LinearLayout ll_delete;
    ListView listViewNotification;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownernotification);
        // Tạo danh sách người dùng
//        List<User> name = new ArrayList<>(Arrays.asList(
//                new User("Võ Vĩ Khương", "vikhuong92", "123"),
//                new User("Nguyễn Thọ Hà", "thoha73", "123"),
//                new User("Phan Anh Duy", "pad76", "123"),
//                new User("Trần Thanh Vỹ", "thanhvy92", "123"),
//                new User("Quang Thảo", "quangthao75", "123")
//        ));
//
//        // Tạo danh sách hình ảnh (Book chứa id của hình ảnh)
//        Book[] image = new Book[]{
//                new Book(R.drawable.imguser),
//                new Book(R.drawable.user1),
//                new Book(R.drawable.user2),
//                new Book(R.drawable.user3),
//                new Book(R.drawable.user4)
//        };

        // Khởi tạo ListView và CustomAdapter
        listViewNotification = findViewById(R.id.lv1);
//        NotificationShopAdapter adapter = new NotificationShopAdapter(OwnerNotification.this, R.layout.layout_ownernotification, name, image);

        // Thiết lập adapter cho ListView
//        listViewNotification.setAdapter(adapter);
        img_back = findViewById(R.id.imgView_Notificationback);
        img_back.setOnClickListener(v->finish());
        SessionManager sessionManager= new SessionManager(this);
        int userId=sessionManager.getUserId();
        GetNotification(userId);
        BottomNavigationView bottom_NavigationView2;
        bottom_NavigationView2 = findViewById(R.id.bottom_navigation2);
        bottom_NavigationView2.setSelectedItemId(R.id.menu_notification);
        bottom_NavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_home){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_notification){
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
                    startActivity(new Intent(getApplicationContext(), ShopOwner.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_product){
                    startActivity(new Intent(getApplicationContext(), PostNewProduct.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                return false;
            }
        });
        ll_delete = findViewById(R.id.ll_delete);
//        ll_delete.setOnClickListener(view -> {
//            name.clear();
//            adapter.notifyDataSetChanged();
//
//        });
    }
    public void GetNotification(int userId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query{\n" +
                "  allNotificationForShop(userId: " + userId + "){\n" +
                "    context\n" +
                "    createdAt\n" +
                "    isRead\n" +
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();

                    // Kiểm tra nếu có lỗi từ GraphQL
                    if (data.getErrors() != null && !data.getErrors().isEmpty()) {
                        String errorMessage = data.getErrors().get(0).getMessage();
                        new AlertDialog.Builder(OwnerNotification.this)
                                .setTitle("Thông báo")
                                .setMessage(errorMessage)
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();
                    } else {
                        try {
                            // Kiểm tra dữ liệu trả về
                            Map<String, Object> responseData = (Map<String, Object>) data.getData();
                            Object notificationData = responseData.get("allNotificationForShop");

                            if (notificationData instanceof List) {
                                List<Map<String, Object>> notificationList = (List<Map<String, Object>>) notificationData;
                                List<com.example.appsellbook.DTOs.Notification> notifications = new ArrayList<>();

                                for (Map<String, Object> notificationMap : notificationList) {
                                    com.example.appsellbook.DTOs.Notification notification = new com.example.appsellbook.DTOs.Notification();

                                    // Lấy thông tin thông báo
                                    String context = (String) notificationMap.get("context");
                                    String createdAt = (String) notificationMap.get("createdAt");
                                    boolean isRead = (Boolean) notificationMap.get("isRead");

                                    // Chuyển đổi createdAt sang đối tượng Date
                                    Date createdDate = convertStringToDate(createdAt);

                                    // Cập nhật thông tin cho đối tượng Notification
                                    notification.setContext(context);
                                    notification.setCreatedDate(createdDate);
                                    notification.setRead(isRead);

                                    // Thêm vào danh sách
                                    notifications.add(notification);
                                }

                                // Cập nhật dữ liệu vào Adapter hoặc xử lý sau khi có dữ liệu
                                NotificationShopAdapter notificationShopAdapter= new NotificationShopAdapter(OwnerNotification.this,R.layout.layout_ownernotification,notifications);
                                listViewNotification.setAdapter(notificationShopAdapter);
                                ll_delete.setOnClickListener(view -> {
                                    notifications.clear();
                                    notificationShopAdapter.notifyDataSetChanged();
                                });
                            } else {
                                Log.e("Error", "Dữ liệu trả về không phải là một danh sách thông báo.");
                            }
                        } catch (Exception e) {
                            Log.e("Error", "Lỗi khi xử lý dữ liệu trả về từ GraphQL: " + e.getMessage());
                        }
                    }
                } else {
                    Log.e("Error", "Phản hồi thất bại: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("Error", "Lỗi khi tải dữ liệu: " + t.getMessage());
                // Hiển thị thông báo lỗi cho người dùng khi không kết nối được
                new AlertDialog.Builder(OwnerNotification.this)
                        .setTitle("Lỗi kết nối")
                        .setMessage("Không thể tải dữ liệu từ server. Vui lòng thử lại.")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }

    // Phương thức để chuyển đổi chuỗi thời gian sang đối tượng Date
    private Date convertStringToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            Log.e("Error", "Lỗi khi chuyển đổi chuỗi thành ngày: " + e.getMessage());
            return null;
        }
    }
}