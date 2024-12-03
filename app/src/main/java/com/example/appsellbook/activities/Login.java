package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLError;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    Button btn_login;
    EditText edt_username, edt_password;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView tv_signup, tv_forgotpassword;
        tv_signup= findViewById(R.id.tv_signup);
        tv_forgotpassword = findViewById(R.id.textView_ForgotPassword);

        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        edt_username.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                edt_username.setHint("");
            }else{
                edt_username.setHint("Username");
            }
        });
        edt_password.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                edt_password.setHint("");
            }
        });
        edt_password.setOnTouchListener((v, event) -> {

            if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (edt_password.getRight() - (edt_password.getCompoundDrawables()[2].getBounds().width()+20))) {
                    if (edt_password.getTransformationMethod() instanceof android.text.method.PasswordTransformationMethod) {
                        edt_password.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                        Drawable drawableR = getResources().getDrawable(R.drawable.eye_slash_solid);
                        Drawable drawableL = getResources().getDrawable(R.drawable.lock_solid);
                        drawableR.setBounds(0, 0, drawableR.getIntrinsicWidth(), drawableR.getIntrinsicHeight());
                        drawableL.setBounds(0, 0, drawableL.getIntrinsicWidth(), drawableL.getIntrinsicHeight());
                        edt_password.setCompoundDrawables(drawableL, null, drawableR, null);
                    } else {
                        edt_password.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                        Drawable drawableR = getResources().getDrawable(R.drawable.eye_solid);
                        Drawable drawableL = getResources().getDrawable(R.drawable.lock_solid);
                        drawableR.setBounds(0, 0, drawableR.getIntrinsicWidth(), drawableR.getIntrinsicHeight());
                        drawableL.setBounds(0,0,drawableL.getIntrinsicWidth(),drawableL.getIntrinsicHeight());
                        edt_password.setCompoundDrawables(drawableL, null, drawableR, null);

                    }
                    edt_password.setSelection(edt_password.getText().length());
                    return true;
                }
            }
            return false;
        });
        tv_signup.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Signup.class));
        });
        tv_forgotpassword.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, ForgetPassword.class));
        });
        setUpLogin();
    }
    private void setUpLogin() {
        btn_login.setOnClickListener(view -> {
            String username=edt_username.getText().toString();
            String password=edt_password.getText().toString();
            if(username.isEmpty() || password.isEmpty()){
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Vui lòng nhập đầy đủ thông tin!")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }else {
                LoginApp(username,password);
            }

        });
    }
    private void LoginApp(String username, String password) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);

        // Tạo GraphQL query
        String query = "mutation {\n" +
                "  login(loginRequest: {\n" +
                "     username: \"" + username + "\",\n" +
                "     password: \"" + password + "\"\n" +
                "  }) {\n" +
                "    userId\n" +
                "    username\n" +
                "    point\n" +
                "    firstName\n" +
                "    roleUsers{\n"+
                "       rolesroleId\n"+
                "    }\n"+
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        Log.e("Login", "Query: " + query);

        // Gửi yêu cầu
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> responseData = response.body();
                    Object data = responseData.getData();

                    if (data instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data;
                        Object userObject = dataMap.get("login");

                        if (userObject instanceof LinkedTreeMap) {
                            LinkedTreeMap<String, Object> userMap = (LinkedTreeMap<String, Object>) userObject;

                            // Trích xuất thông tin từ phản hồi
                            Double userIdDouble = (Double) userMap.get("userId");
                            Double pointDouble = (Double) userMap.get("point");
                            String returnedUsername = (String) userMap.get("username");
                            String returnedfirstname = (String) userMap.get("firstName");

                            int userId = userIdDouble != null ? userIdDouble.intValue() : -1;
                            int point = pointDouble != null ? pointDouble.intValue() : 0;
                            List<LinkedTreeMap<String, Object>> roleUsers = (List<LinkedTreeMap<String, Object>>) userMap.get("roleUsers");
                            List<Integer> roleIds = new ArrayList<>();
                            if (roleUsers != null) {
                                for (LinkedTreeMap<String, Object> roleUser : roleUsers) {
                                    Double roleIdDouble = (Double) roleUser.get("rolesroleId");
                                    Integer roleId = roleIdDouble != null ? roleIdDouble.intValue() : null;
                                    if (roleId != null) {
                                        roleIds.add(roleId);
                                    }
                                }
                            }
                            // Lưu thông tin vào session
                            SessionManager sessionManager = new SessionManager(getApplicationContext());
                            sessionManager.saveUserSession(userId, returnedfirstname,roleIds,point);

                            long currentTimeMillis = System.currentTimeMillis();
                            SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putLong("session_start_time", currentTimeMillis); // Lưu thời gian bắt đầu
                            editor.apply();

                            // Thông báo đăng ký thành công
                            new AlertDialog.Builder(Login.this)
                                    .setTitle("Thông báo")
                                    .setMessage("Đăng nhập thành công! ")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        boolean hasHomeAccess = false;

                                        for (LinkedTreeMap<String, Object> roleUser : roleUsers) {
                                            Object roleIdObj = roleUser.get("rolesroleId");
                                            Integer roleId = null;

                                            if (roleIdObj instanceof Double) {
                                                roleId = ((Double) roleIdObj).intValue(); // Chuyển Double sang Integer
                                            } else if (roleIdObj instanceof Integer) {
                                                roleId = (Integer) roleIdObj; // Nếu là Integer, giữ nguyên
                                            }

                                            if (roleId != null && roleId == 1) {
                                                hasHomeAccess = true;
                                                break;  // Dừng lại nếu tìm thấy roleId == 1
                                            }
                                            if(roleId != null && roleId == 2){
                                                hasHomeAccess = false;
                                                break;
                                            }
                                        }

                                        if (hasHomeAccess) {
                                            dialog.dismiss();  // Đóng dialog
                                            startActivity(new Intent(Login.this, Home.class));
                                        } else {
                                            dialog.dismiss();  // Đóng dialog
                                            startActivity(new Intent(Login.this, Home1.class));                                        }
                                    })
                                    .show();
                        } else {
                            Log.e("Login Error", "Không tìm thấy thông tin 'login' trong phản hồi.");
                        }
                    } else if (responseData.getErrors() != null) {
                        // Nếu có lỗi trong trường errors
                        List<GraphQLError> errors = (List<GraphQLError>) responseData.getErrors();
                        StringBuilder errorMessages = new StringBuilder();
                        for (GraphQLError error : errors) {
                            errorMessages.append(error.getMessage()).append("\n");
                        }
                        // Hiển thị thông báo lỗi cho người dùng
                        new AlertDialog.Builder(Login.this)
                                .setTitle("Lỗi")
                                .setMessage(errorMessages.toString())
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();
                    } else {
                        Log.e("Login Error", "Dữ liệu phản hồi không đúng định dạng.");
                    }
                } else {
                    Log.e("Login Error", "Phản hồi thất bại: " + response.message());
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("Register Failure", "Lỗi khi gửi yêu cầu", t);
                Toast.makeText(getApplicationContext(), "Đăng nhập thất bại, kiểm tra kết nối!", Toast.LENGTH_SHORT).show();
            }

        });
    }


}
