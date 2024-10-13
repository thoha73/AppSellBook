package com.example.appbansach;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        ListView listView = findViewById(R.id.cart_list);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(new Cart(R.drawable.img_chualanhtraitim, "Chữa lành trái tim", "100.000 đ"));
        cartList.add(new Cart(R.drawable.img_dacnhantam, "Đắc nhân tâm", "100.000 đ"));
        cartList.add(new Cart(R.drawable.img_thirdpronuncitionofhistory, "Third pronuncition of history", "200.000 đ"));
        cartList.add(new Cart(R.drawable.img_sucmanhtiemthuc, "Sức mạnh tiềm thức (Tái bản năm 2021)", "100.000 đ"));
        CartAdapter adapter = new CartAdapter(this, R.layout.cart_item, cartList);
        listView.setAdapter(adapter);
    }
}
