package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appsellbook.DTOs.Commentation;
import com.example.appsellbook.DTOs.User;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.adapter.CommentArrayAdapter;
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

public class Signup extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btn_signup = findViewById(R.id.btn_signup);
        TextView textViewLogin = findViewById(R.id.textView_Login);
        EditText edt_username, edt_password1, edt_confirmPassword;
        edt_username= findViewById(R.id.edt_username);
        edt_password1 = findViewById(R.id.edt_password);
        edt_confirmPassword = findViewById(R.id.edt_confirmPassword);
        CheckBox checkBox = findViewById(R.id.checkBox_agree);
        btn_signup.setOnClickListener(v->{

        });
        textViewLogin.setOnClickListener(v->{
            startActivity(new Intent(Signup.this, Login.class));
        });
        edt_username.setOnFocusChangeListener((view, b) -> {
            if (b) {
                edt_username.setHint("");
            } else {
                if (edt_username.getText().toString().isEmpty()) {
                    edt_username.setHint("Username");
                }
        }});
        edt_password1.setOnFocusChangeListener((view, b) -> {
            if (b) {
                edt_password1.setHint("");

            } else {
                if (edt_password1.getText().toString().isEmpty()) {
                    edt_password1.setHint("Password");
                }
            }
        });
        edt_confirmPassword.setOnFocusChangeListener((view, b) -> {
            if (b) {
                edt_confirmPassword.setHint("");
            } else {
                if (edt_confirmPassword.getText().toString().isEmpty()) {
                    edt_confirmPassword.setHint("Confirm Password");
                }
            }
        });
        edt_password1.setOnTouchListener((view, motionEvent) -> {
            return hidenPassword(edt_password1, motionEvent);
        });
        edt_confirmPassword.setOnTouchListener((view, motionEvent) -> {
           return  hidenPassword(edt_confirmPassword, motionEvent);
        });
        btn_signup.setOnClickListener(view -> {
            String username="";
            String password="";
            if(edt_username.getText().toString().isEmpty()){
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Vui lòng nhập tên đăng nhập!")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();

            }
            username=edt_username.getText().toString();
            if (!edt_password1.getText().toString().equals(edt_confirmPassword.getText().toString())) {
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Mật khẩu nhập lại không khớp. Vui lòng kiểm tra lại!")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }else{
                password=edt_password1.getText().toString();
                if(!checkBox.isChecked()){
                    new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Vui lòng chọn đồng ý với điều khoản của dịch vụ!")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .show();
                }else{
                    Register(username, password);

                }
            }

        });


    }
    public boolean hidenPassword(EditText edt, MotionEvent motionEvent){
        if(motionEvent.getAction()==MotionEvent.ACTION_UP){
            if (motionEvent.getRawX()>=(edt.getRight()-(edt.getCompoundDrawables()[2].getBounds().width()+20))) {
                if(edt.getTransformationMethod() instanceof PasswordTransformationMethod){
                    edt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Drawable drawableR = getResources().getDrawable(R.drawable.eye_slash_solid);
                    Drawable drawableL = getResources().getDrawable(R.drawable.lock_solid);
                    drawableR.setBounds(0,0,drawableR.getIntrinsicWidth(),drawableR.getIntrinsicHeight());
                    drawableL.setBounds(0,0,drawableL.getIntrinsicWidth(),drawableL.getIntrinsicHeight());
                    edt.setCompoundDrawables(drawableL, null, drawableR, null);
                }else{
                    edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Drawable drawableR= getResources().getDrawable(R.drawable.eye_solid);
                    Drawable drawableL= getResources().getDrawable(R.drawable.lock_solid);
                    drawableR.setBounds(0,0,drawableR.getIntrinsicWidth(),drawableR.getIntrinsicHeight());
                    drawableL.setBounds(0,0,drawableL.getIntrinsicWidth(),drawableL.getIntrinsicHeight());
                    edt.setCompoundDrawables(drawableL, null, drawableR, null);

                }
                edt.setSelection(edt.getText().length());
                return true;
            }
        }
        return false;
    };
