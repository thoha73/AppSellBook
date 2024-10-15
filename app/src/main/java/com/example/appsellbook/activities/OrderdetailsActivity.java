package com.example.appsellbook.activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.OrderDetailAdapter;
import com.example.appsellbook.model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderdetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        ListView listView = findViewById(R.id.order_details_list);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(new OrderDetail(R.drawable.icon_orderer, "Oder:", "Jenny Huynh"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_id, "ID Order:", "2211505"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_address, "Shipping address:" , " Khoi Pho Quang Lang B,Dien Nam Trung ,Dien Ban,Quang Nam"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_order, "Oder Detail:", "How to Win Friends and Influence People (x1) Never eat alone(x1)"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_payment, "Payment method :", "Cash payment"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_money, "Total amount:", "540.000đ"));
        orderDetailList.add(new OrderDetail(R.drawable.ic_shipping, "Shipping unit:", "HKVBOOK express"));

        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(R.layout.order_detail_item, this, orderDetailList);
        listView.setAdapter(orderDetailAdapter);

    }
}