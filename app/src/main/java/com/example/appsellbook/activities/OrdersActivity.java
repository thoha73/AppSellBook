package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.DTOs.Order;
import com.example.appsellbook.DTOs.OrderDetail;
import com.example.appsellbook.DTOs.User;
import com.example.appsellbook.R;
import com.example.appsellbook.adapter.OrdersAdapter;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.example.appsellbook.model.OrderDetails;
import com.example.appsellbook.model.Orders;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {
    private ListView listView;
    private OrdersAdapter ordersAdapter;
    private List<Order> orderList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);

        listView = findViewById(R.id.orders_list);

        // Thiết lập sự kiện cho nút quay lại
        ImageView img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());

        // Thiết lập BottomNavigationView
        BottomNavigationView bottom_NavigationView2 = findViewById(R.id.bottom_navigation2);
        bottom_NavigationView2.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_home) {
                startActivity(new Intent(getApplicationContext(), Home1.class));
                overridePendingTransition(0, 0);
                return true;
            }
            if (id == R.id.menu_notification) {
                startActivity(new Intent(getApplicationContext(), OwnerNotification.class));
                overridePendingTransition(0, 0);
                return true;
            }
            if (id == R.id.menu_search) {
                startActivity(new Intent(getApplicationContext(), Home1.class));
                overridePendingTransition(0, 0);
                return true;
            }
            if (id == R.id.menu_setting) {
                startActivity(new Intent(getApplicationContext(), ShopOwner.class));
                overridePendingTransition(0, 0);
                return true;
            }
            if (id == R.id.menu_product) {
                startActivity(new Intent(getApplicationContext(), PostNewProduct.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

        // Gọi phương thức để lấy dữ liệu đơn hàng từ GraphQL
        GetOrder();
    }

    public void GetOrder() {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query {\n" +
                "  orderConfirm {\n" +
                "    orderId\n" +
                "    deliveryAddress\n" +
                "    paymentMethod\n" +
                "    orderDetails {\n" +
                "      quantity\n" +
                "      sellPrice\n" +
                "      book {\n" +
                "        bookName\n" +
                "      }\n" +
                "    }\n" +
                "    user {\n" +
                "      userId\n" +
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
                        new AlertDialog.Builder(OrdersActivity.this)
                                .setTitle("Thông báo")
                                .setMessage(errorMessage)
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();
                    } else {
                        try {
                            // Kiểm tra dữ liệu trả về
                            Map<String, Object> responseData = (Map<String, Object>) data.getData();
                            Object orderData = responseData.get("orderConfirm");

                            if (orderData instanceof List) {
                                List<Map<String, Object>> orderListData = (List<Map<String, Object>>) orderData;
                                List<Order> orders = new ArrayList<>();
                                for (Map<String, Object> orderMap : orderListData) {
                                    Order order = new Order();

                                    // Lấy thông tin đơn hàng
                                    String deliveryAddress = (String) orderMap.get("deliveryAddress");
                                    int orderId = ((Number) orderMap.get("orderId")).intValue();
                                    int paymentMethod = ((Number) orderMap.get("paymentMethod")).intValue();
                                    order.setDeliveryAddress(deliveryAddress);
                                    order.setPaymentMethod(paymentMethod);
                                    order.setOrderId(orderId);

                                    // Lấy thông tin người dùng
                                    Map<String, Object> userMap = (Map<String, Object>) orderMap.get("user");
                                    User user = new User();
                                    String firstName = (String) userMap.get("firstName");
                                    int userId = ((Number) userMap.get("userId")).intValue();
                                    user.setFirstName(firstName);
                                    user.setUserId(userId);
                                    order.setUser(user);

                                    // Lấy chi tiết đơn hàng
                                    List<Map<String, Object>> orderDetails = (List<Map<String, Object>>) orderMap.get("orderDetails");
                                    List<OrderDetail> orderDetailsList = new ArrayList<>();
                                    for (Map<String, Object> detailMap : orderDetails) {
                                        int quantity = ((Number) detailMap.get("quantity")).intValue();
                                        double sellPrice = ((Number) detailMap.get("sellPrice")).doubleValue();
                                        OrderDetail orderDetail = new OrderDetail();
                                        orderDetail.setSellPrice(sellPrice);
                                        orderDetail.setQuantity(quantity);
                                        orderDetailsList.add(orderDetail);
                                    }
                                    order.setOrderDetails(orderDetailsList);

                                    orders.add(order);  // Thêm đơn hàng vào danh sách
                                }

                                // Cập nhật dữ liệu vào Adapter
                                ordersAdapter = new OrdersAdapter(OrdersActivity.this, R.layout.order_item, orders);
                                ordersAdapter.notifyDataSetChanged();
                                listView.setAdapter(ordersAdapter);
                            } else {
                                Log.e("Error", "Dữ liệu trả về không phải là một danh sách đơn hàng.");
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
                new AlertDialog.Builder(OrdersActivity.this)
                        .setTitle("Lỗi kết nối")
                        .setMessage("Không thể tải dữ liệu từ server. Vui lòng thử lại.")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }
}