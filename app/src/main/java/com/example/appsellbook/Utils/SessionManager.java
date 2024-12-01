package com.example.appsellbook.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE_IDS = "roleIds";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_SESSION_START_TIME = "sessionStartTime";
    private static final String KEY_SESSION_POINT = "point";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu userId và username vào session
    public void saveUserSession(int userId, String username, List<Integer> roleIds,int point) {
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putLong(KEY_SESSION_START_TIME, System.currentTimeMillis());
        editor.putInt(KEY_SESSION_POINT,point);
        Set<String> roleIdSet = new HashSet<>();
        for (Integer roleId : roleIds) {
            roleIdSet.add(String.valueOf(roleId));
        }
        editor.putStringSet(KEY_ROLE_IDS, roleIdSet);
        editor.apply();
    }
    public boolean isSessionExpired() {
        long sessionStartTime = sharedPreferences.getLong(KEY_SESSION_START_TIME, 0);
        if (sessionStartTime == 0) {
            return true;  // Nếu không có thời gian bắt đầu session, coi như session đã hết hạn
        }

        long currentTime = System.currentTimeMillis();
        long sessionTimeout = 10 * 60 * 1000;  // 5 phút (300000 ms)

        return (currentTime - sessionStartTime) > sessionTimeout;
    }
    // Lấy userId từ session
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1); // -1 nếu không có userId
    }
    public int getPoint(){
        return sharedPreferences.getInt(KEY_SESSION_POINT,0);
    }

    // Lấy username từ session
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null); // null nếu không có username
    }
    public Set<Integer> getRoleIds() {
        Set<String> roleIdSet = sharedPreferences.getStringSet(KEY_ROLE_IDS, new HashSet<>());
        Set<Integer> roleIds = new HashSet<>();
        for (String roleId : roleIdSet) {
            roleIds.add(Integer.parseInt(roleId)); // Chuyển đổi từ String sang Integer
        }
        return roleIds;
    }
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Xóa session
    public void clearSession() {
        editor.clear();
        editor.apply();
    }

}
