package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.DTOs.CartDetail;
import com.example.appsellbook.DTOs.DataCache;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.adapter.CartsAdapter;
import com.example.appsellbook.adapter.OrderTotalAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.example.appsellbook.model.Carts;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderTotal extends AppCompatActivity {
    private ArrayList<CartDetail> selectedItems = DataCache.getSelectedItems();
    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_total);
        RecyclerView recyclerView ;
        TextView tv_date,tv_totalmoney,tv_totalpay,tv_point,tv_swpoint;
        Switch sw_point;
        tv_swpoint = findViewById(R.id.tv_swpoint);
        tv_date = findViewById(R.id.tv_date);
        tv_point = findViewById(R.id.tvpoint);
        tv_totalmoney = findViewById(R.id.tv_totalMoney);
        tv_totalpay = findViewById(R.id.total_Pay);
        recyclerView = findViewById(R.id.rcv_ordertotal);
        sw_point= findViewById(R.id.switchbtn_bypoints);
        SessionManager sessionManager = new SessionManager(this);
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        int swpoint = sessionManager.getPoint();
        int userId = sessionManager.getUserId();


        tv_point.setText(decimalFormat.format((swpoint * 100))+"đ");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        tv_date.setText(""+currentDate);
        Intent intent1 = new Intent();
        double tongtien  = DataCache.getTotalOrder();

        tv_totalmoney.setText( decimalFormat.format(tongtien)+"");
        double tienthuong = swpoint*100;
        double tongthanhtoan =  (tongtien - tienthuong);


        sw_point.setOnClickListener(view -> {
            if (sw_point.isChecked()){
                tv_swpoint.setVisibility(View.VISIBLE);
                tv_point.setVisibility(View.VISIBLE);
                tv_swpoint.setText(decimalFormat.format((swpoint * 100))+"đ");
                tv_totalpay.setText(decimalFormat.format(tongthanhtoan)+"đ");
            }else {
                tv_swpoint.setVisibility(View.GONE);
                tv_point.setVisibility(View.GONE);
                tv_totalpay.setText(decimalFormat.format(tongtien)+"đ");
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderTotal.this));
        OrderTotalAdapter adapter = new OrderTotalAdapter(this,selectedItems);
        recyclerView.setAdapter(adapter);



        Button btnPurchase = findViewById(R.id.btn_purchase);
        btnPurchase.setOnClickListener(v -> {
            String userName = sessionManager.getUsername();

            AddOrder(userId);
            CreateNotification(userName);
            if (sw_point.isChecked()){
                UpdatePoint(userId,0);
            }
            else{
                UpdatePoint(userId,swpoint+DataCache.getSelectedItemsCount()*2);
            }
        });

        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
    }
    private  void AddOrder(int userId){
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "mutation{\n" +
                "  addOrder(userId:"+ userId + "){\n" +
                "    orderId\n" +
                "  }\n" +
                "\n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                        LinkedTreeMap data = (LinkedTreeMap) response.body().getData();
                        LinkedTreeMap addOrderData = (LinkedTreeMap) data.get("addOrder");
                        Double orderId = (Double) addOrderData.get("orderId");
                        int id = orderId.intValue();
                        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("orderId", id);
                        editor.apply();

                        Toast.makeText(OrderTotal.this, "Đặt hàng thành công ! \n Chúc mừng bạn nhận được "+ (DataCache.getSelectedItemsCount()) +" điểm thưởng", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(OrderTotal.this,YourOrders.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    Log.e("Ordertotal", "Lỗi từ server: " + response.errorBody());
                    Toast.makeText(OrderTotal.this, "Không thể đặt hàng. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("Feedback", "Lỗi kết nối: " + t.getMessage());
                Toast.makeText(OrderTotal.this, "Kết nối thất bại! Vui lòng kiểm tra mạng.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void CreateNotification( String context){
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " mutation{\n" +
                "  createNotification(notification:  {\n" +
                "     context: \""+context+" đã đặt một đơn hàng.\",\n" +
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
    public void UpdatePoint(int userId,int point){
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " mutation{\n" +
                "  updatePoint(point: "+point+",userId: "+userId+")\n" +
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
                        Boolean success = (Boolean) dataMap.get("updatePoint");

                        // If 'success' is true, log or handle the successful update
                        if (success != null && success) {
                            Log.d("Notification", "Update successfully for point: " + userId);

                        } else {
                            Log.d("Notification", "Update failed for point: " + userId);
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
