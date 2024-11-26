package com.example.appsellbook.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu userId và username vào session
    public void saveUserSession(int userId, String username) {
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    // Lấy userId từ session
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1); // -1 nếu không có userId
    }

    // Lấy username từ session
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null); // null nếu không có username
    }

    // Kiểm tra trạng thái đăng nhập
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Xóa session
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
