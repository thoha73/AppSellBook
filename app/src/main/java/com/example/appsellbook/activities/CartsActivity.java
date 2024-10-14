package com.example.appsellbook.activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.CartsAdapter;
import com.example.appsellbook.model.Carts;

import java.util.ArrayList;
import java.util.List;

public class CartsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        ListView listView = findViewById(R.id.cart_list);
        List<Carts> cartList = new ArrayList<>();
        cartList.add(new Carts(R.drawable.img_chualanhtraitim, "Chữa lành trái tim", "100.000 đ"));
        cartList.add(new Carts(R.drawable.img_dacnhantam, "Đắc nhân tâm", "100.000 đ"));
        cartList.add(new Carts(R.drawable.img_thirdpronuncitionofhistory, "Third pronuncition of history", "200.000 đ"));
        cartList.add(new Carts(R.drawable.img_sucmanhtiemthuc, "Sức mạnh tiềm thức (Tái bản năm 2021)", "100.000 đ"));
        CartsAdapter adapter = new CartsAdapter(this, R.layout.cart_item, cartList);
        listView.setAdapter(adapter);
    }
}
