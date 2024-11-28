package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.google.gson.internal.LinkedTreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText edt_username, edt_password, edt_confirmpass;
        TextView tv_login=findViewById(R.id.textView_Login);
        Button btn_next;
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        edt_confirmpass = findViewById(R.id.edt_confirmpassword);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(view -> {
            if (edt_username.getText().toString().isEmpty()) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Please enter username");
                alertDialog.show();
            } else {
                GetUsername(edt_username.getText().toString(), username -> {
                    if (username != null) {
                        // Nếu username hợp lệ
                        runOnUiThread(() -> {
                            edt_username.setVisibility(View.GONE);
                            edt_password.setVisibility(View.VISIBLE);
                            edt_confirmpass.setVisibility(View.VISIBLE);
                            btn_next.setText("RESET");
                            btn_next.setOnClickListener(v -> {
                                if (!edt_password.getText().toString().equals(edt_confirmpass.getText().toString())) {
                                    new AlertDialog.Builder(ForgetPassword.this)
                                            .setTitle("Thông báo")
                                            .setMessage("Mật khẩu nhập lại không đúng. Vui lòng nhập lại!")
                                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                            .show();
                                } else {
                                    ResetPassword(username, edt_password.getText().toString());
                                }
                            });
                        });
                    } else {
                        runOnUiThread(() -> {
                            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                            alertDialog.setTitle("Thông báo");
                            alertDialog.setMessage("Tài khoản không hợp lệ. Vui lòng kiểm tra lại!.");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK",(dialog, which) -> dialog.dismiss());
                            alertDialog.show();
                        });
                    }
                });
            }
        });
        edt_username.setOnFocusChangeListener((view, b) -> {
            setFocus(edt_username,b,"Username");
      });
        edt_password.setOnFocusChangeListener((view, b) -> {
            setFocus(edt_password,b,"Password");
        });
        edt_password.setOnTouchListener((view, motionEvent) -> {
            return hidenPassword(edt_password, motionEvent);
        });
        edt_confirmpass.setOnFocusChangeListener((view, b) -> {
            setFocus(edt_confirmpass,b,"Confirm password");
        });
        edt_confirmpass.setOnTouchListener((view, motionEvent) -> {
          return hidenPassword(edt_confirmpass,motionEvent)  ;
        });
        tv_login.setOnClickListener(view -> {
            startActivity(new Intent(ForgetPassword.this,Login.class));
        });


    }
    private boolean hidenPassword(EditText edt, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP){
            if(event.getRawX()>=edt.getRight()-(edt.getCompoundDrawables()[2].getBounds().width()+20)){
                if(edt.getTransformationMethod() instanceof PasswordTransformationMethod){
                    edt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Drawable drawableR=getResources().getDrawable(R.drawable.eye_slash_solid);
                    Drawable drawableL=getResources().getDrawable(R.drawable.lock_solid);
                    drawableR.setBounds(0,0,drawableR.getIntrinsicWidth(),drawableR.getIntrinsicHeight());
                    drawableL.setBounds(0,0,drawableL.getIntrinsicWidth(),drawableL.getIntrinsicHeight());
                    edt.setCompoundDrawables(drawableL,null,drawableR,null);
                    return true;
                }else{
                    edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Drawable drawableR=getResources().getDrawable(R.drawable.eye_solid);
                    Drawable drawableL=getResources().getDrawable(R.drawable.lock_solid);
                    drawableR.setBounds(0,0,drawableR.getIntrinsicWidth(),drawableR.getIntrinsicHeight());
                    drawableL.setBounds(0,0,drawableL.getIntrinsicWidth(),drawableL.getIntrinsicHeight());
                    edt.setCompoundDrawables(drawableL,null,drawableR,null);
                    return true;
                }
            }
        }
        edt.setSelection(edt.getText().length());
        return false;
    };
    private void setFocus(View v,Boolean hasFocus, String hint){
        if(v instanceof EditText){
            EditText edt=(EditText) v;
            if(hasFocus){
                edt.setHint("");
            }else{
                edt.setHint(hint);
            }
        }
    }
    private void GetUsername(String username, UsernameCallback callback) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "mutation{\n" +
                "  pass(username: \"" + username + "\"){\n" +
                "    username\n" +
                "    userId\n" +
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);

        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();

                    if (data.getErrors() != null && !data.getErrors().isEmpty()) {
                        callback.onResult(null); // Lỗi, trả về null
                    } else {
                        if (data.getData() instanceof LinkedTreeMap) {
                            LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                            Object pass = dataMap.get("pass");

                            if (pass instanceof LinkedTreeMap) {
                                LinkedTreeMap<String, Object> passMap = (LinkedTreeMap<String, Object>) pass;
                                String usernameValue = (String) passMap.get("username");

                                callback.onResult(usernameValue); // Trả về username
                                return;
                            }
                        }
                    }
                }
                callback.onResult(null); // Trả về null nếu không thành công
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                callback.onResult(null); // Lỗi mạng hoặc khác
            }
        });
    }

    private void ResetPassword(String inputUsername, String inputPassword) {
            GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
            String query = "mutation{\n" +
                    "  updatePass(username: \""+inputUsername+"\",pass: \""+inputPassword+"\"){\n" +
                    "    userId\n" +
                    "  }\n" +
                    "}";

            GraphQLRequest request = new GraphQLRequest(query);
            apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
                @Override
                public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        GraphQLResponse<Object> data = response.body();
                        if (data.getErrors() != null && !data.getErrors().isEmpty()) {
                            // Nếu có lỗi trong response, hiển thị lỗi
                            String errorMessage = data.getErrors().get(0).getMessage();
                            new AlertDialog.Builder(ForgetPassword.this)
                                    .setTitle("Thông báo")
                                    .setMessage(errorMessage)
                                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                    .show();
                        } else {
                            try {
                                if (data.getData() instanceof LinkedTreeMap) {
                                    LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                                    Object  pass= dataMap.get("updatePass");

                                    if (pass instanceof LinkedTreeMap) {
                                        LinkedTreeMap<String, Object> password = (LinkedTreeMap<String, Object>) pass;

                                        Double userIdValue = (Double) password.get("userId");

                                        if (userIdValue != null ) {
                                            int userId = userIdValue.intValue();

                                            // Hiển thị thông báo thành công
                                            new AlertDialog.Builder(ForgetPassword.this)
                                                    .setTitle("Thông báo")
                                                    .setMessage("Bạn đã thay đổi mật khẩu thành công. Vui lòng đăng nhập!\n")
                                                    .setPositiveButton("OK", (dialog, which) -> {
                                                        dialog.dismiss();
                                                        startActivity(new Intent(ForgetPassword.this,Login.class) );
                                                    })
                                                    .show();
                                        } else {
                                            Log.e("Error","Dữ liệu trả về chứa giá trị null  userId");
                                        }
                                    } else {
                                        Log.e("Error","Dữ liệu 'updatePass' không phải LinkedTreeMap: " + pass);
                                    }
                                } else {
                                    Log.e("Error","Dữ liệu phản hồi không đúng định dạng: " + data.getData());
                                }
                            } catch (Exception e) {
                                Log.e("Error","Lỗi khi xử lý dữ liệu trả về từ GraphQL: " + e.getMessage());
                            }
                        }
                    } else {
                        Log.e("Error","Phản hồi thất bại: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                    Log.e("Error","Lỗi khi tải dữ liệu: " + t.getMessage());
                }
            });
    }

}
interface UsernameCallback {
    void onResult(String username);
}