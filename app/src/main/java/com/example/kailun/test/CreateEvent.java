package com.example.kailun.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

/**
 * Created by kailun on 2016/5/28.
 */
public class CreateEvent extends Fragment implements View.OnClickListener {
    View view;
    private Firebase myFirebaseRef;
    private Button btnSubmit;
    private DatePicker dpEventDate;
    private EditText edtxtEventName,edtxtEventLocation;
    private static final String FIREBASE_URL = "https://radiant-inferno-4091.firebaseio.com/Events";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_addevent, container, false);
        Firebase.setAndroidContext(view.getContext());

        dpEventDate = (DatePicker)view.findViewById(R.id.adding_event_date);
        edtxtEventLocation = (EditText)view.findViewById(R.id.adding_event_location);
        edtxtEventName = (EditText)view.findViewById(R.id.adding_event_title);
        btnSubmit = (Button)view.findViewById(R.id.adding_event_submit);
        btnSubmit.setOnClickListener(this);

        myFirebaseRef = new Firebase(FIREBASE_URL);
        return view;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.adding_event_submit:
                addToFireBase();
                break;
            case R.id.adding_event_poster:
                break;

        }

    }
    public void addToFireBase(){
        //if user has not input text
        if(!edtxtEventName.getText().toString().matches("") && !edtxtEventLocation.getText().toString().matches("")){
            try{

                String name = edtxtEventName.getText().toString();
                String location = edtxtEventLocation.getText().toString();
                String date = dpEventDate.getDayOfMonth()+"/"+(dpEventDate.getMonth()+1)+"/"+dpEventDate.getYear();
                EventsChat eventsChat = new EventsChat(name,location,date,"https://");
                //set these date to firebase
                myFirebaseRef.push().setValue(eventsChat);
                Toast.makeText(view.getContext(),"Submit",Toast.LENGTH_LONG).show();
                edtxtEventName.setText("");
                edtxtEventLocation.setText("");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(view.getContext(),"Some column is empty",Toast.LENGTH_LONG).show();
        }

    }
}
