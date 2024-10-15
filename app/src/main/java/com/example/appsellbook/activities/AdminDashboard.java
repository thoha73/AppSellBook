package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.AdminAdapter;
import com.example.appsellbook.model.Statistic;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdminAdapter adminAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        recyclerView = findViewById(R.id.rcv_admin);
        adminAdapter = new AdminAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adminAdapter.setData(getData());
        recyclerView.setAdapter(adminAdapter);
        adminAdapter.setOnItemClickListener(position -> {
            Statistic stc=adminAdapter.getData().get(position);
            if(stc.getTitle()=="Total user"){
                startActivity(new Intent(AdminDashboard.this,TotalUser.class));
            }else if(stc.getTitle()=="Product"){
                Intent intent = new Intent(AdminDashboard.this, Home1.class);
                intent.putExtra("role","admin");
                startActivity(intent);
//                startActivity(new Intent(AdminDashboard.this,Home.class));
            }else if(stc.getTitle()=="Orders"){
//                startActivity(new Intent(AdminDashboard.this,Orders.class));
            }
        });
    }
    public List<Statistic> getData(){
        List<Statistic> list = new ArrayList<>();
        list.add(new Statistic(1,"Total user","100K"));
        list.add(new Statistic(2,"Product","20K"));
        list.add(new Statistic(3,"Orders","20K"));
        return list;
    }

}