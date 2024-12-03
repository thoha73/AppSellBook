package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.DTOs.CartDetail;
import com.example.appsellbook.DTOs.DataCache;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.adapter.OrderTotalAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeSuccess extends AppCompatActivity {
    private Button btn_cancel;
    RecyclerView recyclerView ;
    TextView tv_tongtien;
    private ArrayList<CartDetail> selectedItems = DataCache.getSelectedItems();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_success);
        btn_cancel = findViewById(R.id.btn_cancel_order);
        recyclerView = findViewById(R.id.rcv_status_order);
        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());

        tv_tongtien = findViewById(R.id.tv_tongtien);
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        SessionManager sessionManager = new SessionManager(this);
        tv_tongtien.setText("Tổng tiền: "+decimalFormat.format(DataCache.getTotalOrder()- sessionManager.getPoint()*100)+"đ" );

        recyclerView.setLayoutManager(new LinearLayoutManager(ChangeSuccess.this));
        OrderTotalAdapter adapter = new OrderTotalAdapter(this,selectedItems);
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        int orderId = sharedPreferences.getInt("orderId", -1);
        if (orderId != -1) {
            Log.d("SharedPreferences", "Order ID: " + orderId);
        } else {
            Log.d("SharedPreferences", "Order ID not found!");
        }
        btn_cancel.setOnClickListener(view -> {
            CancelOrder(orderId);
        });
        BottomNavigationView bottom_NavigationView;
        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setSelectedItemId(R.id.menu_profile);
        bottom_NavigationView.setOnNavigationItemSelectedListener(item -> {
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
        });
    }
    public void CancelOrder(int orderId){
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " mutation{\n" +
                "  removeOrder(orderId:" +orderId+")\n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    // Assuming the response contains a "success" field indicating the result of the mutation
                    if (data.getErrors() != null && !data.getErrors().isEmpty()) {
                        // Lấy thông báo lỗi từ response
                        String errorMessage = data.getErrors().get(0).getMessage();

                        // Hiển thị thông báo lỗi cho người dùng
                        runOnUiThread(() -> {
                            new AlertDialog.Builder(ChangeSuccess.this)
                                    .setTitle("Thông báo")
                                    .setMessage(errorMessage)
                                    .setPositiveButton("Ok", (dialog, which) -> dialog.dismiss())
                                    .create()
                                    .show();
                        });
                        Log.e("GraphQL Error", "Error: " + errorMessage);
                        return;
                    }

                    // Nếu không có lỗi, tiếp tục xử lý bình thường
                    if (data.getData() != null) {
                        if (data.getData() instanceof LinkedTreeMap) {
                            LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                            Boolean success = (Boolean) dataMap.get("removeOrder");
                            if (success != null && success) {
                                Log.d("Notification", "Order cancelled successfully for orderId: " + orderId);
                                SessionManager sessionManager = new SessionManager(ChangeSuccess.this);
                                CreateNotification(sessionManager.getUsername());
                                runOnUiThread(() -> {
                                    new AlertDialog.Builder(ChangeSuccess.this)
                                            .setTitle("Thông báo")
                                            .setMessage("Bạn đã hủy đơn hàng thành công!")
                                            .setPositiveButton("Ok", (dialog, which) -> {
                                                dialog.dismiss();
                                                Intent intent = new Intent(ChangeSuccess.this, Home.class);
                                                startActivity(intent);
                                            })
                                            .create()
                                            .show();
                                });
                            } else {
                                Log.e("GraphQL Error", "Failed to cancel order: " + orderId);
                            }
                        }
                    }
                } else {
                    Log.e("GraphQL Error", "Mutation failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Error during mutation", t);
            }
        });
    }
    public void CreateNotification( String context){
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " mutation{\n" +
                "  createNotification(notification:  {\n" +
                "     context: \""+context+" đã hủy một đơn hàng.\",\n" +
                "     userId: "+12+"\n" +
                "  })\n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    // Assuming the response contains a "success" field indicating the result of the mutation
                    if (data.getData() instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                        Boolean success = (Boolean) dataMap.get("createNotification");

                        // If 'success' is true, log or handle the successful update
                        if (success != null && success) {
                            Log.d("Notification", "Update successfully for notification: " );
                        } else {
                            Log.d("Notification", "Update failed for notification: " );
                        }
                    }
                } else {
                    Log.e("GraphQL Error", "Mutation failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Error during mutation", t);
            }
        });
    }
}
