package com.example.kailun.test;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

/**
 * Created by kailun on 2016/9/3.
 */
public class RegisterActivity extends Activity {
    static EditText edEmail;
    static EditText edPassword;
    static EditText edCpassword;
    static EditText edUsername;
    Button btn_signUp;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intent = getIntent();
        edEmail = (EditText)findViewById(R.id.register_email);
        edPassword = (EditText)findViewById(R.id.register_password);
        edCpassword = (EditText)findViewById(R.id.register_con_password);
        edUsername = (EditText)findViewById(R.id.register_username);
        btn_signUp = (Button)findViewById(R.id.register_sign_up);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signUp();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void signUp() throws ExecutionException, InterruptedException {
        String email = this.edEmail.getText().toString();
        String password = this.edPassword.getText().toString();
        String cpassword = this.edCpassword.getText().toString();
        String username = this.edUsername.getText().toString();
        String register_url = " https://html-practicing-ming43121.c9users.io/Task1/php/createAccount.php";
        //compare password and confirm password
        if(!existingEmail()){
            //System.out.println("Enter");
            if(password.equals(cpassword)){
                if(validPassword(password)) {

                    String result =  new Connection().execute(email,password,username,register_url).get();
                    if(result.equals("Account Created")) {
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        showToast(result);
                    }
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
    private class Connection extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            String username = params[2];
            String register_url = params[3];
            String result="";
            try{
                URL url = new URL(register_url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
                post_data += "&" + URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");
                post_data += "&" + URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(username,"UTF-8");
                bufferedWriter.write(post_data);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    result+=line;
                }

                urlConnection.disconnect();

                return result;
            }catch (Exception e){
                e.printStackTrace();
            }
            return "error";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            showToast("Signing up");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
