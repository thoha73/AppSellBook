package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.DTOs.Order;
import com.example.appsellbook.DTOs.User;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.CurrencyFormat;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.adapter.OrderDetailAdapter;
import com.example.appsellbook.adapter.OrdersAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.example.appsellbook.model.OrderDetail;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderdetailsActivity extends AppCompatActivity {
    private  List<OrderDetail> orderDetailList = new ArrayList<>();
    private ListView listView;
    private Button btn_cancelorder, btn_confirmorder;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        listView = findViewById(R.id.order_details_list);
        btn_cancelorder=findViewById(R.id.btn_cancelorder);
        btn_confirmorder=findViewById(R.id.btn_confirmorder);
        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
        BottomNavigationView bottom_NavigationView2;
        bottom_NavigationView2 = findViewById(R.id.bottom_navigation2);
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
                    startActivity(new Intent(getApplicationContext(), OwnerNotification.class));
                    overridePendingTransition(0,0);
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

        Intent intent = getIntent();
        int orderId = intent.getIntExtra("orderId", 0);
        int userId = intent.getIntExtra("userId", 0);
        if(orderId!=0){
            GetOrder(orderId);
        }
        String context="";
        btn_confirmorder.setOnClickListener(v->{
            ConfirmOrder(userId,orderId);
        });
        btn_cancelorder.setOnClickListener(v->{
            EditText inputReason = new EditText(this);
            inputReason.setHint("Nhập lý do hủy");
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận hủy đơn hàng")
                    .setMessage("Vui lòng nhập lý do hủy đơn hàng:")
                    .setView(inputReason)
                    .setPositiveButton("Xác nhận", (dialog, which) -> {
                        String reason = "Đơn hàng của bạn đã bị hủy với lí do :"+inputReason.getText().toString().trim();
                        if (!reason.isEmpty()) {
                            CancelOrder(userId,orderId, reason);
                        } else {
                            Toast.makeText(this, "Lý do hủy không được để trống!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                    .show();
        });

    }
    public void GetOrder(int orderId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query{\n" +
                "  orderById(orderId: " + orderId + "){\n" +
                "    deliveryAddress\n" +
                "    paymentMethod\n" +
                "    orderDetails{\n" +
                "      quantity\n" +
                "      sellPrice\n" +
                "      book{\n" +
                "        bookName\n" +
                "      }\n" +
                "    }\n" +
                "    user{\n" +
                "      firstName\n" +
                "    }\n" +
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    if (data.getErrors() != null && !data.getErrors().isEmpty()) {
                        String errorMessage = data.getErrors().get(0).getMessage();
                        new AlertDialog.Builder(OrderdetailsActivity.this)
                                .setTitle("Thông báo")
                                .setMessage(errorMessage)
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();
                    } else {
                        try {
                            // Extract the response data
                            Map<String, Object> responseData = (Map<String, Object>) data.getData();
                            Map<String, Object> orderData = (Map<String, Object>) responseData.get("orderById");

                            if (orderData != null) {
                                String deliveryAddress = (String) orderData.get("deliveryAddress");
                                int paymentMethod = ((Number) orderData.get("paymentMethod")).intValue();
                                String orderer = ((Map<String, Object>) orderData.get("user")).get("firstName").toString();

                                // Create the list of order details
                                List<OrderDetail> updatedOrderDetails = new ArrayList<>();
                                List<Map<String, Object>> orderDetails = (List<Map<String, Object>>) orderData.get("orderDetails");
                                String content = "";
                                double total=0;
                                for (Map<String, Object> detailMap : orderDetails) {
                                    String bookName = (String) ((Map<String, Object>) detailMap.get("book")).get("bookName");
                                    int quantity = ((Number) detailMap.get("quantity")).intValue();
                                    double sellPrice = ((Number) detailMap.get("sellPrice")).doubleValue();
                                    content += bookName+"(x"+quantity+")\n";
                                    total+=(quantity*sellPrice);
                                    updatedOrderDetails.add(new OrderDetail(R.drawable.ic_order, "Book: " + bookName, "Quantity: " + quantity + ", Price: " + sellPrice));
                                }
                                Map<String, Object> userMap = (Map<String, Object>) orderData.get("user");
                                User user = new User();
                                String firstName = (String) userMap.get("firstName");
                                user.setFirstName(firstName);
                                String method="";
                                if(paymentMethod==0){
                                    method="Thanh toán khi nhận hàng";
                                }else {
                                    method="Thanh toán qua thẻ";
                                }
                                orderDetailList.add(new OrderDetail(R.drawable.icon_orderer, "Người đặt hàng:", user.getFirstName()));
                                orderDetailList.add(new OrderDetail(R.drawable.ic_id, "Mã đơn hàng:", "DHHKVK00"+orderId));
                                orderDetailList.add(new OrderDetail(R.drawable.ic_address, "Địa chỉ giao hàng:" , deliveryAddress));
                                orderDetailList.add(new OrderDetail(R.drawable.ic_order, "Nội dung đặt hàng:", content));
                                orderDetailList.add(new OrderDetail(R.drawable.ic_payment, "Phương thức thanh toán :", method));
                                orderDetailList.add(new OrderDetail(R.drawable.ic_money, "Tổng tiền:", CurrencyFormat.formatCurrency(total)));
                                orderDetailList.add(new OrderDetail(R.drawable.ic_shipping, "Đơn vị vận chuyển:", "HKVBOOK express"));

                                OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(R.layout.order_detail_item, OrderdetailsActivity.this, orderDetailList);
                                listView.setAdapter(orderDetailAdapter);
                            }
                        } catch (Exception e) {
                            Log.e("Error", "Error processing GraphQL response: " + e.getMessage());
                        }
                    }
                } else {
                    Log.e("Error", "Response unsuccessful: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("Error", "Error in API call: " + t.getMessage());
                new AlertDialog.Builder(OrderdetailsActivity.this)
                        .setTitle("Lỗi")
                        .setMessage("Có lỗi xảy ra khi kết nối tới server. Vui lòng thử lại sau.")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }
            ;
        });
    }
    public void CreateNotification(int userId , int orderId, String context){
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " mutation{\n" +
                "  createNotification(notification:  {\n" +
                "     context: \""+context+"\",\n" +
                "     userId: "+userId+"\n" +
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
                            Log.d("Notification", "Update successfully for notification: " + userId);
                        } else {
                            Log.d("Notification", "Update failed for notification: " + userId);
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
    public void ConfirmOrder(int userId,int orderId){
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " mutation{\n" +
                "  updateOrder(orderId:" +orderId+")\n" +
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
                        Boolean success = (Boolean) dataMap.get("updateOrder");
                        String context="Đơn hàng DHHKBOOK00\""+orderId+"\" đã được xác nhận. Đơn hàng sẽ được vận chuyển trong ngày.";
                        // If 'success' is true, log or handle the successful update
                        if (success != null && success) {
                            Log.d("Notification", "Update successfully for orderId: " + orderId);
                            CreateNotification(userId,orderId,context);
                            new AlertDialog.Builder(OrderdetailsActivity.this)
                                    .setTitle("Thông báo")
                                    .setMessage("Bạn xác nhận đơn hàng thành công!")
                                    .setPositiveButton("Ok", (dialog, which) ->{
                                        dialog.dismiss();
                                        Intent intent = new Intent(OrderdetailsActivity.this, OrdersActivity.class);
                                        startActivity(intent);
                                    })
                                    .create()
                                    .show();

                        } else {
                            Log.d("Notification", "Update failed for orderId: " + orderId);
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
    public void CancelOrder(int userId,int orderId, String context){
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " mutation{\n" +
                "  removeOrder1(orderId:" +orderId+")\n" +
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
                        Boolean success = (Boolean) dataMap.get("removeOrder1");

                        // If 'success' is true, log or handle the successful update
                        if (success != null && success) {
                            Log.d("Notification", "Update successfully for orderId: " + orderId);
                            CreateNotification(userId,orderId,context);
                            new AlertDialog.Builder(OrderdetailsActivity.this)
                                    .setTitle("Thông báo")
                                    .setMessage("Bạn xác hủy đơn hàng thành công!")
                                    .setPositiveButton("Ok", (dialog, which) ->{
                                        dialog.dismiss();
                                        Intent intent = new Intent(OrderdetailsActivity.this, OrdersActivity.class);
                                        startActivity(intent);
                                    })
                                    .create()
                                    .show();
                        } else {
                            Log.d("Notification", "Update failed for orderId: " + orderId);
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