//    public void Register(String username, String password) {
//        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
//
//        // Tạo GraphQL query
//        String query = "mutation {\n" +
//                "  register(registerRequest: {\n" +
//                "     username: \"" + username + "\",\n" +
//                "     password: \"" + password + "\"\n" +
//                "  }) {\n" +
//                "    userId\n" +
//                "    username\n" +// Có thể loại bỏ nếu không cần trả về password
//                "  }\n" +
//                "}";
//
//        GraphQLRequest request = new GraphQLRequest(query);
//        Log.e("Register", "Query: " + query);
//
//        // Gửi yêu cầu
//        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
//            @Override
//            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    GraphQLResponse<Object> responseData = response.body();
//                    Object data = responseData.getData();
//
//                    if (data instanceof LinkedTreeMap) {
//                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data;
//                        Object userObject = dataMap.get("register");
//
//                        if (userObject instanceof LinkedTreeMap) {
//                            LinkedTreeMap<String, Object> userMap = (LinkedTreeMap<String, Object>) userObject;
//
//                            // Trích xuất thông tin từ phản hồi
//                            Double userIdDouble = (Double) userMap.get("userId");
//                            String returnedUsername = (String) userMap.get("username");
//
//                            int userId = userIdDouble != null ? userIdDouble.intValue() : -1;
//
//                            // Lưu thông tin vào session
//                            SessionManager sessionManager = new SessionManager(getApplicationContext());
//                            sessionManager.saveUserSession(userId, returnedUsername);
//
//                            long currentTimeMillis = System.currentTimeMillis();
//                            SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putLong("session_start_time", currentTimeMillis); // Lưu thời gian bắt đầu
//                            editor.apply();
//                            // Thông báo đăng ký thành công
//                            new AlertDialog.Builder(Signup.this)
//                                    .setTitle("Thông báo")
//                                    .setMessage("Đăng kí thành công. Vui lòng cập nhật thông tin!")
//                                    .setPositiveButton("OK", (dialog, which) ->{
//                                        dialog.dismiss();
//                                        startActivity(new Intent(Signup.this, EditProfile.class));
//                                    } )
//                                    .show();
//                        } else {
//                            Log.e("Register Error", "Không tìm thấy thông tin 'register' trong phản hồi.");
//                        }
//                    } else {
//                        Log.e("Register Error", "Dữ liệu phản hồi không đúng định dạng.");
//                    }
//                } else {
//                    Log.e("Register Error", "Phản hồi thất bại: " + response.message());
//                    Toast.makeText(getApplicationContext(), "Đăng ký thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
//                Log.e("Register Failure", "Lỗi khi gửi yêu cầu", t);
//                Toast.makeText(getApplicationContext(), "Đăng ký thất bại, kiểm tra kết nối!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    public void Register(String username, String password) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);

        // Tạo GraphQL query
        String query = "mutation {\n" +
                "  register(registerRequest: {\n" +
                "     username: \"" + username + "\",\n" +
                "     password: \"" + password + "\"\n" +
                "  }) {\n" +
                "    userId\n" +
                "    username\n" + // Có thể loại bỏ nếu không cần trả về password
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        Log.e("Register", "Query: " + query);

        // Gửi yêu cầu
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> responseData = response.body();
                    Object data = responseData.getData();

                    if (data instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data;
                        Object userObject = dataMap.get("register");

                        if (userObject instanceof LinkedTreeMap) {
                            LinkedTreeMap<String, Object> userMap = (LinkedTreeMap<String, Object>) userObject;

                            // Trích xuất thông tin từ phản hồi
                            Double userIdDouble = (Double) userMap.get("userId");
                            String returnedUsername = (String) userMap.get("username");

                            int userId = userIdDouble != null ? userIdDouble.intValue() : -1;

                            // Lưu thông tin vào session
                            SessionManager sessionManager = new SessionManager(getApplicationContext());
                            List<Integer> roleIds = new ArrayList<>();
                            roleIds.add(1);
                            sessionManager.saveUserSession(userId, returnedUsername,roleIds);

                            long currentTimeMillis = System.currentTimeMillis();
                            SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putLong("session_start_time", currentTimeMillis); // Lưu thời gian bắt đầu
                            editor.apply();

                            // Thông báo đăng ký thành công
                            new AlertDialog.Builder(Signup.this)
                                    .setTitle("Thông báo")
                                    .setMessage("Đăng kí thành công. Vui lòng cập nhật thông tin!")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        dialog.dismiss();
                                        startActivity(new Intent(Signup.this, EditProfile.class));
                                    })
                                    .show();
                        } else {
                            Log.e("Register Error", "Không tìm thấy thông tin 'register' trong phản hồi.");
                        }
                    } else if (responseData.getErrors() != null) {
                        // Nếu có lỗi trong trường errors
                        List<GraphQLError> errors = (List<GraphQLError>) responseData.getErrors();
                        StringBuilder errorMessages = new StringBuilder();
                        for (GraphQLError error : errors) {
                            errorMessages.append(error.getMessage()).append("\n");
                        }
                        // Hiển thị thông báo lỗi cho người dùng
                        new AlertDialog.Builder(Signup.this)
                                .setTitle("Lỗi")
                                .setMessage(errorMessages.toString())
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();
                    } else {
                        Log.e("Register Error", "Dữ liệu phản hồi không đúng định dạng.");
                    }
                } else {
                    Log.e("Register Error", "Phản hồi thất bại: " + response.message());
                    Toast.makeText(getApplicationContext(), "Đăng ký thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("Register Failure", "Lỗi khi gửi yêu cầu", t);
                Toast.makeText(getApplicationContext(), "Đăng ký thất bại, kiểm tra kết nối!", Toast.LENGTH_SHORT).show();
            }

        });
    }




}