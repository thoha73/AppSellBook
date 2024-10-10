package com.example.appsellbook.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;

public class ChangePasswordUser extends AppCompatActivity {
    Button btn_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(v->viewdialog());
    }
    private void viewdialog(){
        Dialog dialog = new Dialog(ChangePasswordUser.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialogsuccessful);

        Button buttonOK = dialog.findViewById(R.id.buttonOK);


        // Xử lý sự kiện khi nhấn Yes hoặc No
        buttonOK.setOnClickListener(v -> {
            dialog.dismiss();
            new Handler().postDelayed(() -> {
                // Quay lại trang TotalUser
                Intent intent = new Intent(ChangePasswordUser.this, TotalUser.class);
                startActivity(intent);
                finish(); // Hoặc có thể dùng finish() nếu bạn muốn đóng Activity hiện tại
            }, 1000);
        });


        dialog.show();
    }
}
