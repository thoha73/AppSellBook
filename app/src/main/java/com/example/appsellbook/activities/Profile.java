package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.DTOs.User;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    TextView tv_name, tv_gender, tv_dob, tv_phone, tv_email, tv_address;

    @SuppressLint("MissingInflatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tv_name = findViewById(R.id.name);
        tv_gender = findViewById(R.id.gender);
        tv_dob = findViewById(R.id.dob);
        tv_phone = findViewById(R.id.phone);
        tv_email = findViewById(R.id.email);
        tv_address = findViewById(R.id.address);
        SessionManager section = new SessionManager(Profile.this);
        int userID = section.getUserId();
        if (userID != 0) {
            Information(userID);
        }
        Button btnChange = findViewById(R.id.change_button);
        btnChange.setOnClickListener(view -> {
            startActivity(new Intent(Profile.this, EditProfile.class));
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
                return true;
            }
            return false;
        });
    }

    public void Information(int userID) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query { " +
                "userById(userId: " + userID + ") { " +
                "firstName " +
                "gender " +
                "dateOfBirth " +
                "phone " +
                "email " +
                "deliveryAddress " +
                "point " +
                "} " +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    if (data.getData() instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                        Object informations = dataMap.get("userById");
                        if (informations instanceof LinkedTreeMap) {
                            LinkedTreeMap<String, Object> informationMap = (LinkedTreeMap<String, Object>) informations;
                            User user = new User();
                            user.setFirstName((String) informationMap.get("firstName"));
                            user.setGender((String) informationMap.get("gender"));
                            user.setDateOfBirth(parseDate((String) informationMap.get("dateOfBirth")));
                            user.setPhone((String) informationMap.get("phone"));
                            user.setEmail((String) informationMap.get("email"));
                            user.setDeliveryAddress((String) informationMap.get("deliveryAddress"));

                            // Set data to TextViews
                            tv_name.setText(user.getFirstName());
                            tv_gender.setText(user.getGender());
                            setFormattedDateOfBirth(user.getDateOfBirth());
                            tv_phone.setText(user.getPhone());
                            tv_email.setText(user.getEmail());
                            tv_address.setText(user.getDeliveryAddress());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("Profile", "Lỗi khi gọi API: " + t.getMessage());
            }
        });
    }

    public void setFormattedDateOfBirth(Date dateTime) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày muốn hiển thị
        String formattedDate = displayFormat.format(dateTime);
        tv_dob.setText(formattedDate);
    }
    public Date parseDate(String dateString) {
        SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // Định dạng ngày từ API
        try {
            return apiFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}