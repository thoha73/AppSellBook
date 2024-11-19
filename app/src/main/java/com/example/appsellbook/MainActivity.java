//package com.example.appsellbook;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.ListView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.appsellbook.activities.Login;
//import com.example.appsellbook.adapter.CartsAdapter;
//import com.example.appsellbook.model.Carts;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.cart);
//            ListView listView = findViewById(R.id.cart_list);
//            List<Carts> cartList = new ArrayList<>();
//            cartList.add(new Carts(R.drawable.img_chualanhtraitim, "Chữa lành trái tim", "100.000 đ"));
//            cartList.add(new Carts(R.drawable.img_dacnhantam, "Đắc nhân tâm", "100.000 đ"));
//            cartList.add(new Carts(R.drawable.img_thirdpronuncitionofhistory, "Third pronuncition of history", "200.000 đ"));
//            cartList.add(new Carts(R.drawable.img_sucmanhtiemthuc, "Sức mạnh tiềm thức (Tái bản năm 2021)", "100.000 đ"));
//            CartsAdapter adapter = new CartsAdapter(this, R.layout.cart_item, cartList);
//            listView.setAdapter(adapter);
//        }
//    }
package com.example.appsellbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.activities.HKVBook;
import com.example.appsellbook.activities.InformationGroup;
import com.example.appsellbook.activities.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển sang Activity B sau 5 giây
                Intent intent = new Intent(MainActivity.this, InformationGroup.class);
                startActivity(intent);
                // Đóng Activity hiện tại (nếu muốn)
                finish();
            }
        }, 5000);
    }
}
