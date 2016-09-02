package com.example.kailun.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.SyncTree;
import com.firebase.client.realtime.util.StringListReader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kailun on 2016/9/1.
 */

public class Login extends Fragment implements View.OnClickListener{
    View view;
    EditText editEmail;
    EditText editPassword;
    Button btnLogin;
    Button btnRegister;
    Firebase myFirebaseRef;
    private static final String FIREBASE_URL = "https://radiant-inferno-4091.firebaseio.com";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_login, container, false);


        Firebase.setAndroidContext(view.getContext());
        myFirebaseRef = new Firebase(FIREBASE_URL);
        editEmail = (EditText) view.findViewById(R.id.email_edtext);
        editPassword = (EditText)view.findViewById(R.id.password_edtext);
        btnLogin = (Button)view.findViewById(R.id.login_btn);
        btnRegister = (Button)view.findViewById(R.id.register_btn);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        return view;
    }
    public void login(){
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        myFirebaseRef.authWithPassword(email,password, new Firebase.AuthResultHandler(){
            @Override
            public void onAuthenticated(AuthData authData) {
                showToast("logged in");
                System.out.println(authData.getProviderData());
                Map<String, String> map = new HashMap<String, String>();
                map.put("provider", authData.getProvider());
                if(authData.getProviderData().containsKey("email")) {
                    map.put("email", authData.getProviderData().get("email").toString());

                }
                myFirebaseRef.child("users").child(authData.getUid()).setValue(map);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                switch (firebaseError.getCode()) {
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        // handle a non existing user
                        showToast("non existing user");
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        // handle an invalid password
                        showToast("invalid password");
                        break;
                    default:
                        // handle other errors
                        break;
                }
            }
        });

    }
    public void register(){

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        myFirebaseRef.createUser(email,password,new Firebase.ValueResultHandler<Map<String, Object>>(){

            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                showToast("Successfully created user account with uid: " + stringObjectMap.get("uid"));
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                showToast(firebaseError.getMessage());
            }
        });

    }
    public void showToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login_btn)
            login();
        else if(v.getId() == R.id.register_btn)
            register();
    }
}
