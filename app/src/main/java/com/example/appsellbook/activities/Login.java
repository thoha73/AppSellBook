package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;

public class Login extends AppCompatActivity {
    Button btn_login;
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
        EditText edt_username, edt_password;
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
            startActivity(new Intent(Login.this, ShopOwner.class));
        });
    }

}
