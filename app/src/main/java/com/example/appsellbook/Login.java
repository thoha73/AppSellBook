package com.example.appsellbook;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

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
        EditText edt_username, edt_password;
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
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
                    requestFocus();
                    return true;
                }
            }
            return false;
        });
    }
}
