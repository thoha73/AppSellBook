package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.OrderDetailAdapter;
import com.example.appsellbook.model.OrderDetail;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OrderdetailsActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        ListView listView = findViewById(R.id.order_details_list);
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
        List<OrderDetail> orderDetailList = new ArrayList<>();
        Intent intent = getIntent();
        int img = intent.getIntExtra("img", 0);
        String orderer = intent.getStringExtra("orderer");
        String content = intent.getStringExtra("content");
        orderDetailList.add(new OrderDetail(img, "Người đặt hàng:", orderer));
        //orderDetailList.add(new OrderDetail(R.drawable.icon_orderer, "Người đặt hàng:", "Jenny Huynh"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_id, "Mã đơn hàng:", "2211505"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_address, "Địa chỉ giao hàng:" , " Khoi Pho Quang Lang B,Dien Nam Trung ,Dien Ban,Quang Nam"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_order, "Nội dung đặt hàng:", content));
        orderDetailList.add(new OrderDetail(R.drawable.ic_payment, "Phương thức thanh toán :", "Thanh toán khi giao hàng"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_money, "Tổng tiền:", "540.000đ"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_shipping, "Đơn vị vận chuyển:", "HKVBOOK express"));

        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(R.layout.order_detail_item, this, orderDetailList);
        listView.setAdapter(orderDetailAdapter);
    }
}