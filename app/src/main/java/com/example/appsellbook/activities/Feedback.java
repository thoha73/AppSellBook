package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feedback extends AppCompatActivity {
    private ImageView imageV_book,imageV_back;
    private TextView textView_bookName;
    RatingBar ratingBar;
    EditText edt_feedback;
    Button btn_confirm_feedback;
    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());

        imageV_book = findViewById(R.id.img_feedback);
        textView_bookName = findViewById(R.id.textView_bookName);
        btn_confirm_feedback = findViewById(R.id.btn_confirm_feedback);
        ratingBar = findViewById(R.id.ratingbar_feedback);
        edt_feedback = findViewById(R.id.content_feedback);

        String bookName = getIntent().getStringExtra("bookName");
        String imagePath = getIntent().getStringExtra("imagePath");
        int bookId = getIntent().getIntExtra("bookId",0);
        textView_bookName.setText(bookName);

        if (imagePath != null) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageV_book.setImageBitmap(bitmap);
            } else {
                Log.e("Feedback", "Image file does not exist: " + imagePath);
            }
        }
        btn_confirm_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedbackContent = edt_feedback.getText().toString();
                float rating = ratingBar.getRating();
                SessionManager sessionManager = new SessionManager(Feedback.this);
                int userId = sessionManager.getUserId();
                if (feedbackContent.isEmpty() || rating == 0) {
                    Toast.makeText(Feedback.this, "Vui lòng nhập nội dung và đánh giá!", Toast.LENGTH_SHORT).show();
                    return;
                }

                GraphQLApiService apiService = RetrofitClient.getClient(Feedback.this).create(GraphQLApiService.class);
                String mutationQuery = "mutation {" +
                        " createCommentation(commentationType: { " +
                        "content: \"" + feedbackContent + "\", " +
                        "rank: " + rating + " }," +
                        " userId: " + userId + "," +
                        " bookId: " + bookId + ") " +
                        "{ " +
                        "content " +
                        "} " +
                        "}";
                GraphQLRequest request = new GraphQLRequest(mutationQuery);
                apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
                    @Override
                    public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(Feedback.this, "Phản hồi đã được lưu thành công!", Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish(); // Kết thúc Activity hiện tại để quay lại màn hình trước đó
                                }
                            }, 2000);
                        } else {
                            Log.e("Feedback", "Lỗi từ server: " + response.errorBody());
                            Toast.makeText(Feedback.this, "Không thể lưu phản hồi. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                        Log.e("Feedback", "Lỗi kết nối: " + t.getMessage());
                        Toast.makeText(Feedback.this, "Kết nối thất bại! Vui lòng kiểm tra mạng.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        BottomNavigationView bottom_NavigationView;
        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_home){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_notification){
                    startActivity(new Intent(getApplicationContext(), Notification.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
                    startActivity(new Intent(getApplicationContext(), Settings.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_profile){
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                return false;
            }
        });
    }
}