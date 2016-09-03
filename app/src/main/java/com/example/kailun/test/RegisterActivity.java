package com.example.kailun.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by kailun on 2016/9/3.
 */
public class RegisterActivity extends Activity {
    EditText email;
    EditText password;
    EditText cpassword;
    Button btn_signUp;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intent = getIntent();
        email = (EditText)findViewById(R.id.register_email);
        password = (EditText)findViewById(R.id.register_password);
        cpassword = (EditText)findViewById(R.id.register_con_password);
        btn_signUp = (Button)findViewById(R.id.register_sign_up);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void signUp(){
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String cpassword = this.cpassword.getText().toString();
        //compare password and confirm password
        if(!existingEmail()){
            System.out.println("Enter");
            if(password.equals(cpassword)){
                if(validPassword(password)) {
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    showToast("無效的密碼");
                }
            }else{
                showToast("密碼確認錯誤");
            }
        }else{
            //alert
            showToast("郵件地址已被使用");
        }


    }
    public boolean existingEmail(){

        return  false;
    }
    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
    public boolean validPassword(String password){
        //boolean hasSpecial   = !password.matches("[A-Za-z0-9 ]*");
        boolean isNotEmpty = !password.equals("");
        boolean isAtLeast8   = password.length() >=7;
        boolean noConditions = !(password.contains("AND") || password.contains("NOT"));
        boolean hasUppercase = !password.equals(password.toLowerCase());

        return isNotEmpty && isAtLeast8 && noConditions && hasUppercase;
    }
}
