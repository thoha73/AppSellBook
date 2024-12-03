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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.adapter.NotificationUserAdapter;
import com.example.appsellbook.adapter.OrdersAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity {
    private ListView lv_Notification;
    private LinearLayout ll_delete ;

    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lv_Notification=findViewById(R.id.listView_Notification);
        List<com.example.appsellbook.DTOs.Notification> list= new ArrayList<>();
        Calendar calendar= Calendar.getInstance();


//        list.add(new Notification("Giảm giá đến 15% cho ngày 11/11",df.format(calendar.getTime()),false));
//        list.add(new Notification("Giảm giá đến 10% cho ngày 12/11",df.format(calendar.getTime()),false));
//        list.add(new Notification("Đơn hàng của bạn đã được xác nhận! Vui lòng chú ý điện thoại để nhận hàng.",df.format(calendar.getTime()),false));

        SessionManager sessionManager= new SessionManager(this);
        int userId=sessionManager.getUserId();
        GetNotification(userId);
        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
        BottomNavigationView bottom_NavigationView;
        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setSelectedItemId(R.id.menu_notification);
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

        ll_delete = findViewById(R.id.layout_delete);

    }
    public void GetNotification(int userId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query{\n" +
                "  allNotification(userId: " + userId + "){\n" +
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
                        new AlertDialog.Builder(Notification.this)
                                .setTitle("Thông báo")
                                .setMessage(errorMessage)
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();
                    } else {
                        try {
                            // Kiểm tra dữ liệu trả về
                            Map<String, Object> responseData = (Map<String, Object>) data.getData();
                            Object notificationData = responseData.get("allNotification");

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
                                NotificationUserAdapter notificationUserAdapter= new NotificationUserAdapter(Notification.this,R.layout.layout_item_notification,notifications);
                                lv_Notification.setAdapter(notificationUserAdapter);
                                ll_delete.setOnClickListener(view -> {
                                    notifications.clear();
                                    notificationUserAdapter.notifyDataSetChanged();
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
                new AlertDialog.Builder(Notification.this)
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