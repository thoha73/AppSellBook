package com.example.appsellbook.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        checkSessionTimeout();
    }

    private void updateSessionStartTime() {
        long currentTimeMillis = System.currentTimeMillis();
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("session_start_time", currentTimeMillis);
        editor.apply();
    }

    private void checkSessionTimeout() {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        long sessionStartTime = preferences.getLong("session_start_time", -1);

        if (sessionStartTime != -1) {
            long currentTimeMillis = System.currentTimeMillis();
            long sessionDuration = currentTimeMillis - sessionStartTime;
            long maxSessionDuration = 5 * 60 * 1000; // 1 phút (hoặc 5 phút tùy vào yêu cầu)

            if (sessionDuration < maxSessionDuration) {
                // Kéo dài thời gian session nếu chưa hết hạn
                updateSessionStartTime();
            } else {
                // Session hết hạn, yêu cầu đăng nhập lại
                new AlertDialog.Builder(this)
                        .setTitle("Phiên làm việc hết hạn")
                        .setMessage("Phiên làm việc của bạn đã hết hạn. Vui lòng đăng nhập lại.")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                            startActivity(new Intent(BaseActivity.this, Login.class));
                            finish(); // Đóng activity hiện tại
                        })
                        .show();
            }
        }
    }
}
