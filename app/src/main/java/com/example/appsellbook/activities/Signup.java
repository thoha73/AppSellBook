package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;

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
//            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
//                if (motionEvent.getRawX() >= (edt_password1.getRight() - (edt_password1.getCompoundDrawables()[2].getBounds().width()+20))){
//                    if(edt_password1.getTransformationMethod() instanceof PasswordTransformationMethod){
//                        edt_password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                        Drawable drawableR = getResources().getDrawable(R.drawable.eye_slash_solid);
//                        Drawable drawableL = getResources().getDrawable(R.drawable.lock_solid);
//                        drawableR.setBounds(0, 0, drawableR.getIntrinsicWidth(), drawableR.getIntrinsicHeight());
//                        drawableL.setBounds(0, 0, drawableL.getIntrinsicWidth(), drawableL.getIntrinsicHeight());
//                        edt_password1.setCompoundDrawables(drawableL, null, drawableR, null);
//                    }else{
//                        edt_password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                        Drawable drawableR = getResources().getDrawable(R.drawable.eye_solid);
//                        Drawable drawableL = getResources().getDrawable(R.drawable.lock_solid);
//                        drawableR.setBounds(0, 0, drawableR.getIntrinsicWidth(), drawableR.getIntrinsicHeight());
//                        drawableL.setBounds(0, 0, drawableL.getIntrinsicWidth(), drawableL.getIntrinsicHeight());
//                        edt_password1.setCompoundDrawables(drawableL, null, drawableR, null);
//                    }
//                    edt_password1.setSelection(edt_password1.getText().length());
//                    return true;
//                }
//            }
//            return false;
        });
        edt_confirmPassword.setOnTouchListener((view, motionEvent) -> {
           return  hidenPassword(edt_confirmPassword, motionEvent);
//            if(motionEvent.getAction()==MotionEvent.ACTION_UP){
//                if (motionEvent.getRawX()>=(edt_confirmPassword.getRight()-(edt_confirmPassword.getCompoundDrawables()[2].getBounds().width()+20))) {
//                    if(edt_confirmPassword.getTransformationMethod() instanceof PasswordTransformationMethod){
//                        edt_confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                        Drawable drawableR = getResources().getDrawable(R.drawable.eye_slash_solid);
//                        Drawable drawableL = getResources().getDrawable(R.drawable.lock_solid);
//                        drawableR.setBounds(0,0,drawableR.getIntrinsicWidth(),drawableR.getIntrinsicHeight());
//                        drawableL.setBounds(0,0,drawableL.getIntrinsicWidth(),drawableL.getIntrinsicHeight());
//                        edt_confirmPassword.setCompoundDrawables(drawableL, null, drawableR, null);
//                    }else{
//                        edt_confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                        Drawable drawableR= getResources().getDrawable(R.drawable.eye_solid);
//                        Drawable drawableL= getResources().getDrawable(R.drawable.lock_solid);
//                        drawableR.setBounds(0,0,drawableR.getIntrinsicWidth(),drawableR.getIntrinsicHeight());
//                        drawableL.setBounds(0,0,drawableL.getIntrinsicWidth(),drawableL.getIntrinsicHeight());
//                        edt_confirmPassword.setCompoundDrawables(drawableL, null, drawableR, null);
//
//                    }
//                    edt_confirmPassword.setSelection(edt_confirmPassword.getText().length());
//                    return true;
//                }
//        }
//                return false;
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
}